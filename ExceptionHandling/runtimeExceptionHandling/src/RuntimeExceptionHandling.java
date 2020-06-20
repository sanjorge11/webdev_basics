
public class RuntimeExceptionHandling {
	
	//There are two main type of exceptions: checked and unchecked. Checked exceptions are 
	//caught in compile time and the IDE will force you to handle it with try/catch blocks
	//or by throwing an exception. Unchecked exceptions are not caught in compile time, 
	//they arise in runtime. Those types of exceptions usually point to serious 
	//fundamental issues with your program.

	public static void main(String[] args) {
		
		int num = 100; 
		
		int quotient = 0; 
		
		try { 
			quotient = num / 0; 			//dividing by 0 causes an ArithmeticException 
		} catch(ArithmeticException e) { 	//a subclass of RuntimeException
			System.out.println("Caught ArithmeticException"); 	//you could catch the specific ArithmeticException
		}
		
		//or 
		
		try { 
			quotient = num / 0; 
		} catch(RuntimeException e) { 		//catch the generic top-level parent class RuntimeException
			System.out.println("Caught RuntimeException");
		}
		
		//or 
		
		try { 
			quotient = num / 0; 
		} catch(Exception e) { 			//a RuntimeException is a subclass of Exception 
			System.out.println("Caught Exception");	
		}
		
		//Note that it is generally not good practice to handle RuntimeExceptions, because if the program 
		//were to throw that exception, then it means it has a serious flaw that needs to be resolved.
		//Production-ready applications should be well-tested to catch those before deployment. 
		//RuntimeExceptions could, for instance, point to a NullPointerException that will need to 
		//be fixed for the next software update -- instead of having a catch block that allows the 
		//program to continue with that serious flaw that could have other serious implications. 
		//Nonetheless, there'a nothing wrong with catching the exception if you have something useful 
		//to do with the caught exception. Catching all types of exceptions is still possible. 

	}

}
