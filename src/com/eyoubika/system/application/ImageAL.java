package com.eyoubika.system.application;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;

import com.eyoubika.util.CommonUtil;

public class ImageAL{
	 
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		uploadImage
	 * Author:		1.0 created by lijiaxuan at 2015年7月17日 上午11:37:42
	 *--------------------------------------------------------------------------------------*/
	public String uploadImage(HttpServletRequest request ){
		
		 String batchNo = "";
	    	//磁盘文件条目
	    	DiskFileItemFactory factory = new DiskFileItemFactory();
	    	//上传文件的路径
	    	String savePath = CommonUtil.TRANS_IMG_DIR;
	    	System.out.println("savePath " + savePath);
	    	//设置缓存
	    	factory.setRepository(new File(savePath));
	    	//设置缓存大小，如果超过阀值，直接放到临时文件。。。
	    	 factory.setSizeThreshold(4096); 
	    	 
	    	ServletFileUpload upload = new ServletFileUpload(factory);
	    	 upload.setSizeMax(1000000 * 20);  
	    	//HttpServletRequest request = ServletActionContext.getRequest();
	    	try {
	    		
	    		List<FileItem> list= (List<FileItem>) upload.parseRequest(request);
	    		String id = "";
	    		batchNo =getBatchNo(list);
	    		for (FileItem item : list ){	    			
	    			//获取表单的属性名字
	    			String name = item.getFieldName();
	    			//如果获取的是普通文本
	    			if (item.isFormField()){
	    				//获取字符串
	    				String value = item.getString();
	    				if (name.equals("id")){
	    					id = value.replace("WU_FILE_", "");
	    				}
	    				request.setAttribute(name, value);
	    			} else{ //传入的非简单字符串处理，比如二进制数据。
	    				id = id + 1; 
	    				//获取路径名
	    				String value = item.getName();
	    				System.out.println("value " + value);
	    				//索引到最后一个反斜杠
	    				int start = value.lastIndexOf("\\");
	    				//截取上传文件的字符串名字
	    				String fileName = value.substring(start + 1);
	    				String subfix = fileName.substring(fileName.lastIndexOf(".")+1);
	    				//System.out.println("fileName " + fileName);
	    				request.setAttribute(name, fileName);
	    				item.write(new File(savePath, fileName));
	    				  				
	    				if (!batchNo.equals("")){		
	    					CommonUtil.renameFile(savePath,fileName,batchNo+"_"+id+"."+ subfix);
	    				}
	    				
	    			}
	    		}
	    	}catch (Exception e){
	    		
	    	}
	    	return batchNo;
	    }
	
	public String getBatchNo(List<FileItem> list){
		String batchNo = "";
		for (FileItem item : list ){
			//获取表单的属性名字
			String name = item.getFieldName();
			if (item.isFormField()){
				//获取字符串
				String value = item.getString();
				if (name.equals("batchNo")){
					batchNo = value;
				}
				
			}
		}
		System.out.println("batchNo " + batchNo);
		return batchNo;
	}
}