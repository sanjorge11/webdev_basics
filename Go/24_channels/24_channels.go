package main

import "fmt"

func main() {
	//Channels are communication objects used by goroutines to communicate and
	//share data with each other. In their simplest incarnation, one goroutine
	//will write messages into the channel, while another goroutine will read the
	//same messages out of the channel. Channels are created using the make function
	//together with the chan keyword. Like variables, channels are typed and as such
	//can be used to transport messages only of that data type, for which was declared with.
	msgChan := make(chan string)

	//To write data into a channel, you first specify the name of the channel followed by
	//the left arrow syntax, and then the data itself. This style of syntax is highly readable,
	//since the data always flows in the direction of the arrow, making the concept of message
	//passing and processing quite easy to understand.

	go func() {
		msgChan <- "Cloud" //three individual messages are written into the channel
		msgChan <- "Academy"
		msgChan <- "2020"
	}()

	msg1 := <-msgChan //Reading data off the channel involves moving the left arrow syntax to the front of the channel name.
	msg2 := <-msgChan
	msg3 := <-msgChan

	//Note: Channels act as first-in-first-out queues. For example, if one goroutine writes
	//messages into a channel and a separate goroutine reads them off the channel, then the
	//messages will be received in the exact same order sent.
	fmt.Println(msg1, msg2, msg3)
}
