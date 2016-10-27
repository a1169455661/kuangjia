package com.easaa.widget;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import cn.bidaround.ytcore.YtCore;
import cn.bidaround.ytcore.YtShareListener;
import cn.bidaround.ytcore.data.ShareData;
import cn.bidaround.ytcore.data.YtPlatform;
import cn.bidaround.ytcore.util.YtToast;

import com.easaa.main.R;
import com.easaa.tools.ScreenUtils;
/**分享对话框*/
public class OrderShareDialog extends Dialog implements 
android.view.View.OnClickListener{

	/**分享图片（http://manage.meijinguanjia.com/upload/logo.png）*/
	/**分享链接（http://www.meijinguanjia.com/login.aspx?oid=订单ID&mid=会员ID）*/

	ShareData shareData  = new ShareData();

	private Activity activity;
	public String share_Content;
	public String order_Id;
	public OrderShareDialog(Context context, int theme) {
		super(context, theme); 
	}

	public OrderShareDialog(Activity activity) {
		this(activity, R.style.App_Share_Dialog);
		this.activity=activity;
	}

	public OrderShareDialog(Activity activity, String share_Content,String order_Id,String content) {
		this(activity, R.style.App_Share_Dialog);
		this.activity=activity;
		this.share_Content=share_Content.split("【|】")[0]+content+share_Content.split("【|】")[2];
		System.out.println("+++share"+this.share_Content);
		this.order_Id = order_Id;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_order_share);
		Window window = getWindow();
		WindowManager.LayoutParams lp=window.getAttributes();
		lp.alpha = 1.0f;
		lp.gravity = Gravity.BOTTOM;
		lp.width = ScreenUtils.getScreenWidth(activity);
		window.setAttributes(lp);
		setCancelable(true);
		setCanceledOnTouchOutside(true);

		findViewById(R.id.tv_Pengyouquan).setOnClickListener(this);
		findViewById(R.id.tv_Weixin).setOnClickListener(this);
		findViewById(R.id.tv_QQ).setOnClickListener(this);
		findViewById(R.id.tv_QQzone).setOnClickListener(this);
		findViewById(R.id.tv_Xinlang).setOnClickListener(this);
		findViewById(R.id.tv_Dismiss).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		YtPlatform platform = null;
		switch (view.getId()) {
		case R.id.tv_Pengyouquan:
			platform = YtPlatform.PLATFORM_WECHATMOMENTS;
			share(platform);
			break;
		case R.id.tv_Weixin:
			platform = YtPlatform.PLATFORM_WECHAT;
			share(platform);
			break;
		case R.id.tv_QQ:
			platform = YtPlatform.PLATFORM_QQ;
			share(platform);
			break;
		case R.id.tv_QQzone:
			platform = YtPlatform.PLATFORM_QZONE;
			share(platform);
			break;
		case R.id.tv_Xinlang:
			platform = YtPlatform.PLATFORM_SINAWEIBO;
			share(platform);
			break;
		case R.id.tv_Dismiss:
			dismiss();
			break;
		}
	}

	/*
	 * 分享
	 */
	@SuppressLint("SimpleDateFormat")
	void share(YtPlatform platform) {

		YtCore.init(activity);

		shareData.setAppShare(false); // 是否为应用分享，如果为true，分享的数据需在友推后台设置

		shareData.setDescription(share_Content);// 待分享内容的描述

		shareData.setTitle("美今管家"); // 待分享的标题

		shareData.setText(share_Content);// 待分享的文字

		shareData.setImageUrl("http://manage.meijinguanjia.com/upload/logo.png");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		shareData.setPublishTime(sdf.format(System.currentTimeMillis())
				.toString());

		shareData.setTargetId("100");

		shareData
		.setTargetUrl("http://www.meijinguanjia.com/login.aspx?oid="+order_Id+"&mid=");//
		// 待分享内容的跳转链接

		YtCore.getInstance().share(activity, platform, listener, shareData);
		cancel();
	}


	/**
	 * 设置分享监听器
	 */

	YtShareListener listener = new YtShareListener() {

		/** 分享成功后回调方法 */
		@Override
		public void onSuccess(YtPlatform platform, String result) {
			YtToast.showS(activity, "分享成功");

			Log.w("YouTui", platform.getName());
		}

		/** 分享之前调用方法 */
		@Override
		public void onPreShare(YtPlatform platform) {
			//			YtToast.showS(activity, "打开分享");

			Log.w("YouTui", platform.getName());
		}

		/** 分享失败回调方法 */
		@Override
		public void onError(YtPlatform platform, String error) {
			YtToast.showS(activity, "分享失败");

			Log.w("YouTui", platform.getName());
			Log.w("YouTui", error);
		}

		/** 分享取消回调方法 */
		@Override
		public void onCancel(YtPlatform platform) {
			//YtToast.showS(activity, "取消分享");

			Log.w("YouTui", platform.getName());
		}
	};

}
