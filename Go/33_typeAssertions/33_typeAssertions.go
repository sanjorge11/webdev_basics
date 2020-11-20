package main

import "fmt"

//Go provides you with the ability to perform type assertions on interface types.
//A type assertion provides access to an interface's underlying concrete type.

//The type assertion "x. " asserts that the concrete value stored in x is of type T, and that x is not nil.

type company struct {
	name string
}

func main() {
	//the variable x is declared using the empty interface type and is then assigned an instance of the company struct type
	var x interface{} = company{"CloudAcademy"}

	c1 := x.(company) //short notation is used to cast x to its concrete type and assign the cast to the variable c1
	fmt.Println(c1)

	//demonstrate how to do the exact same cast but in a way that allows the application to keep running
	//should the cast fail. Here the type assertion is checked to see if the assertion was true or false.
	//If you don't use this technique to test your assumed type assertion and it turns out to be incorrect,
	//a runtime panic is thrown and the program could terminate if the panic isn't recovered from
	if c2, ok := x.(company); ok {
		fmt.Println(c2, ok)
	}

	//n := x.(int); n++ //runtime panic, this should result in a panic, since this particular cast will not work
}
