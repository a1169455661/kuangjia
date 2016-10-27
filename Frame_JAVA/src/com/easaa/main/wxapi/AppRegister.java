package com.easaa.main.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		WeiXinPayMethod.regist_WeiXinPay(context);
	}
}
