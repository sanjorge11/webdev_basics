import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

//Exception handling is responding to an error that arises when using 
//the program. If exceptions are not handled, then the program may crash. 
//All exceptions should be handled in a program. 

public class ExceptionHandling {
	
	//The top-level class that all exceptions extend is called Exception. 
	//Thrown exceptions may print a stack trace onto the program console or logs. 
	//The stack trace should be read from the top-down. The first error or exception 
	//thrown will be logged, and all subsequent errors caused by that initial error 
	//will be logged as well in chronological order. The stack trace may also likely 
	//contain the class name and line number in your written program where the error
	//occurred. 
	
	//If you need to create your own Exception, you can create the class that extends 
	//Exception and implement the methods that you wish to use from Exception or 
	//implement your own as well. 
	
	public static void main(String[] args) { 
		

		//this causes an error if thrown exception is not handled with either try/catch 
		//or by making the current method throw the exception -- exceptions will have to be 
		//handled somewhere down the stack of method calls, either with try/catch somewhere 
		//down the stack, or ultimately by having the main method throw the exception
		//and having the program crash 
		throwException(); 	
		
	}
	
	public static void throwException() throws FileNotFoundException { 	 
		//assuming we don't have a testFile.txt, this will cause an error 
		File file = new File("testFile.txt"); 	
		
		//method will throw exception if error occurs 
		
		FileReader fileReader = new FileReader(file); 
	}
	
	public static void tryCatch() { 
		//The catch block code will only execute if it caught the exception. The catch 
		//block could instead catch a more generic Exception object, if you wanted to catch 
		//all exception types there, although this is bad practice. 
		
		File file = new File("testFile.txt"); 	
		
		try {
			FileReader fileReader = new FileReader(file);
			
			//any code under here will not be executed if statement above throws exception 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//any other code can be added here to handle exception: 
			//more business logic, print statements, return statements, or throw another exception
		} 
		
		//this code will execute right after try block, or if an exception was caught, 
		//right after the catch block code is done executing 
		System.out.println("After try/catch");
	}

}
