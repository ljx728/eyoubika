package com.eyoubika.common;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.eyoubika.util.CommonUtil;

/*==========================================================================================*
 * Description:	配置文件读取器
 * Class:		PropertyReader
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年8月22日 下午7:07:40
 *==========================================================================================*/
public class PropertyReader {
	public List<Map<String, String>> properties;
	public PropertyReader(){
		String fileName = "Eyoubika.properties";
		String fileDir = CommonUtil.APPSRC_PATH +"conf/";
		String fileLine = CommonUtil.fromFile(fileName, fileDir);
		properties = new ArrayList<Map<String, String>>();
		extractProperties(fileLine);
		
	}
	public void extractProperties(String fileLine){
		String propLines[] = fileLine.split("\n");
		int propTotal = propLines.length;		
		properties = new ArrayList<Map<String, String>>();
		for (int i = 0; i < propTotal; i++){
			Map<String, String> p = new HashMap<String, String>();
			String ps[] = propLines[i].split("=");
			p.put(ps[0].trim(), ps[1].trim());
			properties.add(p);
		}
	}
	
	public String getProperty(String property){
		int length = properties.size();
		for (int i = 0; i < length; i++){
			if (properties.get(i).containsKey(property)){
				return properties.get(i).get(property);
			}
		}
		return null;
	}
	
	
}