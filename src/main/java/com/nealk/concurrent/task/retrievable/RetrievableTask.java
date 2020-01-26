package com.nealk.concurrent.task.retrievable;

import com.nealk.concurrent.ThreadSafe;
import com.nealk.concurrent.task.Task;

public abstract class RetrievableTask<T> implements Task{
	
	protected volatile T obj;
	private java.util.concurrent.Semaphore objSem;
	
	protected RetrievableTask(){
		this.objSem = new java.util.concurrent.Semaphore(0, false);
	}

	@Override
	public final void run() {
		System.out.println("Thread " 
							+ Thread.currentThread().getId()
							+ " is running...");
		this.execute();
		this.objSem.release();
	}
	
	protected abstract void execute();

	/**
	 * Blocks until {@link obj} of type &ltT&gt is not null. Returns the {@link obj}.
	 * </br></br>
	 * Note that compile-time type checking is enabled. The usage 
	 * of Java Generics nullifies the compiler warning: {@link unchecked}.
	 */
	@SuppressWarnings("unchecked") 
	@Override 
	@ThreadSafe
	public final T getVal() throws InterruptedException {
		this.objSem.acquire();
		return obj;
	}

}
