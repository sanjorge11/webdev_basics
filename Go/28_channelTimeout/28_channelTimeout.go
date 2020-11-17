package main

import (
	"fmt"
	"time"
)

//Timeouts can be specified in select statements to help manage situations where
//it's taking too long to receive a message from any one of the channels being monitored.
//Consider using timeouts when you're implementing something that connects to an external resource.
//When working with external resources, you can never guarantee the response times and, therefore,
//may need to proactively take action after a predetermined timeout.

func main() {
	channel := make(chan string)

	go func(channel chan string) {
		time.Sleep(3 * time.Second)
		channel <- "cloudacademy"
	}(channel)

	select {
	case msg1 := <-channel:
		fmt.Println(msg1)
	// case <-time.After(1 * time.Second): //unblock execution after a configured amount of time if no other channels liven up
	// 	fmt.Println("Timeout exceeded!")
	case <-time.After(5 * time.Second): //has longer grace period before ending as a timeout exceeded
		fmt.Println("Timeout exceeded!")
	}
}
