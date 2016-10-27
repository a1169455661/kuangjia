package com.easaa.main;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.easaa.crash.CrashHandler;
import com.easaa.tools.Constant;
import com.easaa.tools.DisplayUtil;
import com.easaa.tools.LogCommon;
import com.easaa.tools.LogFactory;
import com.easaa.widget.NetLoadingDialog;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class TApplication extends Application {

	/**
	 * 添加Activity的集合
	 */
	public static ArrayList<Activity> listActivity = new ArrayList<Activity>();
	public static NetLoadingDialog NetLoadingDialog;
	private static TApplication instance;
	public LogCommon Log = LogFactory.createLog();

	/**引导页图片集合*/
	public static List<String> director_Pic_Array = new ArrayList<String>();

	/** 请求队列 */
	public RequestQueue queue;

	/*
	 * 登陆bean
	 */
	// public ResponLoginuserBean loginbean;

	//	public ResponseRegistAndLoginBean loginbean() {
	//		ResponseRegistAndLoginBean loginbean = (ResponseRegistAndLoginBean) Helper_SharedPreferences
	//				.get_obj_sp(Constant.KEY_LOGIN_BEAN, getApplicationContext());
	//
	//		return loginbean;
	//
	//	};



	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		initImageLoader(getApplicationContext());
		queue = Volley.newRequestQueue(getApplicationContext());


		// 项目报错信息保存到手机SD卡
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());

	}


	public static TApplication getInstance() {
		return instance;
	}

	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 完全退出方法
	 */
	public static void exit() {
		for (Activity activity : listActivity) {
			activity.finish();
		}
		// TApplication.getInstance().loginbean=null;
		//		Helper_SharedPreferences.clear_sharePref(Constant.KEY_LOGIN_BEAN,
		//				getInstance().getApplicationContext());
		System.exit(0);
	}

	/**
	 * 获取应用程序的版本号
	 */
	public int getVersion() {
		int oldVesion = 0;
		try {
			oldVesion = getApplicationContext()
					.getPackageManager()
					.getPackageInfo(getApplicationContext().getPackageName(), 0).versionCode;
		} catch (Exception e) {
			Log.d("获取应用版本失败");
		}
		return oldVesion;
	}

	/**
	 * 得到应用的名称
	 */
	public static String getApplicationName(Context context) {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = context.getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		String applicationName = (String) packageManager
				.getApplicationLabel(applicationInfo);
		return applicationName;
	}

	/**
	 * 显示连接服务器加载进度框
	 */
	@SuppressLint("InlinedApi")
	public static void showProgressDialog(Context context) {
		if (NetLoadingDialog == null) {
			NetLoadingDialog = new NetLoadingDialog(context, R.string.wait, 0);
		}
		NetLoadingDialog.show();
	}

	/**
	 * 隐藏连接服务器加载进度框
	 */
	public static void dissmissProgressDialog() {
		if (NetLoadingDialog != null) {
			NetLoadingDialog.dismiss();
			NetLoadingDialog = null;
		}
	}

	/** 检测网络连接状态 */
	public boolean checkNetwork() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
						return true;
				}
		}
		return false;
	}

	/**判断wifi是否连接*/
	public  boolean  connectWifi(){
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}

	// 服务器错误
	public boolean isServerProblem(Object error) {
		return (error instanceof ServerError)
				|| (error instanceof AuthFailureError);
	}

	// 网络异常
	public boolean isNetworkProblem(Object error) {
		return (error instanceof NetworkError)
				|| (error instanceof NoConnectionError);
	}

	public String handleServerError(VolleyError error) {

		NetworkResponse response = error.networkResponse;

		if (response != null) {
			switch (response.statusCode) {
			case 404:
			case 422:
			case 401:
				return error.getMessage();

			default:
				return "服务器宕机";
			}
		}
		return "generic_error";
	}

	/** 初始化广告网点 */
	public void adDots(RadioGroup tabDot, int length, int position) {
		tabDot.clearCheck();
		tabDot.removeAllViews();
		int width = getApplicationContext().getResources()
				.getDrawable(R.drawable.gallery_radio_on).getIntrinsicWidth();
		int margin = DisplayUtil.dp2px(tabDot.getContext(), 4);
		for (int i = 0; i < length; i++) {
			RadioButton dots = new RadioButton(getApplicationContext());
			dots.setId(i);/* 设置指示图按钮ID */
			dots.setButtonDrawable(R.drawable.adv_dot);/* 设置背景图 */

			RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
					2 * width, width);/* 设置指示图大小 */
			layoutParams.leftMargin = layoutParams.rightMargin = margin;
			dots.setLayoutParams(layoutParams);
			tabDot.addView(dots);/* 把已经初始化的指示图动态添加到指示图的RadioGroup中 */
		}
		tabDot.check(position);
	}

	/**

	/* 裁剪图片的方法 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, Constant.IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		// startActivityForResult(intent, Constant.PHOTORESOULT);
	}

}
