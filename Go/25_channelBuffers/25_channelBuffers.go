package main

import "time"

func main() {
	//Channels can be either unbuffered or buffered. When creating a channel using the
	//make function, you can supply a second optional parameter which specifies a buffer size.
	//The previous example involved the use of an unbuffered channel, given the size parameter
	//was not specified. An unbuffered channel causes the sender to block immediately after writing
	//its message into the channel, and until the receiver has read it back off the channel. In a
	//buffered channel scenario, the sender blocks only once the buffer has filled up and waits until
	//space becomes available before unblocking. Put another way, when the channel is full, the sender
	//blocks and waits until another goroutine reads off the channel making room by receiving.
	size := 3
	var buffChan = make(chan int, size)

	//reader
	go func() {
		//here, a reader goroutine is setup to read continuously from a buffered channel of size three
		for {
			_ = <-buffChan
			//The reader first reads from the channel and then immediately sleeps for a second --
			//The writer writes 10 messages as quickly as possible into the buffered channel
			//It immediately prints out a count of how many messages it has actually been able to
			//put into the buffered channel. Now, when this program runs, you will see the numbers
			//one through to four inclusive printed out immediately, and then the remaining numbers
			//five through to 10 are printed out slower at about one per second.
			//This shows the effect of channel buffering.
			//time.Sleep(time.Second)

			//With this update, there's a pause of three seconds and then rerun
			//the example a second time. And as expected, we now get a longer pause between messages five to 10.
			time.Sleep(time.Second * 3)
		}
	}()

	//writer
	writer := func() {
		for i := 1; i <= 10; i++ {
			buffChan <- i
			println(i)
		}
	}

	writer()
}
