//https://play.golang.org/p/Zn130f09dQD

package main

import "fmt"

func main() {
	var a = "https://play.golang.org/p/Zn130f09dQD"
	fmt.Println("Visit " + a + " for the demo!")
}

/*
-- util.go --
package main

var x int = 11

-- main.go --
package main

import "fmt"

func print(id int, x int) {
	fmt.Printf("%d: x=%d\n", id, x)
}

func main() {
	print(1, x)		//goes up a scope to find x, prints 11

	x := 2

	print(2, x)		//the x := 2 shadows over the 11

	func(x int){
		print(3, x)			//the input parameter 5 shadows over 2
		if x := 3; x == 3 {
			//x := 100
			print(4, x)		//the x := 3 in if statement shadows over 2
		}
		print(5, x)		//out of the if statement scope, 5 shadows over 2
	}(5)

	print(6, x)		//out of function scope, 2 shadows over 5
}
*/

/*
Result:
1: x=11
2: x=2
3: x=5
4: x=3
5: x=5
6: x=2
*/
