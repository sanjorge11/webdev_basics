package main

import (
	"errors"
	"fmt"
)

//same example as 12_functionsWithMultipleReturnValues
func main() {
	//here we see if the result is an error, if so, handle it
	if result, err := divide(10, 0); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(result)
	}
}

//returns either true value or an error
//notice the multi-return value
func divide(num1 int, num2 int) (int, error) {
	if num2 == 0 {
		return 0, errors.New("division by zero not allowed")
	} else {
		return (num1 / num2), nil
	}
}
