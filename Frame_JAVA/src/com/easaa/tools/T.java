package com.easaa.tools;

import android.util.Log;

/**
 * 打log用
 */
public class T {

	public static final String TO_DEVICE_TAG = "to_device";

	public static final String CRASH_TAG = "crash";

	public static final String IMAGE_CACHE_TAG = "ImageCache";

	public static final String LAUNCH_TAG = "Launch";

	public static final String REQUSET = "REQUSET";

	public static final String EXCEPTION_TAG = "Exception";

	private static final boolean ENABLED = true;

	/**
	 * 开发版本
	 */
	public static final boolean isDebugVersion = true;

	/**
	 * tag白名单
	 */
	public static final String[] TAG_WHITE_LIST = {
		TO_DEVICE_TAG,
		CRASH_TAG
	};

	public static boolean isLogcatEnabled() {
		return ENABLED;

	}

	public static void v(String tag, String msg, Throwable tr) {
		if(!ENABLED) return;
		String ex = Log.getStackTraceString(tr);    
		if(isDebugVersion) Log.v(tag, msg+",ex:"+ex);
	}

	public static void d(String tag, String msg, Throwable tr) {
		if(!ENABLED) return;
		String ex = Log.getStackTraceString(tr);
		Log.d(tag, msg+",ex:"+ex);
	}

	public static void i(String tag, String msg, Throwable tr) {
		if(!ENABLED) return;
		String ex = Log.getStackTraceString(tr); 
		Log.i(tag, msg+",ex:"+ex);
	}

	public static void w(String tag, String msg, Throwable tr) {
		if(!ENABLED) return;
		String ex = Log.getStackTraceString(tr);    
		Log.w(tag, msg+",ex:"+ex);
	}

	public static void e(String tag, String msg, Throwable tr) {
		if(!ENABLED) return;
		String ex = Log.getStackTraceString(tr);  
		Log.e(tag, msg+",ex:"+ex);
	}

	public static void v(String tag, String msg) {
		if(!ENABLED) return;
		if(isDebugVersion) Log.v(tag, msg);
	}

	public static void d(String tag, String msg) {
		if(!ENABLED) return;
		Log.d(tag, msg);
	}

	public static void i(String tag, String msg) {
		if(!ENABLED) return;
		Log.i(tag, msg);
	}

	public static void w(String tag, String msg) {
		if(!ENABLED) return;
		Log.w(tag, msg);
	}

	public static void e(String tag, String msg) {
		if(!ENABLED) return;
		Log.e(tag, msg);
	}

	public static void e(Throwable e) {
		if(!ENABLED) return;

		e(EXCEPTION_TAG, "", e);
	}
}
