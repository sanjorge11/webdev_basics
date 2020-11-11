package main

import "fmt"

func main() {

	//below is an exmaple of an anonymous function
	//notice that you can define functions within functions in Go
	result := func(num1 int, num2 int) int {
		sum := Sum(num1, num2)
		minus := minus(num1, num2)

		return sum * minus
	}(5, 3)

	fmt.Println(result)
}

//functions that start with upper case are considered public
//and they can be called from outside of the package
//functions that begin with lower case letter can only be
//called from within the current package file
func Sum(num1 int, num2 int) int {
	result := num1 + num2
	return result
}

func minus(num1, num2 int) (result int) {
	//does not need := because result is already declared above
	//(result int)
	result = num1 - num2

	//it is implied that the named return value above is what's returned
	return
}
