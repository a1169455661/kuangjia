package com.easaa.adv.viewpage.reversal;
	
	import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.easaa.main.R;
	
	/**
	 * This is a slider with a description TextView.
	 */
	public class TextSliderView_ischang extends BaseSliderView{
	
		private WindowManager windowManager;
		@SuppressWarnings("unused")
		private int playWidth;
	
		public TextSliderView_ischang(Context context) {
			super(context);
		}
	
		@SuppressLint("InflateParams")
		@SuppressWarnings("deprecation")
		@Override
		public View getView() {
	
			windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
			playWidth=windowManager.getDefaultDisplay().getWidth();
	
			View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text_ischange,null);
			RelativeLayout re_All= (RelativeLayout)v.findViewById(R.id.re_All_ischange);
			ImageView target = (ImageView)re_All.findViewById(R.id.daimajia_slider_image_ischang);
			//		LayoutParams layoutParams1=re_All.getLayoutParams();
			//		layoutParams1.width=(int) (playWidth*1/2);
			//		layoutParams1.height=(int) (playWidth*1/2);
			//		re_All.setLayoutParams(layoutParams1);
	
			TextView description = (TextView)v.findViewById(R.id.description_ischang);
			description.setText(getDescription());
			loadImage(target);
			bindClickEvent(v);
			return v;
		}
	}
