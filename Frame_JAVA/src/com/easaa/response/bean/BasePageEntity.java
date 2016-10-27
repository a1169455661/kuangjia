package com.easaa.response.bean;

import java.io.Serializable;

/**基类，返回数据必有的字段，所有返回数据类都必须继承于该类*/
public class BasePageEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * true为正常.false为异常
	 */
	public boolean result;
	/**
	 * 返回信息
	 */
	public String msg;

}
