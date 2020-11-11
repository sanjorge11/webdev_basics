package main

import "fmt"

func main() {

	x := 0
	y := 0

	//will loop until it hits the break 
	for {
		if x++; x > 2 {
			fmt.Println(x)
			break
		}
	}

	for y < 3 { 
		fmt.Println(y)
		y++
	}
	
	for z := 0; z < 10; z++ {
		if z < 8 {
			continue
		}
		fmt.Println(z)
	}
}