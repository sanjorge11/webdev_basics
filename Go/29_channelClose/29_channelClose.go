package main

import (
	"bytes"
	"fmt"
)

//Go channels can be explicitly closed to help with synchronization issues.
//Closing a channel is done by invoking the built-in close function, providing it with
//a channels name that you want to close --  the close function to close a channel,
//which is useful for synchronization purposes.

//The process function takes in two channels as inputs, work and fin.
//The work channel is used to receive messages as work to perform.
//The fin channel is used to communicate that all required work has been completed or finished.
//The work channel receives messages that are individual characters extracted
//one at a time from the word string variable initialized in main function

//Within the process function, a non-terminating for loop is started. Each cycle of the
//loop waits until a new message arrives on the work channel. In this case, the process
//function is designed simply to restitch all of the individual characters received back
//into a single string. This is then returned when the channel is determined to have been
//closed, which is done on the line: fin <- b.String(),
//with the final result being printed out by the Printf function on line: fmt.Printf("result: %s", <-fin) at the end
func process(work <-chan string, fin chan<- string) {
	var b bytes.Buffer
	for {
		if msg, notClosed := <-work; notClosed {
			fmt.Printf("%s recieved...\n", msg)
			b.WriteString(msg)
		} else {
			fmt.Println("channel closed")
			fin <- b.String()
			return
		}
	}
}

func main() {
	work := make(chan string, 3)
	fin := make(chan string)

	go process(work, fin)

	word := "cloudacademy"

	for j := 0; j < len(word); j++ {
		letter := string(word[j])
		work <- letter
		fmt.Printf("%s sent...\n", letter)
	}

	close(work)

	fmt.Printf("result: %s", <-fin)
}
