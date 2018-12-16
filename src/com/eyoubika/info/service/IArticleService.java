package com.eyoubika.info.service;
import java.util.Map;

import com.eyoubika.info.web.VO.ArticleVO;
import com.eyoubika.common.PageInfo;

/*==========================================================================================*
 * Description:	TODO
 * Class:		IArticleService
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年10月7日 下午11:55:37
 *==========================================================================================*/
public interface IArticleService {	
	public Map<String, Object> getArticle(ArticleVO articleVO);	
	public Map<String, Object> getArticleList(ArticleVO articleVO, PageInfo pageInfo);
}