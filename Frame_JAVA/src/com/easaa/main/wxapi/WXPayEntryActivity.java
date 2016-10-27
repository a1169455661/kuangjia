package com.easaa.main.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.easaa.main.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, WeiXinPayMethod.APP_ID);

		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		/**
		 * 0-成功-展示成功页面
		 * -1-错误-可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
		 * -2-用户取消-无需处理。发生场景：用户不支付了，点击取消，返回APP
		 * */
		switch (resp.errCode) {
		case 0:
			Toast.makeText(this, "微信支付成功", Toast.LENGTH_SHORT).show();
			break;
		case -1:
			Toast.makeText(this, "微信支付错误", Toast.LENGTH_SHORT).show();
			break;
		case -2:
			Toast.makeText(this, "取消微信支付成功", Toast.LENGTH_SHORT).show();
			break;
		}

		finish();
		//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
		//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//			builder.setTitle("提示");
		//			builder.setMessage(getString(R.string.pay_result_callback_msg, resp.errStr +";code=" + String.valueOf(resp.errCode)));
		//			builder.show();
		//		}
	}
}