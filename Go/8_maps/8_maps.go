package main

import "fmt"

type player struct { //structs are like objects in Go
	id   int
	rank int
}

func main() {
	map1 := make(map[string]int) //key, value pair for map

	map1["key1"] = 1
	map1["key2"] = 2
	map1["key3"] = 3
	map1["key3"] = 4

	fmt.Println(map1)

	delete(map1, "key3")

	fmt.Println(map1)

	team := map[string]player{"John": {3, 10}, "Bob": {14, 11}}

	fmt.Println(team)
}
