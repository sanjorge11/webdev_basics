package main

import "fmt"

func main() {
    fmt.Println("Hello World!")

    var num = 11

    //example below declares/initializes variable in if statement
    if toggle := true; toggle && num == 10 { 
        fmt.Println("Toggle on")
    }
}