package com.easaa.response.bean;


public class GetAlipayInfoPageEntity extends BasePageEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GetAlipayInfo data;

	/**获取支付宝商户信息返回Bean*/
	public class GetAlipayInfo{
		public String body;
		public String input_charset;
		public String it_b_pay;
		public String notify_url;
		public String out_trade_no;
		public String partner;
		public String payment_type;
		public String privatekey;
		public String seller_id;
		public String service;
		public String sign;
		public String sign_type;
		public String subject;
		public String total_fee;
	}
}
