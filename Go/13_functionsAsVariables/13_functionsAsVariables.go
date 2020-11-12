package main

import "fmt"

func extend(val string) func() string {
	//example of closure, this i is saved for this particular instance
	//saved to word variable below -- the other variable word2
	//has its own i variable value bound to it
	i := 0
	return func() string { //anonymous function
		i++
		return val[:i] //substring from 0 to i
	}
}

func main() {
	ca := "cloudacademy"

	word := extend(ca)
	word2 := extend(ca)

	fmt.Println(word2())

	for i := 0; i < len(ca); i++ {
		fmt.Println(word())
	}
}
