package main

import (
	"fmt"
	"math/rand"
	"time"
)

func pause() {
	rand.Seed(time.Now().UnixNano())
	n := rand.Intn(3) //n will be between 0 and 3
	time.Sleep(time.Duration(n) * time.Second)
}

func doSomething(msg string) {
	pause() //pauses for random amount of time, 0 to 3 seconds
	fmt.Println(msg)
}

func main() {
	doSomething("sync1")

	//these go routines are conceptually lightweight threads, although they are not
	//strictly threads in the sense of what the operating system sees and manages.
	//Instead, goroutines are managed and scheduled by the Go runtime and done entirely
	//within the virtual space of the Go runtime. Goroutines enable you to start up and run
	//other threads of execution concurrently within your program. They are designed to be
	//lightweight with minimal overhead, meaning you can literally spin up thousands of these
	//without incurring performance penalties.
	go doSomething("async1")
	go doSomething("async2")
	go doSomething("async3")

	doSomething("sync2")

	time.Sleep(time.Second * 10)

	//Here, we can see that the order of the messages is different to the order as seen in the code.
	//The reason for this is that the goroutines all run at approximately the same time concurrently.
	//And since they are each required to pause for a random amount of time, the order in which they wake
	//up and resume will be different. Note, the final time.Sleep function, as used above, is a quick and
	//basic approach to allowing the program's goroutines to complete before the main program execution does.
	//There are, however, better techniques to manage this aspect when working with concurrency within Go.
}
