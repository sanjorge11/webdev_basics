package main

import "fmt"

func doSomething(msg string) {
	fmt.Println(msg)
}

func system() int {
	fmt.Println("system started...")

	defer doSomething("cleanup")
	defer doSomething("stop")

	fmt.Println("system finished!")

	return 1
}

func main() {
	//defer statements are used to defer the execution of
	//a function until the currently running and surrounding
	//function's execution returns -- a motivation for using
	//defer could be when you're working with or processing
	//external resources, for example, files on a file system
	//or database connections

	//An example would be a requirement
	//to open a file and manipulate it, by calling defer
	//immediately after opening it, you can be assured that it gets
	//closed and written back to the file system once the surrounding
	//function or method completes, regardless of the execution
	//path taken within it -- calling defer also has the added benefit
	//of insuring that you don't unintentionally leak resources
	//due to unforseen program flow, since your resource management
	//is explicitly declared  right from the start

	// using the defer statements ensures that the deferred function
	//is always called regardless of the execution path within the
	//surrounding function -- it's also possible to stack up multiple
	//defer statements and then have them processed in LIFO order
	//after the surrounding function returns
	data := system()
	fmt.Println(data)
}
