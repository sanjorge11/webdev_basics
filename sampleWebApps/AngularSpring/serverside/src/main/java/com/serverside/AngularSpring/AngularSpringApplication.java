package com.serverside.AngularSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AngularSpringApplication {

	//the outputPath of angular build was updated in angular.json 
	//so that the files are served in the static folder 
	public static void main(String[] args) {
		SpringApplication.run(AngularSpringApplication.class, args);
	}

}
