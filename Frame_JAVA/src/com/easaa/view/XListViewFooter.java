package com.easaa.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easaa.main.R;

public class XListViewFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;

	private Context mContext;

	private View mContentView;
	private TextView mHintView;
	public  LinearLayout moreView;
	private ImageView mLoadIcon;
	private Animation animation;

	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}


	public void setState(int state) {
		mHintView.setVisibility(View.INVISIBLE);
		mHintView.setVisibility(View.INVISIBLE);

		switch (state) {
		case STATE_READY:
			mLoadIcon.setImageResource(R.drawable.icon_loading_not_selected);
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_ready);
			break;
		case STATE_LOADING:
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(getContext().getString(R.string.xlistview_header_hint_loading));
			mLoadIcon.setImageResource(R.drawable.icon_loading_selected);
			//mLoadIcon.setPadding(10, 10, 10, 10);
			mLoadIcon.startAnimation(animation);
			break;
		case STATE_NORMAL:
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_normal);
			mLoadIcon.clearAnimation();
			mLoadIcon.setImageResource(R.drawable.icon_loading_not_selected);
			break;
		}
	}

	public void setBottomMargin(int height) {
		if (height < 0) return ;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public int getBottomMargin() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		return lp.bottomMargin;
	}


	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
	}


	/**
	 * loading status 
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mHintView.setText(R.string.xlistview_header_hint_loading);
		mLoadIcon.setImageResource(R.drawable.icon_loading_selected);
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * show footer
	 */
	public void show() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	private void initView(Context context) {
		mContext = context;
		moreView = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mLoadIcon = (ImageView) moreView.findViewById(R.id.iv_load);
		mHintView = (TextView)moreView.findViewById(R.id.xlistview_footer_hint_textview);

		animation = AnimationUtils.loadAnimation(mContext, R.anim.loadmore);  
		LinearInterpolator lin = new LinearInterpolator();  
		animation.setInterpolator(lin);
	}


}
