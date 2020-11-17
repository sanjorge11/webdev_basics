package main

import (
	"fmt"
	"time"
)

//When using channels as function parameters, as you often will, by default can send and receive within the function.
//To provide additional safety at compile time, channel function parameters can be defined with a direction.
//That is, they can be defined to be read-only or write-only.

//The in function signature defined has a channel function parameter locked down for writing only,
//as per the additional channel direction notation inserted between the channel name and the channel type.
func in(channel chan<- string, msg string) {
	channel <- msg
}

//Likewise, the out function signature declared has a channel function parameter locked down for reading only.
func out(channel <-chan string) {
	for {
		fmt.Println(<-channel)
	}
}

func main() {
	channel := make(chan string, 1)

	go out(channel)

	for i := 0; i < 10; i++ {
		in(channel, fmt.Sprintf("cloudacademy - %d", i))
	}

	time.Sleep(time.Second * 10) //the "hacky" way to let goroutines to complete before the main program execution does
}
