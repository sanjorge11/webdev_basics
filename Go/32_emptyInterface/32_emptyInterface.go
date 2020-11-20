package main

import "fmt"

//The empty interface type in Go denoted by the keyword interface and empty curly brackets
//is used to indicate that a variable can hold values of any type, since by definition every
//type implements at least zero methods. The empty interface is often useful when you need to
//accommodate an unknown set of types that a function may consume.

// Being able to call a function with as many variables, of differing types, helps to reduce
//code bloat by condensing and generalizing many functions into a singular multipurpose function.
//Compiler type safety checking is given up when taking this approach, so you need to consider your
//requirements carefully when doing this.

type company struct {
	name string
}

func main() {
	var a, b, c, d interface{}

	a = 42
	b = "blah"
	c = true
	d = company{"cloudadacademy"}

	func(list ...interface{}) {
		for _, v := range list {
			fmt.Printf("%v, %T\n", v, v)
		}

	}(a, b, c, d)
}
