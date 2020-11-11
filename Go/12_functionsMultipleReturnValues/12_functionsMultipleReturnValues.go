package main

import (
	"errors"
	"fmt"
)

func main() {
	if result, err := divide(10, 0); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(result)
	}
}

//either returns an int or an error object
//a common error handling pattern is to do this, either
//return a normal value or an error -- an error value defaults to nil
//when the function executes without any errors -- otherwise it returns
//any errors encountered during execution
func divide(num1 int, num2 int) (int, error) {
	if num2 == 0 {
		return 0, errors.New("division by zero not allowed")
	} else {
		return (num1 / num2), nil
	}
}
