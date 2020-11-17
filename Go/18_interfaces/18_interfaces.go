package main

import (
	"fmt"
	"strings"
)

//an interface is a collection of one or more method signatures
//that describes the behavior of a type that implements it --
//iterfaces are abstract, meaning you cannot create instances
//directly from them, instead what you can do is declare variables
//with the interface type and then assign them to instances of types
//that happen to implement all the methods declared within the interface

//Go allows types to implicitly implement an interface if that type
//implements the full range of method signatures within the interface

type device interface {
	turnOn() string
	update(version float32)
}

type iphone struct {
	name    string
	model   string
	version float32
}

type imac struct {
	name    string
	model   string
	version float32
}

func (phone iphone) turnOn() string {
	return "iOS starting up..."
}

func (mac imac) turnOn() string {
	return "macOS starting up..."
}

//interfaces with pointer-based receivers provides us with the
//ability to update the data within the underlying struct,
//such that the changes persist beyonf the lifetime of the method call
func (phone *iphone) update(version float32) {
	//notice that deference is not used on this pointer object
	//this is because they are automatically dereferenced by the compiler
	//since the underlying type is a struct and dot notation is being
	//used on it -- this is syntactic sugar that the compiler knows about
	phone.version = version
}

func (mac *imac) update(version float32) {
	mac.version = version
}

func main() {
	dev1 := iphone{"iPhone", "11 Pro", 13.1}
	dev2 := imac{"iMac", "27 5k Retina", 10.15}

	//passes in mem addresses of these variables
	devices := []device{&dev1, &dev2}

	//example of polymorphism -- iphone an imac are of different
	//types but they share the same common device interface since
	//they both implement the same method signature as declared within
	//the device interface

	//this implicitly typing system is a useful tool and abstraction
	//mechanism that aides the overall development experience
	//when working with larges code bases within Go because
	//it helps create reusable and maintainable code
	for _, dev := range devices {
		if strings.Contains(dev.turnOn(), "iOS") {
			dev.update(14.0)
		} else if strings.Contains(dev.turnOn(), "macOS") {
			dev.update(11.0)
		}
	}

	fmt.Println(dev1)
	fmt.Println(dev2)
}
