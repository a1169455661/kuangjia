package com.easaa.tools;

import android.util.Log;

public class LogCommon {

	private String tag = "CH";
	public static boolean isDebug = true;
	public static int logLevel = Log.VERBOSE;

	public LogCommon() {
	}

	public LogCommon(String tag) {
		this.tag = tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	private String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();

		if (sts == null) {
			return null;
		}

		for (StackTraceElement st:sts) {
			if (st.isNativeMethod()) {
				continue;
			}

			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}

			if (st.getClassName().equals(this.getClass().getName())) {
				continue;
			}

			return "["+Thread.currentThread().getId()+": "+st.getFileName()+":"+st.getLineNumber()+"]";
		}

		return null;
	}

	private void debug(Object str) {
		if(logLevel<=Log.DEBUG){
			String name = getFunctionName();
			String ls=(name==null?str.toString():(name+" - "+str));
			Log.i(tag, ls);
		}
	}

	public void d(Object str) {
		if (isDebug) {
			debug(str);
		}
	}

	public void info(Object str) {
		if (logLevel <= Log.INFO) {	        
			String name = getFunctionName();
			String ls=(name==null?str.toString():(name+" - "+str));
			Log.i(tag, ls);
		}
	}

	public void i(Object str) {
		if (isDebug) {
			info(str);
		}
	}

	public void verbose(Object str) {
		if (logLevel <= Log.VERBOSE) {
			String name = getFunctionName();
			String ls=(name==null?str.toString():(name+" - "+str));
			Log.v(tag, ls);    
		}
	}

	public void v(Object str) {
		if (isDebug) {
			verbose(str);
		}
	}

	public void warn(Object str) {
		if (logLevel <= Log.WARN) {
			String name = getFunctionName();
			String ls=(name==null?str.toString():(name+" - "+str));
			Log.w(tag, ls);
		}
	}

	public void w(Object str) {
		if (isDebug) {
			warn(str);
		}
	}

	public void error(Object str) {
		if (logLevel <= Log.ERROR) {            
			String name = getFunctionName();
			String ls=(name==null?str.toString():(name+" - "+str));
			Log.e(tag, ls);
		}
	}

	public void error(Exception ex) {
		if (logLevel <= Log.ERROR) {
			StringBuffer sb = new StringBuffer();
			String name = getFunctionName();
			StackTraceElement[] sts = ex.getStackTrace();

			if (name != null) {
				sb.append(name+" - "+ex+"\r\n");
			} else {
				sb.append(ex+"\r\n");
			}

			if (sts != null && sts.length > 0) {
				for (StackTraceElement st:sts) {
					if (st != null) {
						sb.append("[ "+st.getFileName()+":"+st.getLineNumber()+" ]\r\n");
					}
				}
			}

			Log.e(tag, sb.toString());
		}
	}

	public void e(Object str) {
		if (isDebug) {
			error(str);
		}
	}

	public void e(Exception ex) {
		if (isDebug) {
			error(ex);
		}
	}
}