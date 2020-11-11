package main

import "fmt"

func main() {
	var arr1[4] int //init with 0s

	arr2 := [...] int {3, 1, 2, 4, 6} //compiler infers array size

	fmt.Println(arr1);
	fmt.Println(arr2);

	//multidimensional arrays
	arr3 := [2][2] string {
		{"a1", "a2"}, 
		{"b1", "b2"},	//trailing comma necessary to build 
	}

	fmt.Println(arr3)

	//iterate arrays
	//underscore receives and discards the index value, we do this 
	//because we do not use the index variable, and go does not let 
	//you declare unused variables 
	for _, value := range arr2 { 
		fmt.Println(value)
	}
}