package main

import "fmt"

func main() {

	//unlike Java, you don't need a break statement -- 
	//once a matching case is found, it will break 
    switch signal := 1; signal { 
		case 0: 
		fmt.Println("reg")
		case 1: 
		fmt.Println("orange")
		case 2: 
		fmt.Println("green")
	}
	var score int = 63
	switch {
	case score <= 25: 
	fmt.Println("beginner")

	case score <= 75: 
	fmt.Println("pro")

	default: 
	fmt.Println("expert")
	}
}