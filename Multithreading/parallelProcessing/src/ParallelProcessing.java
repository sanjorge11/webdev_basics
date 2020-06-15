import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelProcessing {
	
	//Parallel processing is a type of computation in which many calculations or the execution 
	//of processes are carried out simultaneously. Large problems can often be divided into 
	//smaller ones, which can then be solved at the same time. 
	
	//https://en.wikipedia.org/wiki/Parallel_computing
	
	public static void main(String[] args) throws InterruptedException, ExecutionException { 
		
		//It is possible to create as many threads as you'd like in a for loop. 
		//However, the action of creating a thread is expensive. It is conventional 
		//to instead create a thread pool, a managed pool of a fixed number of threads. 
		//The pre-instantiated threads remain idle until the task queue assigns a task 
		//to any given idle thread. The size given to a thread pool varies on the 
		//task that needs to be completed. 
		
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println(cores);
		
		//initialize thread pool size same as amount of cores to ideally have 1 thread executed per core
		ExecutorService service = Executors.newFixedThreadPool(cores);	
		
		
		//Example: Use multithreading to find summation of 1 to 100. 
		int paritionSize = 100 % cores > 0 ? (100/cores + 1) : 100/cores; 
		
		List<Task> tasks = new ArrayList<>();
		for(int i=1; i<=cores; i++) { 
			
			final int partition = i; 
			
			tasks.add(new Task() {
				@Override
				public Integer call() throws Exception {	
					int localSum = 0; 
					int start = (partition-1)*paritionSize;;
					int end = (partition)*paritionSize; 
					for(int j=start+1; (end > 100 ? j<=100 : j<=end); j++) {
						localSum += j;
					}
					return localSum;
				}
			});
			
			System.out.println("added thread # " + i);
		}
		
		//A Future object is similar to a Promise is JavaScript. In this case, the Future 
		//object has an Integer return type. When a Future is completed, you can retrieve 
		//the returned value. We used Callable objects instead of Runnable objects to 
		//take advantage of the return type capability.
		
		//this is a blocking statement, the code after the statement will run when all Futures are done
		List<Future<Integer>> allFutures = service.invokeAll(tasks); 	
		int finalSum = 0; 
		for(Future<Integer> future : allFutures) finalSum += future.get();
		
		System.out.println(finalSum);		//Sum from 1-100 should be 5,050
	}
	
	public void ExampleWithoutWaitingForFuture() { 
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println(cores);
		
		ExecutorService service = Executors.newFixedThreadPool(cores);	
				
		int paritionSize = 100 % cores > 0 ? (100/cores + 1) : 100/cores; 
		
		//A Future object is similar to a Promise is JavaScript. In this case, the Future 
		//object has an Integer return type. When a Future is completed, you can retrieve 
		//the returned value. We used Callable objects instead of Runnable objects to 
		//take advantage of the return type capability.
		
		List<Future<Integer>> allFutures = new ArrayList<>();
		for(int i=1; i<=cores; i++) { 
			
			final int partition = i; 
			
			//service can have Callable or Runnable tasks as parameter
			//the Callable interface lets us use return types, whereas Runnable is void
			Future<Integer> future = service.submit(new Task() {
				@Override
				public Integer call() throws Exception {	
					int localSum = 0; 
					int start = (partition-1)*paritionSize;;
					int end = (partition)*paritionSize; 
					for(int j=start+1; (end > 100 ? j<=100 : j<=end); j++) {
						localSum += j;
					}
					return localSum;
				}
			});
		
											//this is a blocking statement,
			//Integer sum = future.get();   //code below will not run until Future is completed
			System.out.println("added thread # " + i);
			allFutures.add(future);
		}
		
	}

}

class Task implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {	//will be overridden above
		return null;
	}
	
}
