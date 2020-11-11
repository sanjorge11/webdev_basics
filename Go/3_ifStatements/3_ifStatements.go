package main

import "fmt"

func main() {

	var num = 10

    //example below declares/initializes variable within if statement
    if toggle := true; toggle && num == 10 { 
        fmt.Println("Toggle on")
    }

    if num < 100 { 
        fmt.Println("num is less than 100");
    }

    if num >= 0 && num <= 100 { 
        fmt.Println("num is between 0 and 100");
    }
}