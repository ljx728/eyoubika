package com.eyoubika.system.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.system.web.VO.SmsVO;
import com.eyoubika.system.service.IImageService;
import com.eyoubika.system.application.ImageAL;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ContentUtil;
/*==========================================================================================*
 * Description:	定义了短信服务的接口实现
 * Class:		SmsServiceImpl
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-26 9:14:23
 *==========================================================================================*/
public class ImageServiceImpl extends BaseService implements IImageService{
	ImageAL imageAL;
	

	public ImageAL getImageAL() {
		return imageAL;
	}


	public void setImageAL(ImageAL imageAL) {
		this.imageAL = imageAL;
	}


	@Override
	public Map<String, Object> uploadImage(HttpServletRequest request) {
		imageAL.uploadImage(request);
		return this.buildRetData( "0", "0");	//用户注册前，userId=0
	}
}