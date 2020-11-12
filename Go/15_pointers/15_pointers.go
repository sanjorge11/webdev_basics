package main

import "fmt"

func main() {
	var num1 int = 100
	var num2 int = 200
	// var str1 string = "blah"

	var pointer1 *int = &num1 //& prefix gets the mem address of var

	//compile error because it's of type int pointer
	//var pointer1 *int = &str1

	fmt.Printf("mem address of num1 is %p\n", &num1)

	fmt.Printf("ptr1 points to mem address %p\n", pointer1)

	//* prefix de-references pointer to get the value
	//we change the value stored from 100 to 101
	//any other variable that points to the same location
	//will also see this update
	*pointer1 = 101
	fmt.Println(num1)

	//pointers can be updated to point to a new mem address
	pointer1 = &num2

	//build pointer with new(), it allocates just enough
	//memory for a pointer of that data type
	pointer2 := new(int)
	pointer2 = pointer1
	fmt.Println(*pointer2)

	msg := "cloudacademy"
	notString(msg)
	fmt.Println(msg) //does not have not as prefix, copy by value passed

	notStringPointer(&msg)
	fmt.Println(msg) //does have prefix added, copy by reference

	//using a copy by reference prevents the copy of parameter data
	//when attempting to copy by value, instead you can update the
	//data with the copy of the reference -- you can also directly
	//the variables inside the functions when called so that it is
	//ready to be used outside of the function

	//note that the zero value of a pointer is null
	//so any uninitialized pointer defaults to null --
	//there is NO pointer arithmetic in Go
}

func notString(msg string) {
	msg = fmt.Sprintf("not%s", msg)
}

func notStringPointer(msg *string) { //parameter is a pointer
	*msg = fmt.Sprintf("not%s", *msg)
}
