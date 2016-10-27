package com.easaa.business.thread;

import java.util.HashMap;
import java.util.Map;



public class HandlerThreadFactory {
	public static final String TAG = "HandlerThreadFactory";

	/**
	 * 标准线程
	 * @备注:如果这个线程Looper里面的任务过重,就需要移到线程池去!!
	 */
	public static final String NormalThread = "Normal_HandlerThread";

	/**
	 * UI辅助线程
	 */
	public static final String RealTimeThread = "RealTime_HandlerThread";

	/**
	 * 专属线程：business looper线程
	 */
	public static final String BusinessThread = "Business_HandlerThread";

	/**
	 * 后台线程
	 */
	public static final String BackgroundThread = "Background_HandlerThread";

	/**
	 * 上传线程
	 */
	public static final String UploadThread = "Upload_HandlerThread";

	/**
	 * 线程优先级
	 * 
	 * @param type
	 * @return
	 */
	private static int getPriority(String type) {
		if (BackgroundThread.equalsIgnoreCase(type)) {
			return android.os.Process.THREAD_PRIORITY_BACKGROUND;
		} else if (RealTimeThread.equalsIgnoreCase(type)) {
			return android.os.Process.THREAD_PRIORITY_FOREGROUND;
		} else {
			return android.os.Process.THREAD_PRIORITY_DEFAULT;
		} 
	}


	private static Map<String, BaseThread> mHandlerThreadMap = new HashMap<String, BaseThread>();

	public static BaseThread getHandlerThread(String type, boolean isDaemon) {
		synchronized (mHandlerThreadMap) {
			BaseThread handlerThread = mHandlerThreadMap.get(type);
			if (null == handlerThread) {
				handlerThread = new BaseThread(type, getPriority(type));
				if (isDaemon) {
					handlerThread.setDaemon(isDaemon);
				}
				mHandlerThreadMap.put(type, handlerThread);
			}/*
			 * else { if (!handlerThread.isAlive()) { handlerThread.start(); } }
			 */
			return handlerThread;
		}
	}

	public static BaseThread getHandlerThread(String type) {
		return getHandlerThread(type, false);
	}

}
