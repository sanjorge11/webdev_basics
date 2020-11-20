//https://play.golang.org/p/Kmvd8-dNXMh

package main

import "fmt"

func main() {
	var a = "https://play.golang.org/p/Kmvd8-dNXMh"
	fmt.Println("Visit " + a + " for the demo!")
}

//As previously mentioned, the Go language specification uses a naming convention whereby variables,
//constants, functions, et cetera, that you want to export and make available to be imported elsewhere
//are named with a starting capital letter.

//Anything that begins with a lowercase letter is determined to be unexported and therefore not importable.
//In other languages such as C# and Java, this type of control is implemented using dedicated modified keywords
//such as public and private.

//this example is a multi-file example, the double-dash lines are the demarkation points for the start
//and end of each file. You will only require these to model and test multi-file designs within the Go Playground.
//They are not considered part of the formal Go language syntax.

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

//unexported
func getX() int {
	return x
}

-- main.go --
package main

import (
	"github.com/cloudacademy/go/mod/demo/util"
	"fmt"
)

func main() {
	fmt.Println(util.X)
	//fmt.Println(util.x) //compile failure, NOT runtime failure - unexported

	fmt.Println(util.GetX())	//able to access because of uppercase
	//fmt.Println(util.getX()) //compile failure - unexported, not accessed because of lower case

}
*/
