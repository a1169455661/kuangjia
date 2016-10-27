package com.easaa.net;

/**请求接口URL工具类*/
public class NetWorkUtils {

	public static final String URL = "http://120.76.145.194:8080/JC_Jewellery_inter/jc/";

	// 分类列表
	public static final String CLASSIFICATIONLIST = "product/allType.do";

	// 分类列表方法
	public static String classificationlist() {
		System.out.println("-----分类列表" + URL + CLASSIFICATIONLIST);
		return URL + CLASSIFICATIONLIST;
	}

}
