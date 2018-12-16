package com.eyoubika.user.web.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.PageInfo;
import com.eyoubika.common.YbkException;
import com.eyoubika.system.application.ImageAL;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.user.service.IOpenAccountService;
import com.eyoubika.user.web.VO.OpenAccountVO;
import com.eyoubika.user.web.VO.QueryOpenAccountVO;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
 *==========================================================================================*/
public class OpenAccountAction extends BaseAction {
	private IOpenAccountService openAccountService;	//<<attrNote>>
	private String fileName;  
	 
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public IOpenAccountService getOpenAccountService(){
		return openAccountService;
	}
	public void setOpenAccountService(IOpenAccountService openAccountService){
		this.openAccountService = openAccountService;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public String addOpenAccount(){
		OpenAccountVO openAccountVO = new OpenAccountVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		ConverterUtil.RequestToVO(request, openAccountVO);
		//> 3. do
		this.jsonData = openAccountService.addOpenAccount(openAccountVO);
		//> 4. return json
		openAccountVO = null;
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public String getOpenAccount(){
		OpenAccountVO openAccountVO = new OpenAccountVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("openNo") && null==request.getParameter("openType")  && null==request.getParameter("token")){
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		ConverterUtil.RequestToVO(request, openAccountVO);
		//> 3. do
		this.jsonData = openAccountService.getOpenAccount(openAccountVO );
		//> 4. return json
		openAccountVO = null;
		return SUCCESS;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public String modifyOpenAccount(){
		OpenAccountVO openAccountVO = new OpenAccountVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		/*if (null==request.getParameter("openType")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}*/
		//> 2. build vo
		ConverterUtil.RequestToVO(request, openAccountVO);
		//> 3. do
		this.jsonData = openAccountService.modifyOpenAccount(openAccountVO);
		//> 4. return json
		openAccountVO = null;
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public String getOpenAccountList(){
		OpenAccountVO openAccountVO = new OpenAccountVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		PageInfo pageInfo= new PageInfo();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}			
		pageInfo.assign(request);	
		//> 2. build vo
		ConverterUtil.RequestToVO(request, openAccountVO);
		//> 3. do
		this.jsonData = openAccountService.getOpenAccountList(openAccountVO, pageInfo);
		//> 4. return json
		openAccountVO = null;
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public String modifyAccountDetail(){
		OpenAccountVO openAccountVO = new OpenAccountVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId") && null==request.getParameter("openType")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		ConverterUtil.RequestToVO(request, openAccountVO);
		//> 3. do
		this.jsonData = openAccountService.modifyAccountDetail(openAccountVO);
		//> 4. return json
		openAccountVO = null;
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public String uploadAccountDetail(){
		OpenAccountVO openAccountVO = new OpenAccountVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		ConverterUtil.RequestToVO(request, openAccountVO);
		//> 3. do
		this.jsonData = openAccountService.uploadAccountDetail(openAccountVO);
		//> 4. return json
		openAccountVO = null;
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		uploadAccountImage
	 * Author:		1.0 created by lijiaxuan at 2015年8月18日 下午5:10:14
	 *--------------------------------------------------------------------------------------*/
	public String uploadAccountImage(){
		System.out.println("in uploadifal......");
		HttpServletRequest request = ServletActionContext.getRequest();
		//ImageAL imageAL = new ImageAL();
		//String batchNo = imageAL.uploadImage(request);
		this.jsonData = openAccountService.uploadAccountImage(request);
		//imageAL = null;
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getDownloadFile
	 * Author:		1.0 created by lijiaxuan at 2015年8月18日 下午5:10:11
	 *--------------------------------------------------------------------------------------*/
	public InputStream getDownloadFile() throws FileNotFoundException{
		String srcFile=CommonUtil.RESOURCE_DIR+   "a.xls";
		this.fileName = "abc.xls";
		File file=new File(srcFile);//得到一个file对象
		return new FileInputStream(file);//返回一个文件输入流
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getAccountReport
	 * Author:		1.0 created by lijiaxuan at 2015年8月18日 下午5:10:04
	 *--------------------------------------------------------------------------------------*/
	public InputStream getAccountReport(){
		QueryOpenAccountVO queryOpenAccountVO = new QueryOpenAccountVO();
		//> 2. build vo
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null==request.getParameter("userId")){
		//if (null==request.getParameter("userId")  && null==request.getParameter("token")){
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		ConverterUtil.RequestToVO(request, queryOpenAccountVO);
		//> 3. do	
		 this.fileName = openAccountService.createReport(queryOpenAccountVO);
		 return openAccountService.getAccountReport(queryOpenAccountVO);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getAccountZip
	 * Author:		1.0 created by lijiaxuan at 2015年8月18日 下午5:10:08
	 *--------------------------------------------------------------------------------------*/
	public InputStream getAccountZip(){
		
		QueryOpenAccountVO queryOpenAccountVO = new QueryOpenAccountVO();
		//> 2. build vo
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null==request.getParameter("userId")  && null==request.getParameter("token")){
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		ConverterUtil.RequestToVO(request, queryOpenAccountVO);
		//> 3. do	
	
		 this.fileName = openAccountService.createImageZip(queryOpenAccountVO);

		 return openAccountService.getAccountZip(queryOpenAccountVO);
	}
}
