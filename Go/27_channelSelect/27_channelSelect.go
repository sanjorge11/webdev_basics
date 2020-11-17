package main

import (
	"fmt"
	"math/rand"
	"time"
)

//When designing Go applications involving goroutines which read and write to
//multiple channels, you should seriously consider using the select and case statements
//to simplify the management and readability of waits across multiple channels.

func pause() {
	n := rand.Intn(5) //n will be between 0 and 5
	time.Sleep(time.Duration(n) * time.Second)
}

func func1(c chan<- string) {
	for {
		pause()
		c <- "cloud"
	}
}

func func2(c chan<- string) {
	for {
		pause()
		c <- "academy"
	}
}

func main() {
	rand.Seed(time.Now().Unix())

	//The example shown here spins up two goroutines named func1 and func2, each with its own dedicated channel.
	chan1 := make(chan string)
	chan2 := make(chan string)

	//It's important to note here that both functions don't share the same channel -- they have their own dedicated channels
	go func1(chan1)
	go func2(chan2)

	//A non terminating for loop is started, which encloses a select statement containing two case statements,
	//one per channel. The select makes the execution wait until a message arrives on either of the channels,
	//in which case, it is then read from and the execution continues.
	for {
		select { //using the select and case statements together to simplify the management of waiting on multiple channels
		case msg1 := <-chan1:
			fmt.Println(msg1)
		case msg2 := <-chan2:
			fmt.Println(msg2)
		}
	}
}
