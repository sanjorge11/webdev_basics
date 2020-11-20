//https://play.golang.org/p/2xzo2Ho9XiU

package main

import "fmt"

func main() {
	var a = "https://play.golang.org/p/2xzo2Ho9XiU"
	fmt.Println("Visit " + a + " for the demo!")
}

//When it comes to understanding the visibility of variables, constants, and/or functions et cetera,
//it's important to understand that any of them declared outside of any function are visible across
//the entire package in which they were declared, regardless of whether their names are capitalized or not.

//To put it another way, all files that belong to the same package have visibility of them. This is irrespective
//of the exporting naming convention previously reviewed. Therefore, any variable, constant, and/or function whose
//name starts with either a lowercase or uppercase character will be visible and available in all other source files
//that share the same starting package name statement.

//note that both the x.go and y.go files belong to the same util package.
//Therefore, each can see all of the each other's variables, constants, and/or functions et cetera,
//that have been declared outside of any other function.

/*
-- go.mod --
module github.com/cloudacademy/go/mod/demo

-- util/x.go --
package util

const X int = 100 //exported
const x int = 200 //unexported

//exported
func GetX() int {
	return x
}

//exported
func GetXY() int {
	return x * getY() //visible due to being in same package
}

-- util/y.go --
package util

const y int = 300 //unexported

//unexported, but visible to other other files in same package
func getY() int {
	return y
}

-- main.go --
package main

import (
	"github.com/cloudacademy/go/mod/demo/util"
	"fmt"
)

func main() {
	fmt.Println(util.GetXY())		//becomes available when importing util

	//fmt.Println(util.getY()) //compile failure - unexported
}
*/
