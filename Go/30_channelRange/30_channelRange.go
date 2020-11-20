package main

import "fmt"

//Channels can be iterated over. That is, you can use the range keyword in the
//same manner as you would when using it with arrays, slices, and/or maps. This allows
//you to quickly and easily iterate over the messages that exist within a channel.

func squares() func() int { //returns another function which implements returning a sequence of squares
	var x int
	return func() int {
		x++
		return x * x
	}
}

func main() {
	f := squares()
	squares := make(chan int, 20)

	//for loop loads up the squares channel with all the squares of whole numbers where the square is <= 100
	for i := f(); i <= 100; i = f() {
		squares <- i
	}

	//Note, it's very important to close a channel before iterating over the messages within the channel.
	//Without doing this, the program would eventually panic due to a deadlock.
	//Closing the channel in this example also highlights an important point about closing channels,
	//and that is you can still read from them after they are closed, you just can't continue to send
	//messages to them if they have been closed.
	close(squares)

	//another for loop with the range keyword to iterate over the current contents of the squares channel
	for elem := range squares {
		fmt.Println(elem)
	}
}
