//https://play.golang.org/p/PZlq49vz59r

package main

import "fmt"

func main() {
	var a = "https://play.golang.org/p/PZlq49vz59r"
	fmt.Println("Visit " + a + " for the demo!")
}

/*
-- go.mod --
module github.com/cloudacademy/go

require github.com/google/uuid v1.1.1
//require github.com/jeremycook123/uuid v1.1.0	//1.0.3 version would fail since it does not implement GetDoubleUUID()

-- util/x.go --
package util

const x int = 100 //unexported

//exported
func GetX() int {
	return x
}

-- util/y.go --
package util

const y int = 200 //unexported

//exported
func GetY() int {
	return y
}

-- math/calc.go --
package math

import "github.com/google/uuid"

//exported
func Add(num1 int, num2 int) int {
	return num1 + num2
}

func GetUuid() uuid.UUID {
	return uuid.New()
}

-- main.go --
package main

import (
	"fmt"
	"github.com/cloudacademy/go/util"
	"github.com/cloudacademy/go/math"
)

//import "github.com/jeremycook123/uuid/utils"

func main() {
	x := util.GetX()
	y := util.GetY()

	fmt.Println(x)
	fmt.Println(y)

	sum := math.Add(x, y)
	uuid := math.GetUuid()

	fmt.Println(sum)
	fmt.Println(uuid)
	//fmt.Println(utils.GetDoubleUUID())	//from Jeremy's UUID module
}
*/

/*
Transcription from Video:
Go modules are the latest incarnation when it comes to code organization and dependency management, formally released as part of Go version 1.13.

In terms of hierarchy, a single module may contain one or multiple packages where each package may consist of one or many source files sharing the same package header statement. Modules help with a number of things, but most importantly modules allow you to organize related packages into a named module which can then be managed using the go get tool. The first requirement when creating your own module is to have a go.mod file located in the modules root folder. The go.mod file is a special file used by the module system to store metadata about the module itself. The go.mod file contains the name or identity of itself, specified as the first entry in the file using the module directive.

In total there are four possible directives that can be specified in the go.mod file. They are module, require, replace, and exclude. The require directive is used to specify any and all module dependencies that this module requires in terms of the packages that it imports. In the example provided here, the go.mod file declared on lines two to five declares that a module is being defined. In essence, a module is nothing but a directory containing Go packages. Additionally, this module declares that it has a module dependency on Google's publicly available UUID module and, in particular, version 1.1.1 as per the require directive on line four. This dependency exists due to the UUID package import statement contained within the math/calc.go file on line 29.

Now, if you were to navigate to the GitHub URL you would find the corresponding module source code. If you were to then examine the tags for this repo you would see that the latest semantic versioning tag applied is the v1.1.1 tag which precisely matches the semantic version number specified in the go.mod file. The Go toolchain is designed to find the matching tag in the Git repo and then use it to download it locally before a build takes place. This approach helps to ensure that builds are reproducible. If a semantic tag is not supplied, the latest version is downloaded. Running this example produces the following output. As you can see, the program has executed without any compilation or build errors and has printed out the following values.

The Globally Unique ID, or GUID at the bottom, is a string that has been returned from a call to the math.GetUuid function here on line 57. Now the point of showing you all of this is to describe what is happening at build time which when working with and demonstrating through the Go Playground happens behind the scenes. Before this program was able to be executed, the Go toolchain first consults the go.mod file to check for any stated dependencies. Each and every required dependency is this then downloaded and cached. In this case, the UUID module is pulled from its stated location. After the UUID module has been downloaded, a compilation of the source code is then performed, and assuming compilation succeeds, the resulting executable is then launched which has occurred here resulting in the output as previously highlighted.

To emphasize how the module system works in terms of semantic versioning, lets edit the current example by adding in a new module dependency. To do so I will update the go.mod file at the top and add in the following line. This is an example UUID module that I created solely for the purpose of highlighting how to work with specific semantic versions of modules. Next, I'll jump down to the main.go file and add in a new import statement which will import the utils package from within my UUID module. I'll then add in the following line at the bottom of the main function to invoke the GetDoubleUUID function and print out its result. Rerunning the application, we can see that it has indeed recompiled and executed successfully printing out a double sized UUID string. So let's now take a closer look at the implementation of this UUID module To do so, we'll navigate to the following GitHub URL. Again, we can see that this repository has within its root go.mod and go.sum files. These files are used to declare and manage the module setup and should always be checked in with the module source code.

Opening up the go.mod file we can see three things, the module directive, which states that this is a module and the module path, the language version of Go that the module was developed with. and a single module dependency, again, to Google's UUID module. Opening up the go.sum file, we can see the cryptographic checksum for Google's UUID module version 1.1.1. This ensures the integrity of the downloaded dependency and prevents modifications being made to it intentional or not. Drilling into the utils.go file within the utils directory we can see the implementation of both the GetUUID and GetDoubleUUID functions nice and easy. The next thing I'll do is navigate into releases where we can see all of the versioned releases of the module with the latest being the 1.1.0 version which also corresponds to the version we added into the go.mod file.

Clicking on the 1.1.0 release, we can then use the compare option to compare it to the previous 1.0.3 release. Here we can see clearly that the 1.1.0 minor update release extended the API by adding in the GetDoubleUUID function hence the reason for the semantic version minor release tag updating from 1.0.3 to 1.1.0. Jumping back into the Playground example, let's modify the go.mod file to use the previous 1.0.3 version. If we attempt to run this example now without further modification it should now fail since the 1.0.3 version did not implement the GetDoubleUUID function. And here we can see that indeed the build, or compilation phase has failed for the exact reason we just gave. Finally, let's now try a non-existing version of a module to see what happens. And now you can see that it fails as expected. Finally, rolling back the changes to the 1.1.0 version and rerunning the example again allows the build to complete and therefore the application to execute successfully.

Okay, in summary you have just observed how modules are declared and managed through the use of the mod.go and mod.sum files. That both the mod.go and mod.sum files should be checked in with your source code. How modules can be hosted on GitHub. How modules use semantic versioning to manage different versions. And how modules use the require directive declared within the go.mod file to stipulate module dependencies.
*/
