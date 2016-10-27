package com.easaa.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.view.View;
import android.view.View.OnClickListener;

import com.easaa.main.BaseSwipeActivity;
import com.easaa.main.R;
import com.easaa.main.TApplication;
import com.easaa.net.HttpUtils;
import com.easaa.net.HttpUtils.CallBack;
import com.easaa.net.NetWorkUtils;
import com.easaa.response.bean.ClassificationlistPageEntity;
import com.easaa.view.ToastUtil;

/**主Activity*/
public class MainActivity extends BaseSwipeActivity implements OnClickListener{

	/*** 按两次退出程序*/
	private static Boolean isQuit = false;
	private Timer timer = new Timer();

	@Override
	protected int getContentViewRes() {
		return R.layout.activity_main;
	}

	@Override
	protected void initViews() {
		findViewById(R.id.tv_OnClick_).setOnClickListener(this);
	}

	@Override
	protected void initBusiness() {
		HttpUtils.getInstance().getData(this,NetWorkUtils.classificationlist(), ClassificationlistPageEntity.class, null, new CallBack<ClassificationlistPageEntity>() {

			@Override
			public void handleMessage(ClassificationlistPageEntity object) {
				// TODO Auto-generated method stub
				if (object!=null && object.result) {
					ToastUtil.showToast("=======>"+object.data.size());
				}else{
					ToastUtil.showToast("=======>数据异常");
				}
			}
		});
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean needleftButton() {
		return false;
	}

	@Override
	protected boolean needRightButton() {
		return false;
	}

	@Override
	protected void onRightButtonPress() {

	}

	@Override
	protected boolean needSwipeBack() {
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_OnClick_:
			startActivity(FirstActivity.class);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		if (isQuit == false) {
			isQuit = true;
			ToastUtil.showToast(R.string.exit_App);
			TimerTask task = null;
			task = new TimerTask() {
				@Override
				public void run() {
					isQuit = false;
				}
			};
			timer.schedule(task, 2000);
		} else {
			TApplication.exit();
		}
	}
}
