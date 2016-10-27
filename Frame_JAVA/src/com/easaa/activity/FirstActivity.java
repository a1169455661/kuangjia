package com.easaa.activity;

import android.view.View;
import android.view.View.OnClickListener;

import com.easaa.main.BaseSwipeActivity;
import com.easaa.main.R;

/**主Activity*/
public class FirstActivity extends BaseSwipeActivity{

	@Override
	protected int getContentViewRes() {
		return R.layout.activity_first;
	}

	@Override
	protected void initViews() {
		getTextView(R.id.tv_Submit_Feedback).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(SecondActivity.class);
			}
		});
	}

	@Override
	protected void initBusiness() {
		// TODO Auto-generated method stub

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
		return true;
	}

}
