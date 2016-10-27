package com.easaa.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easaa.main.R;
import com.easaa.main.TApplication;
import com.easaa.view.ToastUtil;

public class MainTwoFragment extends Fragment implements OnClickListener{

	private View view_No_NetLayout;
	private TextView tv_Refresh_Net;

	private View mContentView;

	public MainTwoFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mContentView == null) {

			mContentView = inflater.inflate(R.layout.fragment_main_two_, container, false);

			initView();

			if (!TApplication.getInstance().checkNetwork()) {
				view_No_NetLayout.setVisibility(View.VISIBLE);
			}else{
				view_No_NetLayout.setVisibility(View.GONE);
				getData();
			}

		} else {
			ViewGroup parent = (ViewGroup) mContentView.getParent();

			if (parent != null)
				parent.removeView(mContentView);
		}
		return mContentView;
	}

	private void initView() {
		view_No_NetLayout = (View)mContentView.findViewById(R.id.view_No_NetLayout);
		tv_Refresh_Net = (TextView)view_No_NetLayout.findViewById(R.id.tv_Refresh_Net);
		tv_Refresh_Net.setOnClickListener(this);
	}

	private void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_Refresh_Net:
			if (!TApplication.getInstance().checkNetwork()) {
				view_No_NetLayout.setVisibility(View.VISIBLE);
				ToastUtil.showToast(getResources().getString(R.string.check_Net));
			}else{
				view_No_NetLayout.setVisibility(View.GONE);
				getData();
			}
			break;
		}		
	}
}
