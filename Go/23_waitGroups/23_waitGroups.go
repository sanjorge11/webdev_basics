package main

import (
	"fmt"
	"sync"
)

func main() {
	fmt.Println("start program")

	//WaitGroups can be used to halt the main program's execution until all goroutines have completed
	//the WaitGroup manages the goroutines and knows exactly how long to halt the main thread to allow
	//all tagged goroutines enough time to complete their execution
	var wg sync.WaitGroup

	wg.Add(1)
	go func(msg string) {
		defer wg.Done()
		fmt.Println(msg)
	}("cloud")

	wg.Add(1)
	go func(msg string) {
		defer wg.Done()
		fmt.Println(msg)
	}("academy")

	wg.Wait() //will execute code below until wait group completes

	fmt.Println("end program")
}
