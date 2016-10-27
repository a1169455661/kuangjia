package com.easaa.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.easaa.main.R;
import com.easaa.tools.ScreenUtils;

/**网络请求对话框*/
public class NetLoadingDialog extends Dialog {

	Context context;
	String description;

	public NetLoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	public NetLoadingDialog(Context context,String description) {
		this(context, R.style.net_Loading_Dialog_Style);
		this.context=context;
		this.description=description;
	}

	public NetLoadingDialog(Context context,int description,int code) {
		this(context, R.style.net_Loading_Dialog_Style);
		this.context=context;
		this.description=context.getResources().getString(description);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_net_load);

		TextView toast_msg=(TextView)findViewById(R.id.toast_msg);
		toast_msg.setText(description);

		Window window = getWindow();
		WindowManager.LayoutParams lp=window.getAttributes();
		lp.alpha = 1.0f;
		lp.gravity = Gravity.CENTER;
		lp.width = ScreenUtils.getScreenWidth(context);
		lp.height=ScreenUtils.getScreenHeight(context);
		window.setAttributes(lp);
		setCancelable(false);
		setCanceledOnTouchOutside(false);
	}

}
