package com.easaa.view;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SwipeBackLayout extends FrameLayout {
	/** Constants */
	private static final String TAG = "SwipeBackLayout";

	/** Variables */
	private int mTouchSlop;
	private int downX, downY, tempX;
	private int viewWidth;
	private boolean isSilding;
	private boolean isFinish;

	/** Views */
	private View mContentView;
	private Scroller mScroller;
	private Activity mActivity;
	private boolean isCanSwipe;
	private List<ViewPager> mViewPagers = new LinkedList<ViewPager>();

	public SwipeBackLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mScroller = new Scroller(context);
	}

	/**
	 * 是否可以滑动关闭当前Activity
	 * @param activity
	 * @param isCanSwipe
	 */
	public void attachToActivity(Activity activity,boolean isCanSwipe) {
		mActivity = activity;
		this.isCanSwipe = isCanSwipe;
		TypedArray a = activity.getTheme().obtainStyledAttributes(new int[] { android.R.attr.windowBackground });
		int background = a.getResourceId(0, 0);
		a.recycle();

		ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
		ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
		decorChild.setBackgroundResource(background);
		decor.removeView(decorChild);
		addView(decorChild);
		setContentView(decorChild);
		decor.addView(this);
	}

	private void setContentView(View decorChild) {
		mContentView = (View) decorChild.getParent();
	}

	/**
	 * 事件拦截操作
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 处理ViewPager冲突问题
		ViewPager mViewPager = getTouchViewPager(mViewPagers, ev);
		Log.i(TAG, "mViewPager = " + mViewPager);

		if (mViewPager != null && mViewPager.getCurrentItem() != 0) {
			return super.onInterceptTouchEvent(ev);
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = tempX = (int) ev.getRawX();
			downY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) ev.getRawX();
			if (moveX - downX > mTouchSlop && Math.abs((int) ev.getRawY() - downY) < mTouchSlop) {
				return true;
			}
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isCanSwipe) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				int moveX = (int) event.getRawX();
				int deltaX = tempX - moveX;
				tempX = moveX;
				if (moveX - downX > mTouchSlop && Math.abs((int) event.getRawY() - downY) < mTouchSlop) {
					isSilding = true;
				}

				if (moveX - downX >= 0 && isSilding) {
					mContentView.scrollBy(deltaX, 0);
				}
				break;
			case MotionEvent.ACTION_UP:
				isSilding = false;
				if (mContentView.getScrollX() <= -viewWidth / 2) {
					isFinish = true;
					scrollRight();
				} else {
					scrollOrigin();
					isFinish = false;
				}
				break;
			}

			return true;
		}else
			return false;
	}

	/**
	 * 获取SwipeBackLayout里面的ViewPager的集合
	 * 
	 * @param mViewPagers
	 * @param parent
	 */
	private void getAlLViewPager(List<ViewPager> mViewPagers, ViewGroup parent) {
		int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = parent.getChildAt(i);
			if (child instanceof ViewPager) {
				mViewPagers.add((ViewPager) child);
			} else if (child instanceof ViewGroup) {
				getAlLViewPager(mViewPagers, (ViewGroup) child);
			}
		}
	}

	/**
	 * 返回我们touch的ViewPager
	 * 
	 * @param mViewPagers
	 * @param ev
	 * @return
	 */
	private ViewPager getTouchViewPager(List<ViewPager> mViewPagers, MotionEvent ev) {
		if (mViewPagers == null || mViewPagers.size() == 0) {
			return null;
		}
		Rect mRect = new Rect();
		for (ViewPager v : mViewPagers) {
			v.getHitRect(mRect);

			if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
				return v;
			}
		}
		return null;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			viewWidth = this.getWidth();

			getAlLViewPager(mViewPagers, this);
			Log.i(TAG, "ViewPager size = " + mViewPagers.size());
		}
	}

	/**
	 * 滚动出界面
	 */
	private void scrollRight() {
		final int delta = (viewWidth + mContentView.getScrollX());
		// 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
		mScroller.startScroll(mContentView.getScrollX(), 0, -delta + 1, 0, Math.abs(delta));
		postInvalidate();
	}

	/**
	 * 滚动到起始位置
	 */
	private void scrollOrigin() {
		int delta = mContentView.getScrollX();
		mScroller.startScroll(mContentView.getScrollX(), 0, -delta, 0, Math.abs(delta));
		postInvalidate();
	}

	@Override
	public void computeScroll() {
		// 调用startScroll的时候scroller.computeScrollOffset()返回true，
		if (mScroller.computeScrollOffset()) {
			mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();

			if (mScroller.isFinished() && isFinish) {
				mActivity.finish();
			}
		}
	}

}
