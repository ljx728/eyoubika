package com.eyoubika.util;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import com.eyoubika.common.YbkException;

import javax.servlet.http.HttpServletRequest;

import com.eyoubika.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.omg.CORBA.Request;
/*==========================================================================================*
 * Description:	定义了转换器工具包
 * Class:		ConverterUtil
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-18 21:58:32
 *==========================================================================================*/

public class ConverterUtil{
	/*--------------------------------------------------------------------------------------*
	 * Description:	字符串编码转化
	 * Method:		stringForm
	 * Author:		1.0 created by lijiaxuan at 2015年6月2日 下午4:48:03
	 *--------------------------------------------------------------------------------------*/
	public static String utf8Encode(String str) throws UnsupportedEncodingException{
		String form = "";
		String covertStr = null;
		if(str.equals(new String(str.getBytes("GB2312"), "GB2312")))
		{		
			form = "GB2312";
			covertStr=new String(str.getBytes("GB2312"),"utf-8");
		}
		else if(str.equals(new String(str.getBytes("iso8859-1"), "iso8859-1")))
		{		
			form = "iso8859-1";
			covertStr=new String(str.getBytes("iso8859-1"),"utf-8");
		}
		else if(str.equals(new String(str.getBytes("utf-8"), "utf-8")))
		{		
			form = "utf-8";
			covertStr=str;
		} else {
			form = "unkwown";
			covertStr=str;
		}

		return covertStr;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	getFieldTypeFromName
	 * Method:		getFieldTypeFromName
	 * Parameters:	String fieldName, Field[] fields
	 * History:		1.0 created by lijiaxuan at 2015-5-18 22:56:48
	 *--------------------------------------------------------------------------------------*/
	private static String getFieldTypeFromName(String fieldName, Field[] fields){
		for (int i = 0; i < fields.length; i++){
			if (fields[i].getName().equals(fieldName)){
				return fields[i].getType().getName();
			}
		}
		return null;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	addProperties
	 * Method:		addProperties
	 * Parameters:	Field[] fields, List<String> propList
	 * History:		1.0 created by lijiaxuan at 2015-5-18 23:05:12
	 *--------------------------------------------------------------------------------------*/
	private static void addProperties(Field[] fields, List<String> propList){
		for (int i = 0; i < fields.length; i++){
			propList.add(fields[i].getName());
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	mapPropertyAndMethod
	 * Method:		mapPropertyAndMethod
	 * Parameters:	Field[] field, Method[] methods
	 * History:		1.0 created by lijiaxuan at 2015-5-18 23:02:09
	 *--------------------------------------------------------------------------------------*/
	private static Map<String, Method> mapPropertyAndMethod(Field[] field, Method[] methods)
	{
		Map<String, Method> map = new HashMap<String, Method>();
		for (int i = 0; i < field.length; i++)
		{
			String fieldName = field[i].getName();
			String key = fieldName;
			char first = fieldName.toUpperCase().charAt(0);
			fieldName = first + fieldName.substring(1);
			String methodName = "set" + fieldName;

			for (int j = 0; j < methods.length; j++)
			{
				if (methods[j].getName().equalsIgnoreCase(methodName))
				{
					map.put(key, methods[j]);
				}
			}
		}
		return map;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	RequestToVO
	 * Method:		RequestToVO
	 * Parameters:	HttpServletRequest request, Object vo
	 * History:		1.0 created by lijiaxuan at 2015-5-18 23:52:38
	 *--------------------------------------------------------------------------------------*/
	public static Object RequestToVO(HttpServletRequest request, Object vo) {
		Iterator iter = request.getParameterMap().entrySet().iterator();	
		Class voClass = vo.getClass();
		Field[] voFields = voClass.getDeclaredFields();
		List<String> voProperties = new LinkedList<String>();
		
		Map<String, Method> voMap = new HashMap<String, Method>();
		Method[] voMethods = voClass.getDeclaredMethods();
		addProperties(voFields, voProperties);
		voMap = mapPropertyAndMethod(voFields, voMethods);
		while (iter.hasNext()){
			Entry entry = (Entry) iter.next();
			String curFieldName = entry.getKey().toString();
			//System.out.println("curFieldName: " + curFieldName);
			if (voProperties.contains((String) curFieldName)){
				Method m = voMap.get(curFieldName);
				try {
					//System.out.println("method: " + m.getName());
					//System.out.println("entry.getValue(): " + ((String[])entry.getValue())[0]);
					m.invoke(vo, ((String[])entry.getValue())[0].trim());
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
					throw new YbkException(YbkException.CODE000011, YbkException.DESC000011);
				}
			}		
		}		
		return vo;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	VOToDomain
	 * Method:		VOToDomain
	 * Parameters:	Object vo, Object domain
	 * History:		1.0 created by lijiaxuan at 2015-5-18 23:35:24
	 *--------------------------------------------------------------------------------------*/
	public static Object VOToDomain(Object vo, Object domain)//, int dateType)
	{
		if ((null != domain) && (null != vo))
		{
			Class voClass = vo.getClass();
			Class doClass = domain.getClass();
			Field[] voFields = voClass.getDeclaredFields();
			Field[] doFields = doClass.getDeclaredFields();
			List<String> voProperties = new LinkedList<String>();
			List<String> doProperties = new LinkedList<String>();
			

			Map<String, Method> doMap = new HashMap<String, Method>();
			Method[] doMethods = doClass.getDeclaredMethods();
			
			addProperties(voFields, voProperties);
			addProperties(doFields, doProperties);

			doMap = mapPropertyAndMethod(voFields, doMethods);
			int errPosition=0;
			for (int i = 0; i < doFields.length; i++)
				doFields[i].setAccessible(true);
			try
			{
				for (int i = 0; i < voFields.length; i++)
				{
					
					voFields[i].setAccessible(true); errPosition=i;
					String curVoPropertyName = voFields[i].getName();
					if (doProperties.contains(curVoPropertyName))
					{

						String desType = getFieldTypeFromName(voFields[i].getName(), doFields);
						Method m = doMap.get(curVoPropertyName);
						Object curObject = voFields[i].get(vo);
						if (null != curObject)
						{
							if (desType.equalsIgnoreCase("java.lang.String"))
							{
								
								if (!CommonUtil.isStringNull(voFields[i].get(vo).toString()))
								{
									m.invoke(domain, voFields[i].get(vo));
								}
							}else if (desType.equalsIgnoreCase("java.lang.Integer"))
							{
								String intValue = (String) (voFields[i].get(vo));
								if (!CommonUtil.isStringNull(intValue))
								{
									Integer desValue = Integer.parseInt(intValue);
									if (null != desValue)
										m.invoke(domain, desValue);
								}
							}else if (desType.equalsIgnoreCase("java.lang.Float"))
							{
								String floatValue = (String) (voFields[i].get(vo));
								if (!CommonUtil.isStringNull(floatValue))
								{
									Float desValue = Float.parseFloat(floatValue);
									if (null != desValue)
										m.invoke(domain, desValue);
								}
							}else
							{
								m.invoke(domain, voFields[i].get(vo));
							}
						}
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				throw new YbkException(YbkException.CODE000013, YbkException.DESC000013 
						+ "，出错字段：" + voFields[errPosition]);
			}
			return domain;		
		} else
		{
			throw new YbkException(YbkException.CODE000019, YbkException.DESC000019);
		}
	}
	
	
	public static Object DomainToDomain(Object vo, Object domain)//, int dateType)
	{
		if ((null != domain) && (null != vo))
		{
			Class voClass = vo.getClass();
			Class doClass = domain.getClass();
			Field[] voFields = voClass.getDeclaredFields();
			Field[] doFields = doClass.getDeclaredFields();
			List<String> voProperties = new LinkedList<String>();
			List<String> doProperties = new LinkedList<String>();
			

			Map<String, Method> doMap = new HashMap<String, Method>();
			Method[] doMethods = doClass.getDeclaredMethods();
			
			addProperties(voFields, voProperties);
			addProperties(doFields, doProperties);

			doMap = mapPropertyAndMethod(voFields, doMethods);
			int errPosition=0;
			for (int i = 0; i < doFields.length; i++)
				doFields[i].setAccessible(true);
			try
			{
				for (int i = 0; i < voFields.length; i++)
				{
					
					voFields[i].setAccessible(true); errPosition=i;
					String curVoPropertyName = voFields[i].getName();
					
					if (doProperties.contains(curVoPropertyName))
					{

						String desType = getFieldTypeFromName(voFields[i].getName(), doFields);
						Method m = doMap.get(curVoPropertyName);
						Object curObject = voFields[i].get(vo);
						if (null != curObject)
						{
							m.invoke(domain, voFields[i].get(vo));
						}
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				throw new YbkException(YbkException.CODE000013, YbkException.DESC000013 
						+ "，出错字段：" + voFields[errPosition]);
			}
			return domain;		
		} else
		{
			throw new YbkException(YbkException.CODE000019, YbkException.DESC000019);
		}
	}
	 
	public static Object DomainToVO(Object domain, Object vo )//, int dateType)
	{
		if ((null != domain) && (null != vo))
		{
			Class voClass = vo.getClass();
			Class doClass = domain.getClass();
			Field[] voFields = voClass.getDeclaredFields();
			Field[] doFields = doClass.getDeclaredFields();
			List<String> voProperties = new LinkedList<String>();
			List<String> doProperties = new LinkedList<String>();
			

			Map<String, Method> voMap = new HashMap<String, Method>();
			Method[] voMethods = voClass.getDeclaredMethods();
			
			addProperties(voFields, voProperties);
			addProperties(doFields, doProperties);

			voMap = mapPropertyAndMethod(doFields, voMethods);
			int errPosition=0;
			for (int i = 0; i < voFields.length; i++)
				voFields[i].setAccessible(true);
			try
			{
				for (int i = 0; i < doFields.length; i++)
				{
					
					doFields[i].setAccessible(true); errPosition=i;
					String curDoPropertyName = doFields[i].getName();
					
					if (voProperties.contains((String) curDoPropertyName))
					{

						String currenType = doFields[i].getType().getName();//getFieldTypeFromName(doFields[i].getName(), voFields);
						Method m = voMap.get(curDoPropertyName);
						Object curObject = doFields[i].get(domain);
						if (null != curObject)
						{
							/*if (currenType.equalsIgnoreCase("java.lang.Integer"))
							{
								 intValue = (String) (voFields[i].get(vo));
								if (!CommonUtil.isStringNull(intValue))
								{
									Integer desValue = Integer.parseInt(intValue);
									if (null != desValue)
										m.invoke(domain, desValue);
								}
							}else if (desType.equalsIgnoreCase("java.lang.Float"))
							{
								String floatValue = (String) (voFields[i].get(vo));
								if (!CommonUtil.isStringNull(floatValue))
								{
									Float desValue = Float.parseFloat(floatValue);
									if (null != desValue)
										m.invoke(domain, desValue);
								}
							}else*/
							{
								m.invoke(vo, doFields[i].get(domain).toString());
							}
						}
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				throw new YbkException(YbkException.CODE000013, YbkException.DESC000013 
						+ "，出错字段：" + doFields[errPosition]);
			}
			return domain;		
		} else
		{
			throw new YbkException(YbkException.CODE000019, YbkException.DESC000019);
		}
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	DomainToVO
	 * Method:		DomainToVO
	 * Parameters:	Object domain, Object vo
	 * History:		1.0 created by lijiaxuan at 2015-5-18 23:17:37
	 *--------------------------------------------------------------------------------------*/
	public static JSONObject objectToJson(Object object) {
		String result = null;
		if (object != null){
			//JSONObject jsonObject = null;			
			Map<String, Object> map = new HashMap<String, Object>();
			if (object instanceof List){
				map.put("list", object);
				return JSONObject.fromObject(map);
				//jsonObject 
			} else {
				//List<Object> list = new ArrayList<Object>();
				//list.add(object);
				//map.put("data", list);
				//jsonObject = JSONObject.fromObject(map);
				//result = jsonObject.toString();
				//list.clear();
				//list = null;
				//result = JSONObject.fromObject(object);//;.toString();//.replace("\\\\\"", "\"");
				return JSONObject.fromObject(object);
			}
		}

		return JSONObject.fromObject(object);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		ObjectToJson
	 * Author:		1.0 created by lijiaxuan at 2015年6月28日 下午12:21:35
	 *--------------------------------------------------------------------------------------*/
	public static JSONObject listToJson(Object list, int size) {
		String result = null;
		if (list != null){
			//JSONObject jsonObject = null;			
			Map<String, Object> map = new HashMap<String, Object>();		
				map.put("list", list);
				map.put("size", size);
				return JSONObject.fromObject(map);			
		
		}
		return JSONObject.fromObject(list);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		dataToJson
	 * Author:		1.0 created by lijiaxuan at 2015年8月1日 下午2:36:55
	 *--------------------------------------------------------------------------------------*/
	public static JSONObject dataToJson(Map<String, Object> params) {
		if (params != null){
			//JSONObject jsonObject = null;			
			Map<String, Object> map = new HashMap<String, Object>();		
				
				Iterator<Map.Entry<String, Object>> entries = params.entrySet().iterator();  
				  
				while (entries.hasNext()) {  
				    Map.Entry<String, Object> entry = entries.next();  
				  
				    if (entry.getValue() == null){
						continue;
					}
				    map.put( entry.getKey(), entry.getValue());
				}  
				
				return JSONObject.fromObject(map);			
		
		} else{
			return null;
		}
	}
	public static Object JSONStringToDomain(String jsonString){
		
		return null;
	}
	/**
	 * @param vo
	 * @param domain
	 * @param dateType
	 *            :日期类型，VO中的String类型日期 格式：yyyy-MM-dd；1为yyyyMMdd；2为yyyyMMddHHmmsss
	 *//*
	public static Object VOToDomain(Object vo, Object domain, int dateType)
	{
		if ((null != domain) && (null != vo))
		{
			Class cv = vo.getClass();
			Class cd = domain.getClass();

			Field[] fv = cv.getDeclaredFields();
			Field[] fd = cd.getDeclaredFields();

			Map<String, Method> domainmap = new HashMap<String, Method>();
			Method[] md = cd.getDeclaredMethods();

			List<String> domainProperties = new LinkedList<String>();
			List<String> voProperties = new LinkedList<String>();
			addProperties(fd, domainProperties);
			addProperties(fv, voProperties);

			domainmap = mapPropertyAndMethod(fd, md);
            int errPosition=0;
			for (int i = 0; i < fd.length; i++)
				fd[i].setAccessible(true);
			   
			try
			{
				for (int i = 0; i < fv.length; i++)
				{
					fv[i].setAccessible(true); errPosition=i;
					// fd[i].setAccessible(true);
					String curVoPropertyName = fv[i].getName();
					if (domainProperties.contains(curVoPropertyName))
					{

						String desType = getFieldTypeFromName(fv[i].getName(), fd);
						// System.out.println(curVoPropertyName+" "+desType);
						Method m = domainmap.get(curVoPropertyName);
						Object curObject = fv[i].get(vo);
						if (null != curObject)
						{
							if (desType.equalsIgnoreCase("java.lang.String"))
							{
								if (!CommonUtil.isStringNull(fv[i].get(vo).toString()))
								{
									m.invoke(domain, fv[i].get(vo));
								}
							} else if (desType.equalsIgnoreCase("java.util.Date"))
							{
								String date = (String) (fv[i].get(vo));
								if (!CommonUtil.isStringNull(date))
								{
									Date desDate;
									if (0 == dateType)
									{
										desDate = CommonUtil.getDateFromString(date);
									} else if (1 == dateType)
									{
										desDate = CommonUtil.getDateFromString(date, "yyyyMMdd");
									} else if (2 == dateType)
									{
										desDate = CommonUtil.getDateTimeFromString(date);
									} else
									{
										throw new HermesException(HermesException.CODE6006, HermesException.DESC6006, null);
									}
									if (null != desDate)
									{
										m.invoke(domain, desDate);
									}
								}
							} else if (desType.equalsIgnoreCase("java.lang.Double"))
							{
								String dbvalue = (String) (fv[i].get(vo));
								Double desValue;
								if (StringUtils.isNotBlank(dbvalue))
								{
									int hasComma = dbvalue.indexOf(',');
									if (hasComma > 0)
									{
										desValue = CommonUtil.StringToDoubleWithComma(dbvalue);
									} else
									{
										desValue = ConvertBuilder.stringToDoubleObject(dbvalue);
									}
									m.invoke(domain, desValue);
								}

							} else if (desType.equalsIgnoreCase("java.math.BigDecimal"))
							{
								String dbvalue = (String) (fv[i].get(vo));
								BigDecimal desValue;
								if (StringUtils.isNotBlank(dbvalue))
								{
									int hasComma = dbvalue.indexOf(',');
									if (hasComma > 0)
									{
										desValue = new BigDecimal(CommonUtil.StringToDoubleWithComma(dbvalue));
									} else
									{
										desValue = ConvertBuilder.stringToBigDecimal(dbvalue);
									}
									m.invoke(domain, desValue);
								}

							} else if (desType.equalsIgnoreCase("java.lang.Integer"))
							{
								String intValue = (String) (fv[i].get(vo));
								if (!CommonUtil.isStringNull(intValue))
								{
									Integer desValue = Integer.parseInt(intValue);
									if (null != desValue)
										m.invoke(domain, desValue);
								}
							} else if (desType.equalsIgnoreCase("java.lang.Long"))
							{
								String longValue = (String) (fv[i].get(vo));
								if (!CommonUtil.isStringNull(longValue))
								{
									Long desValue = Long.parseLong(longValue);
									if (null != desValue)
									{
										m.invoke(domain, desValue);
									}
								}
							} else
							{
								m.invoke(domain, fv[i].get(vo));
							}
						}
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				throw new HermesException(HermesException.CODE6007, HermesException.DESC6007 
						+ "，出错字段：" + fv[errPosition], null);
			}
			return domain;
		} else
		{
			throw new HermesException(HermesException.CODE6008, HermesException.DESC6008, null);
		}
	}
	
	
	 * 将map中的值写入到vo中
	 
	public static Object MapToVO(Map<String, String> map, Object vo)
	{
		if ((null != map) && (null != vo))
		{
			Class cv = vo.getClass();
			Field[] fv = cv.getDeclaredFields();
			Map<String, Method> vomap = new HashMap<String, Method>();
			Method[] md = cv.getDeclaredMethods();
			List<String> voProperties = new LinkedList<String>();
			addProperties(fv, voProperties);

			vomap = mapPropertyAndMethod(fv, md);
			Iterator<String> iter = map.keySet().iterator();
			try
			{
				for (int i = 0; i < fv.length; i++)
				{
					fv[i].setAccessible(true);
					fv[i].set(vo, "");
					fv[i].setAccessible(false);
				}

				while (iter.hasNext())
				{
					String key = iter.next();
					if (voProperties.contains(key))
					{
						Method m = vomap.get(key);
						m.invoke(vo, map.get(key));
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				throw new HermesException(HermesException.CODE6020, HermesException.DESC6020, null);
			}
		}
		return vo;
	}
	
	 *//**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     * 
     * @author wanghao
     *//*
    @SuppressWarnings("unchecked")
	public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }*/
	
	public static Map objectToMap(Object bean) {
		try {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                returnMap.put(propertyName, result);
               /* if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }*/
            }
        }
      
        
        return returnMap;
		}catch (IntrospectionException |IllegalAccessException | InvocationTargetException e) {
			throw new YbkException(YbkException.CODE000014, YbkException.DESC000014);
		}
    }
	 /* public static Map<String, String> objectToMap(Object obj) {
	        try {
	            Map<String, String> map = new HashMap<String, String>();
	            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
	            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	            for (PropertyDescriptor property : propertyDescriptors) {
	                if (!property.getName().equals("class")) {
	                    map.put(property.getName(), "" + property.getReadMethod().invoke(obj));
	                }
	            }
	            return map;
	        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
	            throw new RuntimeException(e);
	        }
	    }*/
	  
	/*  public static String jsonArrayToRedisHash(JSONArray jsonArray){
		  String result = "";
		  for (int i = 0; i < jsonArray.size(); i++) {  
				 JSONObject obj = JSONObject.fromObject(jsonArray.get(i));
				 System.out.println("num : " + obj.get("code"));
		  return result;  
	  }*/
}