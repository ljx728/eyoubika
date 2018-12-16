package com.eyoubika.system.service;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eyoubika.system.web.VO.SmsVO;
/*==========================================================================================*
 * Description:	定义了短信服务接口
 * Class:		ISmsService
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-26 9:14:10
 *==========================================================================================*/
public interface IImageService {
	
	public Map<String, Object> uploadImage(HttpServletRequest request);
}