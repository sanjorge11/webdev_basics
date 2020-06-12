import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Concurrency {
	
	private static int count = 0; 
	
	//Synchronizing Method 1
//	Lock lock = new ReentrantLock(); 
//	
//	public void increment() {
//		lock.lock();
//		
//		count++;
//		System.out.println(count);
//		
//		lock.unlock();
//	}

	//Synchronizing Method 2
//	public void increment() {
//		
//		synchronized(this) { 
//			count++;
//			System.out.println(count);
//		}
//		
//	}
	
	//Synchronizing Method 3
//	public synchronized void increment() {
//
//		count++;
//		System.out.println(count);
//		
//	}
	
	public void increment() {

		count++;
		System.out.println(count);
		
	}
	
	public static void main(String[] args) throws InterruptedException { 
		
		Concurrency c = new Concurrency();
		
		T t1 = new T(c); 	//creating a thread reference
		T t2 = new T(c); 
		
		t1.start();		//.start() spins up thread and runs it 
		t2.start(); 
		t1.run();		//.run() just executes thread
		t1.run();
		
	}
}

class T extends Thread { 
	
	Concurrency c; 
	Lock lock = new ReentrantLock(); 
	
	public T(Concurrency c) { 
		this.c = c; 
	}
	
	@Override
	public void run() { 
		c.increment();
	}
}

//Concurrency in multithreading refers to the idea of multiple threads doing the same task at the 
//exact same time, count ++ or count = count + 1. The example above shows how concurrency occurs 
//when multiple threads will increment the same global variable. When you use the non-synchronized 
//method you'll see that some values are printed more than once. In this case, more than one thread 
//will load the same count value and will increment 1 on that same value. Therefore, they all printed 
//the same result value because of the concurrent access to that variable. The fix to this problem is 
//to synchronize the method, which can be done with the synchronized keyword or by explicitly using 
//locks in the code. It is clear that it makes more sense to use multithreading when the tasks are
//mutually exclusive. However, if there is a code block that intersects when two different methods  
//are called, then synchronization can be applied to fix concurrency issues that arise. 