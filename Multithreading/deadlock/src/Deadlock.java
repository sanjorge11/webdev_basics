
public class Deadlock {
	
	//This is an example of a deadlock in Java code. When running the program, you'll see that the 
	//first thread is waiting on the second thread to release the lock on count2. Whereas the 
	//second thread is waiting for the first thread to release the lock on count1. 
	
	//Tips for preventing deadlocks: 
	//	1. Avoid nested locks, like the ones seen below
	//	2. Adding .join() to impose a max time on a lock 
	//	3. Avoid adding too many unnecessary locks 
	
	//For more info, see: https://www.geeksforgeeks.org/deadlock-in-java-multithreading/
	
	public static void main(String[] args) throws InterruptedException {
		
		Integer count1 = 10;
		Integer count2 = 20; 
		
		Thread t1 = new Thread() {
			@Override
			public void run() { 
				while(true) {
					synchronized(count1) { 	//put a lock on count1 object 
						try {
							System.out.println(Thread.currentThread().getName() + " locked count1");
							Thread.sleep(100);		//100ms 
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
						
						synchronized(count2) { 
							System.out.println(Thread.currentThread().getName() + " locked count2");
							System.out.println(count1 + count2);
						}
					}
				}
			}
		}; 
		
		Thread t2 = new Thread() {
			@Override
			public void run() { 
				while(true) {
					synchronized(count2) { 
						System.out.println(Thread.currentThread().getName() + " locked count2");
						
						synchronized(count1) { 
							System.out.println(Thread.currentThread().getName() + " locked count1");
							System.out.println(count1 + count2);
						}
					}
				}
			}
		}; 
		
		
		
		
		t1.start();
		t2.start();
	}

}
