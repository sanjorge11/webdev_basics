package main

import "fmt"

//There might be times where you can't always anticipate at compile time, the type of
//a functions input parameter. Often, this is the case when you're building a function that
//needs to be highly flexible in terms of what inputs it can take. In this case, the functions
//input parameter can be typed with the empty interface type, which implies that the input parameter
//can be of any type. This approach, as we know, gives up the type safety checks that the compiler gives
//us at compile time. Even though we might use this approach to provide us with the extra flexibility it
//affords us, we can still test the type inside the function using what's referred to as type assertions.

type cloud interface {
	launch() string
}

type aws struct {
	computeSvcName string
}

type azure struct {
	computeSvcName string
}

func (cloud aws) launch() string {
	return fmt.Sprintf("%s launching instance...", cloud.computeSvcName)
}

func (cloud azure) launch() string {
	return fmt.Sprintf("%s launching virtual machine...", cloud.computeSvcName)
}

//A type assertion provides access to an interface value's underlying concrete value. A type
//assertion is often used within the context of a type switch. Just like the normal switch statement
//that we have seen before, a type switch can be used to combine several type assertions together in a
//series, and evaluate to the first case with the correct matching type.

//The cloud input parameter for this function is declared using the empty interface type, meaning
//at compile time, the compiler can't perform any type safety checks when this function is called
//elsewhere in the application.
func compute(cloud interface{}) {
	switch cloudplatform := cloud.(type) { //set up type switch
	case aws:
		//the launch method is called to highlight polymorphism. Launch simply returns a string
		//indicating the cloud platform's compute service name and the thing that it is launching,
		//which for AWS is an EC2 instance, and for Azure is a virtual machine.
		aws := cloud.(aws)                                         //type casting
		fmt.Printf("AWS: %s -> %s\n", aws, cloudplatform.launch()) //polymorphism
		break
	case azure:
		azure := cloud.(azure)                                         //casting
		fmt.Printf("Azure: %s -> %s\n", azure, cloudplatform.launch()) //polymorphism
		break
	}
}

func main() {
	var clouds []cloud = []cloud{
		aws{"ec2"}, azure{"vm"},
	}

	for _, cloud := range clouds {
		compute(cloud)
	}

}
