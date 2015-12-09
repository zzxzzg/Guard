package com.guard.utillib;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
    private static ExecutorService mThreadPoll;
	private static ExecutorService mThreadPollFixed;
	private static ExecutorService mSingleThread;
    private static ScheduledExecutorService mTimerService;

	static {
	    mThreadPollFixed = Executors.newFixedThreadPool(5);
		mTimerService = Executors.newScheduledThreadPool(5);
		mSingleThread = Executors.newSingleThreadExecutor();
		mThreadPoll = Executors.newCachedThreadPool();
	}
	
	public static void executeSingleThread(Runnable command){
		mSingleThread.execute(command);
	}
	
	public static ExecutorService executeSingleThread(Runnable command,ExecutorService singleThreadService ){
		if(singleThreadService == null ){
			singleThreadService =  Executors.newSingleThreadExecutor();
		}
		singleThreadService.execute(command);
		return singleThreadService;
	}
	
	public static void execute(Runnable command){
	    mThreadPoll.execute(command);
	}
	
	public static void execute(Runnable command,boolean limit){
	    if(limit){
	        mThreadPollFixed.execute(command);
	    }else{
	        mThreadPoll.execute(command);
	    }
       
    }
	
	public static<T> Future<T> submit(Callable<T> callable){
		return mThreadPollFixed.submit(callable);
	}
	
	public static Future<?> submit(Runnable task){
		return mThreadPollFixed.submit(task);
	}
	
	public static<T> Future<T> submit(Runnable task, T result){
		return mThreadPollFixed.submit(task,result);
	}
	
	public static ScheduledFuture<?> schedule(Runnable command,long delay){
    	return mTimerService.schedule(command, delay, TimeUnit.MILLISECONDS);
    }
	
	public static<V> ScheduledFuture<V> schedule(Callable<V> command,long delay){
    	return mTimerService.schedule(command, delay, TimeUnit.MILLISECONDS);
    }
	
	public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command,long initialDelay, long period){
    	return mTimerService.scheduleAtFixedRate(command, initialDelay, period, TimeUnit.MILLISECONDS);
    }
	
	public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,long initialDelay, long period){
    	return mTimerService.scheduleWithFixedDelay(command, initialDelay, period, TimeUnit.MILLISECONDS);
    }
}
