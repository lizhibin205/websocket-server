package com.bytrees.chat.ws.task;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.util.concurrent.FastThreadLocalThread;

public class ThreadFactoryImpl implements ThreadFactory {
	private final String threadName;
	private final AtomicInteger nextId = new AtomicInteger();

	public ThreadFactoryImpl(String threadName) {
		this.threadName = threadName;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new FastThreadLocalThread(r, threadName + '-' + nextId.getAndIncrement());
		thread.setDaemon(true);
		return thread;
	}
}
