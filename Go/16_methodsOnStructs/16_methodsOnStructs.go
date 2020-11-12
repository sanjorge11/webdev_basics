package main

import "fmt"

type person struct {
	firstname string
	lastname  string
	age       int
}

//the item after 'func' is the receiver argument
//it is usually a struct type, but it could also be a non-struct type
//it could be a pointer or non-pointer type -- not that
//non-pointer types are usually used if the method is read-only
//and using a pointer type is optimal for methods that right
//to avoid the copy by value overhead cost
func (p person) fullname() string {
	return p.firstname + " " + p.lastname
}

func (p person) canDrive() bool {
	if p.age >= 20 {
		return true
	} else {
		return false
	}
}

func (p *person) updateAge(newAge int) {
	p.age = newAge
}

func main() {

	person := person{"John", "Doe", 21}
	fmt.Printf("%s can drive: %t\n", person.fullname(), person.canDrive())
}
