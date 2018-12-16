package com.eyoubika.common;

import java.io.IOException;
import java.io.Reader;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
/*==========================================================================================*
 * Description:	BaseDao
 * Class:		BaseDao
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-23 15:32:39
 *==========================================================================================*/
public class BaseDao{
	protected Integer start;	//分页条数
	protected Integer limit;	//页大小
	protected static SqlMapClient sqlMapClient = null;
	static {
		try {
			Reader reader = Resources.getResourceAsReader("conf/SqlMapConfig.xml");
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setPage(){		
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request.getAttribute("start")!=null && request.getAttribute("limit")!=null){
			start = Integer.parseInt(request.getAttribute("start").toString());
			limit = Integer.parseInt(request.getAttribute("limit").toString());
		}	
	}
}