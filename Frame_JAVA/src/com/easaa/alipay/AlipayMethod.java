package com.easaa.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.easaa.response.bean.GetAlipayInfoPageEntity.GetAlipayInfo;

/**第三方支付工具类*/
public class AlipayMethod {

	public static final int SDK_PAY_FLAG = 1;
	public static final int SDK_CHECK_FLAG = 2;

	/**
	 * 支付宝信息
	 */
	//	public String PARTNER = "2088701704828566";
	//	public String SELLER = "2088701704828566";
	//	public String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOnDmyEUXKTrp3kPKuksWc/Fk5NlVDFkqEUdqdV2gAI1+b+1aENg+hzUdwclliICZcdfX9ryE8W7VJWI0d/C6mF7Z4yrm4nexgr8lJ90eYju1C8ah3iMe/Qu1CPfJxEkaWfUDpm2yCRUbpK0z4u2q48vxtiVwgd0dwvQTgy8IxeJAgMBAAECgYEA4g9AaIf5Y59kcETDuYQjQjMOGL8n4TA3LceW4pBbEEhEQilbL7WLie/JP4cQncj1xg0sdF4f0trq3ETYuOwUnOGGdCBJsn4TuDsxdnd42Re31WokBrJ1GEBy2LXkzOlwrKpuSrapbMqHy4DqhWk0lvf+Uy9+WfwXfjgWYBovmIECQQD1OyqTbOlPFxgyJC1NOdoHF2uTCEvNzHLAdNLUjchIM3F4MW6CHQQnuQkVZUsNxLlcTaAikn0yw9gctdTMcxnZAkEA9AeHow8IUwD9YuJppIoEPNR5XADuNtgU7Lrd+39q5xH1noVemSNORYQqMvBijP+BuJUjup1oW1whDb7sKRAtMQJAccDAPyKGFunfj2K4oNn1CNUUUV/Hye7MS/nk0jPyc4tpVxeW60QKaWKrlAEoj3pxgR+A6IiLCsFNvgOacjvyAQJBAMZvSRCDLNWyPFmomxc9U+zTKkxyRUM7uug1nETie9+ibwSUlzQXzZIhwt7hMoJNUwLx47fgP/fqlsZCtm89xEECQAxxjL2DkVYwUkuAKCS/r3MKXdy5GQ4XNb3cIqUeeooedCS5AwZbVbWmjkygJRr4VEKoo3CGGJ4Mbao6nElI+mk=";
	/***支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取*/
	//public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC8KVIfEvjXrxQ+p6LvSxT1trmuEa/xegvk6Wb e9nSEl04OgfmMlmyU5aPK3N2xq4J8AAtErlcHtE7cx3h6XiG9dacqG68gZjuA9DLep3LqcyVi+v1 YLxj6P1qgn7qxFxu+lliGU7lTksUKWjolhacAZFDvTHRa8KALoOEhG2dWwIDAQAB";
	/**支付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致
	public static final String ALIPAY_PLUGIN_NAME = "alipay_plugin_20120428msp.apk";


	 * 支付宝 call alipay sdk pay. 调用SDK支付
	 * @param alipayInfoBean
	 * @param mHandler
	 * @param context
	 */
	public static  void payMethiod(GetAlipayInfo alipayInfoBean,
			final Handler mHandler, final Activity context) {
		String orderInfo = getOrderInfo(alipayInfoBean);
		String sign = sign(orderInfo,alipayInfoBean);
		//String sign = alipayInfoBean.sign;
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");

			Log.d("CH", sign);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(context);
				// 调用支付接口
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 封装支付宝订单信息
	 * @param subject
	 * @param body
	 * @param price
	 * @return
	 */
	public static String getOrderInfo(GetAlipayInfo alipayInfoBean) {
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + alipayInfoBean.partner + "\"";

		// 卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + alipayInfoBean.seller_id + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + alipayInfoBean.out_trade_no + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + alipayInfoBean.subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + alipayInfoBean.body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + alipayInfoBean.total_fee + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + alipayInfoBean.notify_url
				+ "\"";

		// 接口名称， 固定值
		orderInfo += "&service=\""+alipayInfoBean.service+"\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\""+alipayInfoBean.payment_type+"\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\""+alipayInfoBean.input_charset+"\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\""+alipayInfoBean.it_b_pay+"\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public static String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public static String sign(String content,GetAlipayInfo alipayInfoBean) {
		return SignUtils.sign(content, alipayInfoBean.privatekey.replace("@", "+"));
	}

	/**
	 * get the out_trade_no for an order. 获取外部订单号
	 * 
	 */
	public static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
}
