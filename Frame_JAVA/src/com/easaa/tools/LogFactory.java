package com.easaa.tools;

public class LogFactory {
	private static final String TAG = "CH";
	private static LogCommon log = null;

	public static LogCommon createLog() {
		if (log == null) {
			log = new LogCommon();
		}

		log.setTag(TAG);
		return log;
	}

	public static LogCommon createLog(String tag) {
		if (log == null) {
			log = new LogCommon();
		}

		if (tag == null || tag.length() < 1) {
			log.setTag(TAG);
		} else {
			log.setTag(tag);
		}
		return log;
	}
}