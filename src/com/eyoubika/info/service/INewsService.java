package com.eyoubika.info.service;
import java.util.Map;

import com.eyoubika.info.web.VO.NewsVO;
import com.eyoubika.common.PageInfo;
/*==========================================================================================*
 * Description:	定义用户服务接口
 * Class:		INewsService
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-6-27 10:10:23
 *==========================================================================================*/
public interface INewsService {	
	public Map<String, Object> getNews(NewsVO newsVO);	
	//public Map<String, Object> getbs(NewsVO newsVO);	
	public Map<String, Object> getNewsList(NewsVO newsVO, PageInfo pageInfo);	
	public Map<String, Object> getNewsByParams(NewsVO newsVO);	
}