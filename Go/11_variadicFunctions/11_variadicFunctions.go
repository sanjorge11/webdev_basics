package main

import "fmt"

func main() {

	//variadic functions have a variadic parameter
	//which is compiled as a slice of a variable number of params
	//... idicates the varadic parameter should be the last in signature

	displayCount(1, "c", "l", "o", "u", "d")

	cloud2 := []string{"c", "l", "o", "u", "d", "2"}
	displayCount(3, cloud2...)
	//... unpacks cloud2 so that it is passed as a slice

}

func displayCount(id int, letters ...string) {
	count := 0
	for range letters {
		count++
	}

	//display id, letters count, letters type, and letters content
	fmt.Printf("%d - %d - %T - %s\n", id, count, letters, letters)
}
