package com.easaa.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.easaa.main.TApplication;
import com.easaa.response.bean.BasePageEntity;
import com.google.gson.Gson;

public class HttpUtils {

	private static HttpUtils _instance;

	private static ExecutorService executorService = Executors.newFixedThreadPool(10);

	private HttpUtils() {

	}

	/**
	 * 网络请求工具类
	 * （有对话框的情况）
	 * 调用请求成功后一定要执行TApplication.dissmissProgressDialog()关闭对话框
	 * @param url
	 * @param c
	 * @param params
	 * @param callback
	 */
	public <T extends BasePageEntity> void getData(Context context,String url, Class<T> c,
			Map<String, Object> params, CallBack<T> callback) {
		TApplication.showProgressDialog(context);
		SimpleThread<T> thread = new SimpleThread<T>();
		thread.setUrl(url);
		thread.setC(c);
		thread.setCallback(callback);
		thread.setParams(params);
		executorService.execute(thread);
	}

	/**
	 * 网络请求工具类
	 * （无对话框的情况）
	 * @param url
	 * @param c
	 * @param params
	 * @param callback
	 */
	public <T extends BasePageEntity> void getDataNodialog(String url, Class<T> c,
			Map<String, Object> params, CallBack<T> callback) {
		SimpleThread<T> thread = new SimpleThread<T>();
		thread.setUrl(url);
		thread.setC(c);
		thread.setCallback(callback);
		thread.setParams(params);
		executorService.execute(thread);
	}

	public static HttpUtils getInstance() {
		if (_instance == null) {
			_instance = new HttpUtils();
		}
		return _instance;
	}
	
	/**
	 * 发送网络请求，获取返回的Json格式字符串
	 * @param url
	 * @param params
	 * @return
	 */
	public String getHttpStr(String url, Map<String, Object> params) {
		try {
			url = new String(url.getBytes(), "utf-8");
			URL _url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
			conn.setConnectTimeout(20000);
			conn.setRequestMethod("POST");
			if(params!=null){
				StringBuilder sb = new StringBuilder();
				Set<String> keys = params.keySet();
				for (String string : keys) {
					sb.append(string).append("=").append(params.get(string))
					.append("&");
				}

				OutputStream os = conn.getOutputStream();
				os.write(sb.toString().getBytes());
				os.flush();
				os.close();
			}
			// if(conn.getResponseCode()==200){
			StringBuilder resultData = new StringBuilder("");
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			// 使用缓冲一行行的读入，加速InputStreamReader的速度
			BufferedReader buffer = new BufferedReader(isr);
			String inputLine = null;

			while ((inputLine = buffer.readLine()) != null) {
				resultData.append(inputLine);
				resultData.append("\n");
			}
			buffer.close();
			isr.close();
			conn.disconnect();
			return resultData.toString();
			// }else{
			// return null;
			// }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getHttpPOSTStr(String url) {
		try {
			url = new String(url.getBytes(), "utf-8");
			System.out.println("url="+url);
			URL _url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
			conn.setConnectTimeout(20000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			if (conn.getResponseCode() == 200) {
				StringBuilder resultData = new StringBuilder("");
				InputStreamReader isr = new InputStreamReader(
						conn.getInputStream());
				// 使用缓冲一行行的读入，加速InputStreamReader的速度
				BufferedReader buffer = new BufferedReader(isr);
				String inputLine = null;

				while ((inputLine = buffer.readLine()) != null) {
					resultData.append(inputLine);
					resultData.append("\n");
				}
				buffer.close();
				isr.close();
				conn.disconnect();
				return resultData.toString();
			} else {
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public interface CallBack<T extends BasePageEntity> {
		public void handleMessage(T object);
	}

	class SimpleThread<T extends BasePageEntity> extends Thread {
		private String url = null;
		private Class<T> c;
		private CallBack<T> callback;
		private Map<String, Object> params;

		public Map<String, Object> getParams() {
			return params;
		}

		public void setParams(Map<String, Object> params) {
			this.params = params;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Class<T> getC() {
			return c;
		}

		public void setC(Class<T> c) {
			this.c = c;
		}

		public CallBack<T> getCallback() {
			return callback;
		}

		public void setCallback(CallBack<T> callback) {
			this.callback = callback;
		}

		@SuppressLint("HandlerLeak")
		private Handler handler = new Handler() {
			@SuppressWarnings("unchecked")
			public void handleMessage(android.os.Message msg) {
				if (callback != null) {
					callback.handleMessage((T) msg.obj);
				}
			};
		};
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String json = getHttpStr(url,params);
			T bean = getJsonbean(json, c);
			Message msg = new Message();
			msg.obj = bean;
			handler.sendMessage(msg);
		}
	}

	private <T extends BasePageEntity> T getJsonbean(String json, Class<T> c) {
		T object = null;
		if (!TextUtils.isEmpty(json)) {
			try {
				object = new Gson().fromJson(json, c);
			} catch (Exception e) {
				try {
					object = c.newInstance();
					object.msg = "数据解析异常";

					object.result = false;
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			try {
				object = c.newInstance();
				object.msg = "网络异常";
				object.result = false;
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return object;
	}

	/**
	 * 服务器返回码显示
	 * @param result
	 */
	public static void HttpResult(Context context,String result){

		int resultindex=0;
		try {
			if(!TextUtils.isEmpty(result)){
				resultindex=Integer.valueOf(result);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		switch (resultindex) {
		case 0:
			Toast.makeText(context, "网络错误!", Toast.LENGTH_LONG).show();
			break;
		case 2:
			return;
			//Toast.makeText(context, "返回空值!", Toast.LENGTH_LONG).show();
		case 3:
			Toast.makeText(context, "请求协议参数不完整!", Toast.LENGTH_LONG).show();
			break;
		case 4:
			Toast.makeText(context, "用户名或密码错误!", Toast.LENGTH_LONG).show();
			break;
		case 5:
			Toast.makeText(context, "FKEY验证失败!", Toast.LENGTH_LONG).show();
			break;
		case 7:
			Toast.makeText(context, "客户端价格计算错误！", Toast.LENGTH_LONG).show();
			break;
		case 8:
			Toast.makeText(context, "库存不足!", Toast.LENGTH_LONG).show();
			break;
		case 9:
			Toast.makeText(context, "系统错误!", Toast.LENGTH_LONG).show();
			break;
		case 10:
			Toast.makeText(context, "网络错误!", Toast.LENGTH_LONG).show();
			break;
		}
	}

}
