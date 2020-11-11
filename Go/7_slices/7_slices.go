package main

import "fmt"

func main() {

	//slices build on top of arrays, the main addition is 
	//that they are resizable -- you can expand/contract them 
	//at runtime, because of this slices are used more than arrays

	arr1 := [6] int {1, 3, 5, 2, 6}
	slice1 := [] int {1, 3, 5, 2, 6}
	slice2 := slice1[1:3]	//from index 1 to 3 (non-inclusive)
	//slice2 should be [3, 5]

	//remember that arrays, slices are 0-indexed 


	//creating slice with make function 
	//parameters: type, length, capacity 
	var slice3 = make([]int, 2, 3)

	fmt.Println(arr1) 
	fmt.Println(slice1) 
	fmt.Println(slice2) 
	
	fmt.Println(slice3) 
	fmt.Println(len(slice3)) 
	fmt.Println(cap(slice3)) 

	//The length of a slice is the number of elements it contains.
	//The capacity of a slice is the number of elements in the 
	//underlying array, counting from the first element in the slice.
	slice3 = append(slice3, 1, 2)
	fmt.Println(slice3) 
	fmt.Println(len(slice3)) 
	fmt.Println(cap(slice3)) 

	slice3 = append(slice3, []int{7, 8, 9}...)
	fmt.Println(slice3) 

	var slice3Copy = make([]int, len(slice3))
	copy(slice3Copy, slice3)
	fmt.Println(slice3Copy) 
}