package com.easaa.widget;

import com.easaa.main.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
/**Popupwindiow*/
public class BasePopupUtils extends PopupWindow{

	private static BasePopupUtils mInstance;

	public static BasePopupUtils getInstance(Context context, int layout) {
		if (mInstance == null) {
			mInstance = new BasePopupUtils(context,layout);
		}
		return mInstance;
	}

	private View mPopView;

	public BasePopupUtils(Context context, int layout) {  
		super(context);  
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPopView= inflater.inflate(layout, null);
		this.setContentView(mPopView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 点击外面的控件也可以使得PopUpWindow dimiss
		this.setOutsideTouchable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.PopupAnimation2);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);//0xb0000000
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);//半透明颜色
	} 
}
