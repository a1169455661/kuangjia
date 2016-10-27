package com.easaa.tools;

public class Constant {

	/**
	 * 上传头像常量
	 */
	public static final int CAMERA_RESULT=1;
	//从手机相册选择
	public static final int OPEN_RESULT=2;		
	public static final int PHOTORESOULT=3;
	/**拍照图片裁剪固定字段*/
	public static final String IMAGE_UNSPECIFIED = "image/*";

	/**上传图像的接口名*/
	public static String uploadpic = "uploadpic";


	/**调用接口成功的状态*/
	public static final int HTTP_SUCCESS = 0;
	/**调用接口非法访问的状态(需要重新登入)*/
	public static final int HTTP_FEIFA = 10004;


	/**startActivityForResult的请求标识*/
	public static final int REQUEST_CODE = 0;
	/**startActivityForResult的返回标识*/
	public static final int RESPONSE_CODE = 1;
	/**startActivityForResult的返回另外标识*/
	public static final int RESPONSE_CODE_ONTHER = 2;
	/**startActivityForResult的返回另外标识*/
	public static final int RESPONSE_CODE_ONTHER_ONTHER = 3;


	/**购物商城里面购买方式跳转到确认订单页面，1.直接购买，2.购物车购买*/
	public  static final String BUY_STYLE_MARK = "extra_Buy_Style";
	public static final int BUY_STYLE_GWSC_DIRECT_ = 1;
	public static final int BUY_STYLE_GWSC_FROM_SHOPPINGCAR_ = 2;


	/**从订单页面跳转到地址列表页面的标识,点击表示选择地址并且关闭地址页面*/
	public static final int MARK_ORDER_TO_ADDRESS_LIST_ = 0;
	/**从个人中心里或其他页面跳转到地址列表页面的标识,点击表示设置默认*/
	public static final int MARK_OHTHER_TO_ADDRESS_LIST_ = 1;
	/**个人中心我的心心里面地址列表,点击不做任何操作*/
	public static final int MARK_OHTHER_TO_ADDRESS_LIST_NO = 2;

	/**用来传递订单号的常量*/
	public static final String MARK_ORDER_NUMBER_ = "orderNumber";
	/**用来传递订单号总价的常量*/
	public static final String MARK_ORDER_PRICE_ = "orderPrice";
	/**用来传递是否为服务的常量,false表示为商品，true表示为服务*/
	public static final String MARK_IS_SERVUCE_ = "isService";
	/**用来传递支付限制方式的常量,(支付方式限制：0 可以在线和服务后支付，1 只能在线支付，2只能 服务后支付)(只针对服务有限制)*/
	public static final String MARK_PAY_LIMIT_ = "payLimit";

	/**记录APP是否为第一次启动*/
	public static final String KEY_THE_FIRST = "firstOpen";

	/**登陆返回的bean*/
	public final static String KEY_LOGIN_BEAN="login_bean";

	/**App当前选择的定位城市*/
	public final static String KEY_SELECTED_CITY_BEAN="location_city_bean";

	/**App更新的版本号*/
	public final static String KEY_UPDATE_APP_VESION_="app_Vesion";

	/**下订单时候的默认选择地址*/
	public final static String KEY_DEFAULT_ADDRESS_BEAN="default_address_bean";

	/**服务名和服务id*/
	public final static String SERVICE_BEAN="service_Bean";

	/**订单列表数据的状态值(0-全部)*/
	public final static String ORDER_ALL="0";
	/**订单列表数据的状态值(1-未支付/待付款)*/
	public final static String ORDER_PAY_NO="1";
	/**订单列表数据的状态值(2-已支付/待发货)*/
	public final static String ORDER_PAY_YES="2";
	/**订单列表数据的状态值(3-已完成/待评价)*/
	public final static String ORDER_FINISH="3";
	/**订单列表数据的状态值(4-已取消)*/
	public final static String ORDER_QUXIAO="4";


	/**登入成功后的广播接收器*/
	public final static String ACTION_LOGIN_SUCCESS_BROADCAST = "com.easaa.main.LoginActivity";

}
