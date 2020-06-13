package com.Multithreading.MutuallyExclusive.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Multithreading.MutuallyExclusive.dao.CalculationDAO;

@Service
public class CalculationService {
		
	@Autowired
	CalculationDAO calculationDAO;
	
	public int calculate(final int num) { 
		
		int currentNum = num; 
		
		//do these tasks in separate threads 
		Thread t1 = new Thread() {
			@Override
		    public void run() {
		        calculationDAO.addTwo(num);
		    }
		};
		
		Thread t2 = new Thread() {
			@Override
		    public void run() {
				calculationDAO.multiplyTwo(num);
		    }
		};
		
		//start and run the two threads
		t1.start(); 		//this starts and invokes the .run() method
		t2.start();
		
		//Using multithreading in the code above does not introduce any blocking code 
		//from getting to the code below. We do not wait for the two Dao calls to finish 
		//before getting the for loop below. They execute in different threads.
		
		for(int i=0; i<5; i++) { 
			currentNum = currentNum + (currentNum * (i+1));
		}
		
		
		return currentNum;
	}

}
