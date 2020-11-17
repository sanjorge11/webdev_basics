package main

import "fmt"

func system() int {
	fmt.Println("system started...")

	defer func(msg string) {
		if r := recover(); r != nil {
			fmt.Println("recovered")
		}
		fmt.Println(msg)
	}("blah")

	var data []int
	var x = data[0] //causes runtime panic, accessing an empty array
	//the deferred function above will then take program execution from the panic and since it calls the recover function
	//within itself, it enables the panic to be recovered from
	x++

	fmt.Println("system finished!")

	return 1
}

func main() {

	//because the system function return statement didn't get hit, the return value is received back
	//within the main function on the line below will be 0 and not 1 -- 0 being the zero value for the
	//data type int which is the data type for the return value of the system function
	data := system()
	fmt.Println(data)

	panic("die!") //this is the equivalent to throwing an exception
	//nothing handles this panic so program exists with non-zero exit code

	//if panics are not recovered from, it will cause program's execution to be exited with non-zero exit code --
	//the OS uses the code to determine that something abnormal has happened -- a panic can be recovered from if
	//the surrounding function in which the panic happened has a deferred function that itself calls the in-built
	//recover and then does something appropriately to resume
}
