package com.eyoubika.system.application;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;

import com.eyoubika.util.CommonUtil;
import com.eyoubika.system.domain.DbManagerDomain;
public class DbManagerAL{
	
	public void createSbcTable(DbManagerDomain dbManagerDomain){
		
	}
	
	public boolean isExistTable(String tbName){
		boolean res = false;
		return res;
	}
	
}