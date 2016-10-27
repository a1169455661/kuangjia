package com.easaa.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easaa.QuNisDmasd;
import com.easaa.main.R;
import com.easaa.main.TApplication;
import com.easaa.net.HttpUtils;
import com.easaa.response.bean.BasePageEntity;
import com.easaa.view.ToastUtil;

public class MainFragmentActivity extends FragmentActivity implements OnClickListener {
    protected static final String TAG = "MeetingFragmentActivity";

    private View view_No_NetLayout;
    private TextView tv_Refresh_Net, tilte_Back, tilte_Name, tilte_Person_Centre;
    public ImageButton btn_Meeting_One, btn_Meeting_Two, btn_Meeting_Three, btn_Meeting_Four;

    public RelativeLayout re_Meeting_One, re_Meeting_Two, re_Meeting_Three, re_Meeting_Four;

    /**
     * 当前点击的按钮
     */
    private View currentButton;
    /**
     * 当前点击的按钮所在布局
     */
    private RelativeLayout currentLayout;

    private MainOneFragment fragmentOne;
    private MainTwoFragment fragmentTwo;
    private MainThreeFragment fragmentThree;
    private MainFourFragment fragmentFour;

    /***
     * 按两次退出程序
     */
    private static Boolean isQuit = false;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_activity_);
        TApplication.listActivity.add(this);

        initView();
        if (!TApplication.getInstance().checkNetwork()) {
            view_No_NetLayout.setVisibility(View.VISIBLE);
        } else {
            getData();
            view_No_NetLayout.setVisibility(View.GONE);
        }


        wangluoqingqiu();


    }

    /**
     * 网络请求
     */
    private void wangluoqingqiu() {
        String url = "http://192.168.0.97:81/cart/cartList";
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", 1);
        HttpUtils.getInstance().getData(this, url, QuNisDmasd.class, params, new HttpUtils.CallBack<QuNisDmasd>() {
            @Override
            public void handleMessage(QuNisDmasd object) {
                System.out.println("------o"+object.getData().size());
                System.out.println("------o" + object.getData().get(0).getCart_id());

                System.out.println("------object" + object);

            }
        });

    }

    private void initView() {
        //=================================================================网络
        view_No_NetLayout = (View) findViewById(R.id.view_No_NetLayout);
        tv_Refresh_Net = (TextView) view_No_NetLayout.findViewById(R.id.tv_Refresh_Net);
        tv_Refresh_Net.setOnClickListener(this);

        //=================================================================标题
        tilte_Back = (TextView) findViewById(R.id.tilte_Back);
        tilte_Back.setVisibility(View.GONE);
        //		Drawable drawable= getResources().getDrawable(R.drawable.mark_app_location);
        //		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        //		tilte_Back.setCompoundDrawables(drawable, null, null, null);
        tilte_Back.setOnClickListener(this);
        tilte_Name = (TextView) findViewById(R.id.tilte_Name);
        tilte_Person_Centre = (TextView) findViewById(R.id.tilte_Person_Centre);
        Drawable drawable = getResources().getDrawable(R.drawable.add);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tilte_Person_Centre.setCompoundDrawables(drawable, null, null, null);
        tilte_Person_Centre.setText("");
        tilte_Back.setVisibility(View.GONE);
        tilte_Person_Centre.setVisibility(View.VISIBLE);

        //============================================================底部切换按钮
        btn_Meeting_One = (ImageButton) findViewById(R.id.btn_Meeting_One);
        btn_Meeting_Two = (ImageButton) findViewById(R.id.btn_Meeting_Two);
        btn_Meeting_Three = (ImageButton) findViewById(R.id.btn_Meeting_Three);
        btn_Meeting_Four = (ImageButton) findViewById(R.id.btn_Meeting_Four);
        tilte_Person_Centre.setOnClickListener(this);
        btn_Meeting_One.setOnClickListener(this);
        btn_Meeting_Two.setOnClickListener(this);
        btn_Meeting_Three.setOnClickListener(this);
        btn_Meeting_Four.setOnClickListener(this);

        re_Meeting_One = (RelativeLayout) findViewById(R.id.re_Meeting_One);
        re_Meeting_Two = (RelativeLayout) findViewById(R.id.re_Meeting_Two);
        re_Meeting_Three = (RelativeLayout) findViewById(R.id.re_Meeting_Three);
        re_Meeting_Four = (RelativeLayout) findViewById(R.id.re_Meeting_Four);

        //===========================================================四个子页Framgemt
        fragmentOne = new MainOneFragment();
        fragmentTwo = new MainTwoFragment();
        fragmentThree = new MainThreeFragment();
        fragmentFour = new MainFourFragment();

    }

    private void getData() {
        tilte_Name.setText("主页面");
        /**默认显示首页*/
        btn_Meeting_One.performClick();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.listActivity.remove(this);
    }


    @Override
    public void onClick(View view) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch (view.getId()) {
            case R.id.tilte_Back:
                finish();
                break;
            case R.id.tilte_Person_Centre:
                startActivity(new Intent(this, FirstActivity.class));
                break;
            case R.id.tv_Refresh_Net:
                if (!TApplication.getInstance().checkNetwork()) {
                    view_No_NetLayout.setVisibility(View.VISIBLE);
                    ToastUtil.showToast(getResources().getString(R.string.check_Net));
                } else {
                    view_No_NetLayout.setVisibility(View.GONE);
                    getData();
                }
                break;
            case R.id.btn_Meeting_One:
                ft.replace(R.id.fl_content, fragmentOne, MainFragmentActivity.TAG);
                ft.commit();
                setButton(view, re_Meeting_One);
                break;
            case R.id.btn_Meeting_Two:
                ft.replace(R.id.fl_content, fragmentTwo, MainFragmentActivity.TAG);
                ft.commit();
                setButton(view, re_Meeting_Two);

                break;

            case R.id.btn_Meeting_Three:
                ft.replace(R.id.fl_content, fragmentThree, MainFragmentActivity.TAG);
                ft.commit();
                setButton(view, re_Meeting_Three);
                break;

            case R.id.btn_Meeting_Four:
                ft.replace(R.id.fl_content, fragmentFour, MainFragmentActivity.TAG);
                ft.commit();
                setButton(view, re_Meeting_Four);
                break;
        }
    }

    /**
     * 设置切换按钮所在的状态及显示
     */
    private void setButton(View view, RelativeLayout layout) {
        if (currentButton != null && currentButton.getId() != view.getId()) {
            currentButton.setEnabled(true);
            currentLayout.setBackgroundColor(getResources().getColor(R.color.gray));
        }
        view.setEnabled(false);
        currentButton = view;
        layout.setBackgroundColor(getResources().getColor(R.color.title_bacground));
        currentLayout = layout;
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
        //		super.onBackPressed();
        //		overridePendingTransition(0, R.anim.base_slide_right_out);
        if (isQuit == false) {
            isQuit = true;
            ToastUtil.showToast(R.string.exit_App);
            TimerTask task = null;
            task = new TimerTask() {
                @Override
                public void run() {
                    isQuit = false;
                }
            };
            timer.schedule(task, 2000);
        } else {
            TApplication.exit();
        }
    }

    //	@Override
    //	public void finish() {
    //		super.finish();
    //		overridePendingTransition(0, R.anim.base_slide_right_out);
    //	}
}
