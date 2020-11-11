package main

import "fmt"

type player struct { //structs are like objects in Go
	id   int
	rank int
}

func main() {
	nums := []int{1, 3, 4, 2, 6, 8, 5, 4, 5, 3}

	for idx, value := range nums {
		fmt.Print(value)

		if len(nums)-1 == idx {
			fmt.Println()
		}
	}

	team := map[string]player{"John": {3, 10}, "Bob": {14, 11}}
	fmt.Println(team)

	for key, value := range team {
		fmt.Printf("%s -> %d\n", key, value)
	}

	//iterate using only value
	for _, value := range team {
		fmt.Println(value)
	}

	//iterate using only key
	for key := range team {
		fmt.Println("key:", key)
	}
}
