package com.easaa.response.bean;


public class GetWeiXinInfoPageEntity extends BasePageEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GetWeiXinInfoBean data;

	/**获取微信支付请求参数返回Bean*/
	public class GetWeiXinInfoBean{
		public String appid;//公众账号ID
		public String body;
		public String key;
		public String mch_id;
		public String nonce_str;
		public String notify_url;
		public String out_trade_no;
		public String spbill_create_ip;
		public String total_fee;
		public String trade_type;
	}
}
