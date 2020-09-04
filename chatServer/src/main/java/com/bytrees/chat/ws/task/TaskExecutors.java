package com.bytrees.chat.ws.task;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行任务的线程池
 *
 */
public class TaskExecutors {
	private static final Logger logger = LoggerFactory.getLogger(TaskExecutors.class);

	/**
	 * 业务逻辑线程池
	 */
	private ThreadPoolExecutor threadPoolExecutor;

	/**
	 * @param corePoolSize  核心线程池大小
	 * @param maximumPoolSize 最大线程池大小
	 * @param keepAliveTime 线程最大空闲时间
	 * @param unit 时间单位
	 */
	public TaskExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
		threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, //核心线程池大小
			maximumPoolSize,          //最大线程池大小
			keepAliveTime,            //线程最大空闲时间
			unit,  //时间单位
			new SynchronousQueue<Runnable>(false),         //线程等待队列
			new ThreadFactoryImpl("Chat-Server-Business")   //线程创建工厂
		);
		logger.info("create task worker thread pool executor.");
	}

	/**
	 * 执行任务
	 */
	public void execute(Runnable r) {
		try {
			threadPoolExecutor.execute(r);
		} catch (RejectedExecutionException ex) {
			logger.error("Chat-Server-Business Overload.");
		}
	}
}
