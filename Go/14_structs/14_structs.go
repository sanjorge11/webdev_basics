package main

import "fmt"

type person struct {
	firstname string
	lastname  string
}

type lecture struct {
	name       string
	instructor person
	duration   int //seconds
}

func main() {
	lectures := []lecture{
		lecture{"Structs", person{"Jeremy", "Cook"}, 300},
		lecture{"Pointers", person{"Jeremy", "Cook"}, 300},
		lecture{"Functions", person{"Jeremy", "Cook"}, 300},
	}

	for _, lecture := range lectures {
		name := lecture.name
		instructor :=
			fmt.Sprintf("%s %s",
				lecture.instructor.firstname,
				lecture.instructor.lastname)
		duration := lecture.duration

		fmt.Printf("Lecture: '%s' Author: %s, Duration: %d secs\n",
			name, instructor, duration)
	}
}
