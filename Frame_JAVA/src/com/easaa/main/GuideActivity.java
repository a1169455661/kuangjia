package com.easaa.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.easaa.activity.MainActivity;
import com.easaa.view.DepthPageTransformer;
import com.nostra13.universalimageloader.core.ImageLoader;
/**引导页界面*/
public class GuideActivity extends BaseSwipeActivity implements OnPageChangeListener{

	private LayoutInflater inflater;
	private List<View> views;
	private ViewPagerAdapter vpAdapter;
	private ViewPager viewpager;

	public class ViewPagerAdapter extends PagerAdapter {

		private List<View> views;
		private Activity activity;

		public ViewPagerAdapter(List<View> views,Activity activity) {
			this.views=views;
			this.activity=activity;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public int getCount() {
			if(views!=null){
				return views.size();
			}
			return 0;
		}

		public Object instantiateItem(View container, int position) {
			((ViewPager)container).addView(views.get(position), 0);
			TextView image_Go_To = (TextView)container.findViewById(R.id.tv_Go_To);
			if(position==views.size()-1){
				image_Go_To.setVisibility(View.VISIBLE);
				image_Go_To.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						goHome();
					}
				});
			}else
				image_Go_To.setVisibility(View.GONE);

			return views.get(position);
		}

		protected void goHome() {
			startActivity(MainActivity.class);
			activity.finish();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return  (arg0 == arg1);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getContentViewRes() {
		return R.layout.activity_guide;
	}

	@Override
	protected void initViews() {
		viewpager = getViewPager(R.id.viewpager);		
	}

	@SuppressLint("InflateParams")
	@Override
	protected void initBusiness() {
		inflater=LayoutInflater.from(this);
		views= new ArrayList<View>();
		vpAdapter=new ViewPagerAdapter(views,this);

		//		views.add(inflater.inflate(R.layout.guide_01, null)); 
		//		views.add(inflater.inflate(R.layout.guide_02, null));
		//		views.add(inflater.inflate(R.layout.guide_03, null));
		//		views.add(inflater.inflate(R.layout.guide_04, null));	

		if (TApplication.director_Pic_Array.size()==0) 
			TApplication.director_Pic_Array.addAll(Arrays.asList(getResources().getStringArray(R.array.guide_Array)));

		for (int i = 0; i < TApplication.director_Pic_Array.size(); i++) {
			View view = inflater.inflate(R.layout.layout_guide, null);
			ImageView image_Director = (ImageView)view.findViewById(R.id.image_Guide);
			ImageLoader.getInstance().displayImage(TApplication.director_Pic_Array.get(i),image_Director, getImageOption());
			views.add(view);
		}
	}

	@Override
	protected void initEvents() {
		viewpager.setPageTransformer(true, new DepthPageTransformer());
		viewpager.setAdapter(vpAdapter);
		viewpager.setOnPageChangeListener(this);		
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
		return false;
	}

	@Override
	protected void initNoNetwork() {
		noNetLayout.setVisibility(View.GONE);
		initViews();
		initBusiness();
		initEvents();
	}
}
