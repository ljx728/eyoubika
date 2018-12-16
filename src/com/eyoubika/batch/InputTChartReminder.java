package com.eyoubika.batch;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.info.application.QuotationsAL;



public class InputTChartReminder extends TimerTask implements ServletContextAware{
	private ServletContext servletContext;  
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
	
	    this.servletContext = servletContext;   
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		setServletContext(servletContext);  
		
	    try {  
	     // System.out.println(this.servletContext.getRealPath("/"));  
	    } catch (Exception e) {  
	      e.printStackTrace();  
	    }  
	}
	
	
	
	
}