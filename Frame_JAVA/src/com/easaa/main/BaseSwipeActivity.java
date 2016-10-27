package com.easaa.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.easaa.view.SwipeBackLayout;
import com.easaa.view.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
/**带滑动删除关闭Activity（封装了无网络请求处理页面），不继承于此基类的Activity需要根据需求是否重写无网络请求处理机制*/
public abstract class BaseSwipeActivity extends Activity {

	/**滑动关闭布局*/
	protected SwipeBackLayout swipeBackLayout;

	/**无网络布局*/
	protected View noNetLayout;
	protected TextView tv_Refresh_Net;

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(getContentViewRes() > 0) setContentView(getContentViewRes());

		TApplication.listActivity.add(this);

		initTitle();

		swipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.swipe_base, null);

		swipeBackLayout.attachToActivity(this,needSwipeBack());


		if(TApplication.getInstance().checkNetwork()){
			initViews();
			initEvents();
			initBusiness();
		}else{
			noNetLayout = LayoutInflater.from(this).inflate(R.layout.layout_no_network_, null);
			tv_Refresh_Net = (TextView)noNetLayout.findViewById(R.id.tv_Refresh_Net);
			swipeBackLayout.addView(noNetLayout);
			initNoNetwork();
		}
	}


	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		TApplication.listActivity.remove(this);
	}


	/** 当前的布局**/
	protected abstract int getContentViewRes();

	/** 初始化控件(视图层)**/
	protected abstract void initViews();

	/** 创建对象以及初始化数据(业务和逻辑层) **/
	protected abstract void initBusiness();

	/** 设置监听事件 **/
	protected abstract void initEvents();

	/** 是否需要左上角的按钮。  默认为false不显示**/
	protected abstract boolean needleftButton();

	/** 是否需要右上角的按钮。  默认为false不显示**/
	protected abstract boolean needRightButton();

	/** 左边按钮按下。  默认实现是退出当前activity **/
	protected  void onLeftButtonPress(){
		onBackPressed();
	};

	/**是否需要滑动关闭当前activity，默认为false，表示不需要此功能 **/
	protected abstract boolean needSwipeBack();

	/** 右边按钮按下。 默认实现是退出当前activity。 **/
	protected void onRightButtonPress(){
		onBackPressed();
	};

	/**无网络状态下的初始化视图以及设置刷新监听事件(不需要网络状态的页面需要重写此方法)*/
	protected void initNoNetwork(){

		initViews();

		tv_Refresh_Net.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(TApplication.getInstance().checkNetwork()){
					swipeBackLayout.removeView(noNetLayout);
					initBusiness();
					initEvents();
				}else
					ToastUtil.showToast("请检查网络连接。");
			}
		});
	}

	/**根据ID获取TextView控件*/
	protected TextView getTextView(int id){
		return (TextView)findViewById(id);
	}

	/**根据ID获取Button控件*/
	protected Button getButton(int id){
		return (Button)findViewById(id);
	}

	/**根据ID获取ImageView控件*/
	protected ImageView getImageView(int id){
		return (ImageView)findViewById(id);
	}

	/**根据ID获取ImageButton控件*/
	protected ImageButton getImageButton(int id){
		return (ImageButton)findViewById(id);
	}

	/**根据ID获取CheckBox控件*/
	protected CheckBox getCheckBox(int id){
		return (CheckBox)findViewById(id);
	}

	/**根据ID获取EditText控件*/
	protected EditText getEditText(int id){
		return (EditText)findViewById(id);
	}

	/**根据ID获取RatingBar控件*/
	protected RatingBar getRatingBar(int id){
		return (RatingBar)findViewById(id);
	}

	/**根据ID获取RadioButton控件*/
	protected RadioButton getRadioButton(int id){
		return (RadioButton)findViewById(id);
	}

	/**根据ID获取WebView控件*/
	protected WebView getWebView(int id){
		return (WebView)findViewById(id);
	}

	/**根据ID获取ListView控件*/
	protected ListView getListView(int id){
		return (ListView)findViewById(id);
	}

	/**根据ID获取ExpandableListView控件*/
	protected ExpandableListView getExpandableListView(int id){
		return (ExpandableListView)findViewById(id);
	}

	/**根据ID获取HorizontalScrollView控件*/
	protected HorizontalScrollView getHorizontalScrollView(int id){
		return (HorizontalScrollView)findViewById(id);
	}

	/**根据ID获取ViewPager控件*/
	protected ViewPager getViewPager(int id){
		return (ViewPager)findViewById(id);
	}

	protected  GridView getGridView(int id){
		return (GridView) findViewById(id);
	}

	/**根据ID获取ScrollView控件*/
	protected ScrollView getScrollView(int id){
		return (ScrollView)findViewById(id);
	}

	/**根据ID获取LinearLayout控件*/
	protected LinearLayout getLinearLayout(int id){
		return (LinearLayout)findViewById(id);
	}

	/**根据ID获取LinearLayout控件*/
	protected RelativeLayout getRelativeLayout(int id){
		return (RelativeLayout)findViewById(id);
	}

	/**根据ID获取FrameLayout控件*/
	protected FrameLayout getFrameLayout(int id){
		return (FrameLayout)findViewById(id);
	}

	/**根据ID获取View控件*/
	protected View getView(int id){
		return findViewById(id);
	}

	/**设置标题名字(在每个页面的初始化视图里面执行)*/
	protected void setTitleName(String title_Name){
		getTextView(R.id.tilte_Name).setText(title_Name);
	}

	/** 短暂显示Toast提示(来自res) **/
	protected void showShortToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
	}

	/** 短暂显示Toast提示(来自String) **/
	protected void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/** 长时间显示Toast提示(来自res) **/
	protected void showLongToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	/** 通过startActivityForResult跳转界面 **/
	protected void startActivityForResult(Class<?> cls,int requestCode) {
		startActivityForResult(new Intent(this, cls), requestCode);
	}

	/** 含有Bundle通过startActivityForResult跳转界面 **/
	protected void startActivityForResult(Class<?> cls,int requestCode,Bundle bundle) {
		Intent intent = new Intent(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
	}

	/** 通过Class跳转界面 **/
	protected void startActivity(Class<?> cls) {
		startActivity(new Intent(this, cls));
	}

	/** 含有Bundle通过Class跳转界面 **/
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/** 发送广播 **/
	protected void sendBroadCast(String action) {
		sendBroadcast(new Intent(action));
	}

	/** 含有Bundle的发送广播 **/
	protected void sendBroadCast(String action, Bundle bundle) {
		Intent intent = new Intent(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		sendBroadcast(intent);
	}

	/** 含有标题和内容的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message).show();
		return alertDialog;
	}

	/** 含有标题、内容、两个按钮的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message,
			String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}

	/** 含有标题、内容、图标、两个按钮的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message,
			int icon, String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message).setIcon(icon)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}


	/**初始化标题栏*/
	protected void initTitle() {

		/**默认状态下标题的名字*/
		setTitleName(getResources().getString(R.string.app_name));

		View rightBtn = findViewById(R.id.tilte_Person_Centre);
		if(needRightButton() && rightBtn.getVisibility() != View.VISIBLE) {
			//需要右边按钮，则展示
			rightBtn.setVisibility(View.VISIBLE);
		} else if(!needRightButton() && rightBtn.getVisibility() != View.GONE) {
			//隐藏
			rightBtn.setVisibility(View.GONE);
		}
		View leftBtn = findViewById(R.id.tilte_Back);
		if(needleftButton() && leftBtn.getVisibility() != View.VISIBLE) {
			//需要右边按钮，则展示
			leftBtn.setVisibility(View.VISIBLE);
		} else if(!needleftButton() && leftBtn.getVisibility() != View.GONE) {
			//隐藏
			leftBtn.setVisibility(View.GONE);
		}

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch(v.getId()) {
				case R.id.tilte_Back:
					onLeftButtonPress();
					break;
				case R.id.tilte_Person_Centre:
					onRightButtonPress();
					break;
				}
			}
		};
		leftBtn.setOnClickListener(listener);
		rightBtn.setOnClickListener(listener);
	}


	/**获取图片加载工具*/
	@SuppressWarnings("deprecation")
	protected DisplayImageOptions getImageOption(){
		return new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.info_photo_img)
		.showImageForEmptyUri(R.drawable.info_photo_img)
		.showImageOnFail(R.drawable.info_photo_img).cacheInMemory(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
		.bitmapConfig(Bitmap.Config.ALPHA_8)
		.displayer(new RoundedBitmapDisplayer(0)).build();
	}
}
