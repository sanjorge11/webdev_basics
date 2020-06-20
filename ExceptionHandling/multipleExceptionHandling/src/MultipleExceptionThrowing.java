import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;


public class MultipleExceptionThrowing {
	
	public static void main(String[] args) { 
		
		//Below are examples on how to handle multiple exceptions thrown from method call. 
		
		//For the .tryCatch() method
		
		try {
			tryCatch();
		} catch (IOException e) {	//works because of polymorphism, in other words, 
			e.printStackTrace();	//you can always pass a child class wherever a parent class is expected. 
		} 
		
		//or 
		
		try {
			tryCatch();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		//do not handle exceptions in the wrong order like below 
		
		try {
			tryCatch();
		} catch (IOException e) {		//will not work because the parent class will catch all exceptions
			e.printStackTrace();
		} catch (FileNotFoundException e) { 	//this child class exception will never be caught
			e.printStackTrace();
		} 
		
		try {
			tryCatch2();				//FileNotFoundException is already caught by IOException
		} catch (IOException | FileNotFoundException e) {	
			e.printStackTrace();
		} 
		
		
		//For the .tryCatch2() method
		
		try {
			tryCatch2();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		//or 
		
		try {
			tryCatch2();
		} catch (IOException | ParseException e) {		//multi-catch, will catch one of the two 
			e.printStackTrace();
		} 
	}
	
	public static void tryCatch() throws IOException { 

		//assuming we don't have a testFile.txt, this will cause an error 
		File file = new File("testFile.txt"); 
		
		if(file.getName() == null) { 
			throw new IOException("");
		}
		
		FileReader fileReader = new FileReader(file);	//will throw FileNotFoundException, subclass of IOException
		
		System.out.println("After try/catch");
	}
	
	public static void tryCatch2() throws FileNotFoundException, ParseException { 

		File file = new File("testFile.txt"); 
		
		if(file.isHidden()) { 
			throw new ParseException("Error when parsing", 0); 
		}
		
		FileReader fileReader = new FileReader(file);	
		
		System.out.println("After try/catch");
	}

}
