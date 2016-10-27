package com.easaa.main;

import android.os.Debug;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.easaa.activity.MainFragmentActivity;
import com.easaa.tools.ConfigUtil;
import com.easaa.tools.Constant;
/**欢迎界面*/
public class WelcomeActivity extends BaseSwipeActivity {

	// 屏蔽返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return true;
		return false;
	}

	@Override
	public int getContentViewRes() {
		return R.layout.activity_welcome;
	}

	@Override
	protected void initViews() {
		//添加动画效果
		AlphaAnimation animation = new AlphaAnimation(0.5f, 1.0f);
		animation.setDuration(3000);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				//是否是第一次启动app，做标记
				if(ConfigUtil.getBoolean(Constant.KEY_THE_FIRST, true)){
					ConfigUtil.putBoolean(Constant.KEY_THE_FIRST, false);
					startActivity(GuideActivity.class);
				}else{
					startActivity(MainFragmentActivity.class);
				}
				Debug.startNativeTracing();

				finish();

			}
		});

		getRelativeLayout(R.id.re_Welcome).setAnimation(animation);
	}

	@Override
	protected void initBusiness() {

	}


	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean needleftButton() {
		return true;
	}

	@Override
	protected boolean needRightButton() {
		return false;
	}

	@Override
	protected boolean needSwipeBack() {
		return false;
	}

	@Override
	protected void initNoNetwork() {
		noNetLayout.setVisibility(View.GONE);
		initViews();
		initBusiness();
		initEvents();
	}

}
