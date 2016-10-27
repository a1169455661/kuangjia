package com.easaa.activity;

import com.easaa.main.BaseSwipeActivity;
import com.easaa.main.R;

public class SecondActivity extends BaseSwipeActivity{

	@Override
	protected int getContentViewRes() {
		return R.layout.activity_sencond;
	}

	@Override
	protected void initViews() {
		
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
