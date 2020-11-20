package main

import "fmt"

func main() {
	fmt.Println("Read transcription from video in comments!")
}

/*
Transcription from Video:
Creating a new module is performed by running the command go mod init with the module name. This command should be performed within the module's root directory. For example, running the follow command creates and initializes a new module. The outcome of executing this command is a new go.mod file.

Taking a look at this file, we can see that it contains the following. Next, if we now run the command go get, the module dependencies that this module relies on as per the import package statements throughout the source code, will be scanned for and then downloaded. With the results then written back into the go.mod file, using one or several require directives. Let's try this out!

The result of running this command is that the module's dependencies would've been discovered, downloaded and written back into the go.mod file. Reexamining the go.mod file, we can see that it has now been updated. Additionally, a new go.sum file is generated and placed into the same directory containing the go.mod file. The go.sum file contains cryptographic checksums of each of the downloaded modules. This is used to maintain the integrity of the downloaded modules, ensuring that modules aren't either intentionally or unintentionally changed. The go in mod verify command can be invoked at any time there afterwards, to recompute and compare checksums, to determine modifications. When it comes to source code management, both the go.mod file and go.sum files should be committed into your source code repository.

The actual downloaded module dependencies do not themselves need to be added, since anyone with access to the go.mod and go.sum files can rebuild the local dependency cache. Mostly, the go.mod file can be left alone. You don't need to manually maintain it, unless you want to explicitly declare a dependency on a particular module version. Your typical coding workflow will involve creating new go source files, in which you add your required import statements.

Whenever you call go build, or go run, or go test, the new module dependencies will be detected, and automatically downloaded to satisfy the new import statements. When this happens, the go.mod and go.sum files will be automatically updated to reflect the latest dependency requirements.
*/
