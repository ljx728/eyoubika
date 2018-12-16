package com.eyoubika.util;
import com.eyoubika.common.YbkException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexUtil{
	
	public static boolean isDigitString(String str){
		if (str == null || str.equals("")){
			return false;
		}
		boolean flag = true;
		String regex = "[^0-9]+";
		Matcher matcher = Pattern.compile(regex).matcher(str);
		
		if (matcher.find()){
			flag = false;
		}
		return flag;
	}
	
	public static String getExclusiveString(String regexStr, String str){
		if (str == null || str.equals("")){
			return null;
		}
		String res = null;	
		Matcher matcher = Pattern.compile(regexStr).matcher(str);
		if (matcher.find()){
			str = str.substring(matcher.start(), str.length());			
			res = str.replaceFirst(matcher.group(), "").trim();
			if (res.contains("【")) {
				res = res.substring(0, res.indexOf('【')).trim();
			}
		}
		return res;
	}
	
	public static String getExclusiveString(String regexStr, String str, int length){
		if (length == 0){
			length = 64;
		}
		if (str == null || str.equals("")){
			return null;
		}
		String res = null;	
		//System.out.println("str: " + str);		
		//regexStr = "【*(藏 *品 *)?(名 *称)】*[：:]*";
		Matcher matcher = Pattern.compile(regexStr).matcher(str);
		if (matcher.find()){
			str = str.substring(matcher.start(), str.length());
			for (int i = 0 ; i < matcher.start(); i ++){
				
			}
			
			res = str.replace(matcher.group(0), "");
			if (res.contains("【")) {
				res = res.substring(0, res.indexOf('【'));
			} else {
				if (res.indexOf(' ') > 0) {
					res = res.substring(0, res.indexOf(' '));
				}			
			}
		}
		if ( res != null && res.length() > length){
			System.out.println("string $$$$$$  " + res);
			System.out.println("string $$$$$$ length  " + res.length());
			res = null;
		}
		return res;
	}
}