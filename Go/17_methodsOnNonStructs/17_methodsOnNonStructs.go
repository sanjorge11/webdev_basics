package main

import (
	"fmt"
	"strings"
)

type upstring string //non-struct data type

func (msg upstring) up() string {
	s := string(msg)
	return strings.ToUpper(s)
}

func main() {
	message := upstring("str")
	fmt.Println(message.up())
}
