package main

//Writing Go code typically requires functionality declared from within other packages. To enable
//the use of exported types and/or functions declared and contained within other packages, whether
//they are provided as part of the standard Go library, or by 3rd party providers, you use the import
//statement to import them into the current package. The import keyword is followed by the import path
//of the package provided in double quotes.

//A package's import path is its module path joined with its subdirectory within the module itself. All of
//the standard library packages can be imported using just their package name only. There is no need to declare
//the module path, since the compiler already knows where to find them based on the local Go tool chain installation
//and configuration.

//The import keyword can be used to import one or multiple packages at a time. When multiple packages are imported,
//the import keyword only needs to be specified once, with the packages being listed out within parenthesis. This
//style helps by making the code more readable.

//to import 3rd party import, type in Terminal:
//go get github.com/google/uuid

import (
	"fmt"
	"strconv"
	"strings"

	"github.com/google/uuid"

	m "math"
)

//Imported packages can also be aliased to avoid package naming conflicts. In the case that there are two packages,
//which share the same name but for which come from different locations and implement different things, an alias
//can be assigned to one or both to ensure that when their functionality is called upon, the right code gets compiled
//and executed.

// import "strconv"
// import m "math"

func main() {
	name := "cloudacademy"

	//Now, if you look closely at all of the functions that are invoked on any of the imported packages, you will
	//see that they all begin with a capital letter. It is this convention that actually makes
	//them exported, meaning that they can be seen from outside of the package that they were declared within and
	//can be imported into and invoked from within other packages.
	fmt.Println(strings.ToUpper(name))
	fmt.Println(uuid.New())
	f, _ := strconv.ParseFloat("3.1415", 64)
	fmt.Println(f)
	fmt.Println(m.Round(f))
}
