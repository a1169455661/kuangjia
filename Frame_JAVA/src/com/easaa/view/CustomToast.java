package com.easaa.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easaa.main.R;

/**
 *  自定义的toast样式
 */
public class CustomToast {

	public static final int ICON_NONE = -1; // 无图�?
	public static final int ICON_DEFAULT = 0; // 默认图标,蓝色
	public static final int ICON_ERROR = 1; // 错误图标
	public static final int ICON_SUCCESS = 2; // 成功图标

	private Context mContext;
	private Resources mResources;
	private LayoutInflater mInflater;

	private Drawable icon = null;
	private CharSequence message = null;
	private int mDuration = Toast.LENGTH_SHORT;


	public CustomToast(Context context) {
		if(context != null){

			mContext = context;
			mResources = context.getResources();
			mInflater = LayoutInflater.from(context);

		}
	}

	/**
	 * 设置toast显示的图�?
	 * 
	 * @param iconDrawable
	 */
	public void setToastIcon(Drawable iconDrawable) {
		icon = iconDrawable;
	}

	/**
	 * 设置toast显示的图�?
	 * 
	 * @param iconDrawable
	 */
	public void setToastIcon(int iconDrawableResId) {
		if(mResources != null){

			setToastIcon(mResources.getDrawable(iconDrawableResId));
		}
	}

	/**
	 * 设置Toast显示的文�?.
	 * 
	 * @param msg
	 */
	public void setToastMsg(CharSequence msg) {
		message = msg;
	}

	/**
	 * 设置Toast显示的文�?.
	 * 
	 * @param msg
	 */
	public void setToastMsg(int msgResId) {
		if(mResources != null){

			setToastMsg(mResources.getString(msgResId));
		}
	}

	/**
	 * 设置显示时间 Toast.LENGTH_LONG和Toast.LENGTH_SHORT
	 * 
	 * @param dura
	 */
	public void setDuration(int dura) {
		if(dura == Toast.LENGTH_SHORT || dura == Toast.LENGTH_LONG){

			mDuration = dura;
		}else{
			mDuration = Toast.LENGTH_SHORT;
		}

	}

	// TODO Remove unused code found by UCDetector
	// 	public void cancel(){
	// 		
	// 	}

	public static int getIconRes(int iconType) {
		switch (iconType) {
		case ICON_ERROR:
			return R.drawable.common_alert_icon;
		case ICON_SUCCESS:
			return R.drawable.setting_icons_correct;
		default:
			return R.drawable.common_alert_icon;
		}
	}

	/**
	 * 创建Toast对象
	 */
	public Toast create(int yOffset) {
		if(mContext==null || mInflater == null){
			return null;
		}
		Toast toast = new Toast(mContext);

		View view = mInflater.inflate(R.layout.toast_base, null);
		if (icon != null) {
			ImageView v = (ImageView) view.findViewById(R.id.toast_icon);
			v.setImageDrawable(icon);
		} 

		if (message != null) {
			TextView v = (TextView) view.findViewById(R.id.toast_msg);
			v.setText(message);
		}
		toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, yOffset);
		toast.setView(view);
		toast.setDuration(mDuration);

		return toast;
	}

	// TODO Remove unused code found by UCDetector
	// 	/**
	// 	 * 显示Toast 默认显示在系统tilte顶部，可调用此方�?
	// 	 */
	// 	public void show() {
	// 		create(0).show();
	// 	}

	// TODO Remove unused code found by UCDetector
	// 	/**
	// 	 * 显示Toast
	// 	 * 
	// 	 * @param yOffset
	// 	 *            y轴偏移量
	// 	 */
	// 	public void show(int yOffset) {
	// 		create(yOffset).show();
	// 	}

	/**
	 * 配置�?个Toast
	 * 
	 * @param context
	 * @param iconType
	 *            文字左边的图标类�?,可能的�?�为ICON_DEFAULT,ICON_ERROR,ICON_SUCCESS
	 * @param msg
	 * @param duration
	 * @return
	 */
	public static CustomToast makeText(Context context, int iconType,
			CharSequence msg, int duration) {
		CustomToast qzToast = new CustomToast(context);
		qzToast.setToastIcon(getIconRes(iconType));
		qzToast.setToastMsg(msg);
		qzToast.setDuration(duration);
		return qzToast;
	}

	/**
	 * 配置�?个Toast(默认图标)
	 * 
	 * @param context
	 * @param msg
	 * @param duration
	 * @return
	 */
	public static CustomToast makeText(Context context, CharSequence msg,
			int duration) {
		return makeText(context, ICON_DEFAULT, msg, duration);
	}

	public static CustomToast makeText(Context context, CharSequence msg,
			int duration,int iconType) {
		return makeText(context, iconType, msg, duration);
	}

}
