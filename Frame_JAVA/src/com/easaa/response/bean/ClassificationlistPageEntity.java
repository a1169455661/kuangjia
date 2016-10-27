package com.easaa.response.bean;

import java.util.List;

public class ClassificationlistPageEntity extends BasePageEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<ClassificationlistEntity> data;

	public class ClassificationlistEntity{
		public String id;// id
		public String icon;// 图片链接
		public String name;// 名称
	}
}
