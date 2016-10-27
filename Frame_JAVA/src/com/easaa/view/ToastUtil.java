package com.easaa.view;

import java.lang.ref.WeakReference;

import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.Toast;

import com.easaa.business.thread.HandlerThreadFactory;
import com.easaa.main.R;
import com.easaa.main.TApplication;

/**
 * toast只能在UI现成中展示，这里进行封装，使之也能在非UI线程调用
 */
public class ToastUtil {

	private static WeakReference<Toast> tostRef;

	/**
	 * true：取消上�?个toast，立即显示当前toast<br>
	 * false：等上一个toast显示完了，再显示当前toast
	 */
	private static boolean clearLastToastFlag = true;


	private static int mDefaultToastOffset = TApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.title_bar_height);

	/**
	 * 显示默认样式的toast
	 * @see android.widget.Toast#makeText(android.content.Context, CharSequence,
	 *      int)
	 * @param text
	 * @param duration
	 * @param yOffset 相对于界面顶部的偏移
	 */
	public static void showToast(CharSequence text, int duration,int yOffset,int type) {
		showToastImpl(text, duration,yOffset,type);
	}
	public static void showToast(int resId,int yOffset,int type) {
		showToast(resId, Toast.LENGTH_SHORT,yOffset,type);
	}

	public static void showToast(int resId, int duration,int yOffset,int type)
			throws Resources.NotFoundException {
		showToastImpl(TApplication.getInstance().getText(resId), duration,yOffset,type);
	}

	//显示在titleBar 下方
	public static void showToast(CharSequence text,int type){
		if(TextUtils.isEmpty(text))
		{
			return;
		}
		showToast(text,Toast.LENGTH_SHORT,mDefaultToastOffset,type);
	}

	public static void showToast(CharSequence text){
		if(TextUtils.isEmpty(text))
		{
			return;
		}
		showToast(text,Toast.LENGTH_SHORT,mDefaultToastOffset,CustomToast.ICON_DEFAULT);
	}

	public static void showToast(int  textId){
		showToast(textId,mDefaultToastOffset,CustomToast.ICON_DEFAULT);
	}

	public static void showToast(int  textId,int type){
		showToast(textId,mDefaultToastOffset,type);
	}

	// TODO Remove unused code found by UCDetector
	// 	/**
	// 	 * 显示指定样式的toast
	// 	 * @see android.widget.Toast#makeText(android.content.Context, CharSequence,
	// 	 *      int)
	// 	 * @param iconType 文字左边的图标类�?,可能的�?�为ICON_DEFAULT,ICON_ERROR,ICON_SUCCESS
	// 	 * @param text
	// 	 * @param duration
	// 	 */
	// 
	// 	public static void showToast(int iconType,CharSequence text, int duration,int yOffset) {
	// 		showToastImpl(iconType,text, duration,yOffset);
	// 	}


	// TODO Remove unused code found by UCDetector
	// 	public static void showToast(int iconType,int resId, int duration,int yOffset)
	// 			throws Resources.NotFoundException {
	// 
	// 		showToastImpl(iconType,BaseApplication.getContext().getText(resId), duration,yOffset);
	// 	}


	// TODO Remove unused code found by UCDetector
	// 	/**
	// 	 * @see #clearLastToastFlag
	// 	 * @return
	// 	 */
	// 	public static boolean isClearLastToastFlag() {
	// 		return clearLastToastFlag;
	// 	}

	// TODO Remove unused code found by UCDetector
	// 	/**
	// 	 * @see #clearLastToastFlag
	// 	 * @param clearLastToastFlag
	// 	 */
	// 	public static void setClearLastToastFlag(boolean clearLastToastFlag) {
	// 		ToastUtil.clearLastToastFlag = clearLastToastFlag;
	// 	}


	/**
	 * 显示默认样式的toast
	 * @see android.widget.Toast#makeText(android.content.Context, CharSequence,
	 *      int)
	 * @param text
	 * @param duration
	 * @param yOffset 相对于界面顶部的偏移
	 */
	private static void showToastImpl(final CharSequence text,
			final int duration,final int yOffset,final int type) {
		HandlerThreadFactory.getHandlerThread(HandlerThreadFactory.BackgroundThread).getHandler().post(new Runnable() {

			@Override
			public void run() {
				Toast t = CustomToast.makeText(TApplication.getInstance(), text, duration,type).create(yOffset);
				if (t == null) {
					return;
				}
				if (clearLastToastFlag) {
					if (tostRef != null) {
						Toast lastToast = tostRef.get();
						if (lastToast != null) {
							lastToast.cancel();
						}
					}

					tostRef = new WeakReference<Toast>(t);
				}
				try {
					t.show();					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



}
