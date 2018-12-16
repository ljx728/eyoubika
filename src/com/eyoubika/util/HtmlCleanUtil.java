package com.eyoubika.util;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.eyoubika.common.CommonVariables;
/*==========================================================================================*
 * Description:	定义了MD5工具包
 * Class:		ConverterUtil
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-17 20:10:32
 *==========================================================================================*/
public class HtmlCleanUtil{
	private static MessageDigest md5 = null;
	private final static Whitelist user_content_filter = Whitelist.relaxed();
	static {
		//增加可信标签到白名单
		user_content_filter.addTags("embed","param","span","div");
		user_content_filter.addAttributes("id", "class");
		//user_content_filter.addTags("embed","object","param","span","div");
		//增加可信属性
		//user_content_filter.addAttributes(":all", "style", "class", "id", "name");
		//user_content_filter.addAttributes("object", "width", "height","classid","codebase");
		//user_content_filter.addAttributes("param", "name", "value");
		//user_content_filter.addAttributes("embed", "src","quality","width","height","allowFullScreen","allowScriptAccess","flashvars","name","type","pluginspage");
	}
 
	/**
	 * 对用户输入内容进行过滤
	 * @param html
	 * @return
	 */
	/*public static String filter(String html) {
		if(StringUtil.isBlank(html)) return "";
		return Jsoup.clean(html, user_content_filter);
		//return filterScriptAndStyle(html);
	}*/
 
	/**
	 * 比较宽松的过滤，但是会过滤掉object，script， span,div等标签，适用于富文本编辑器内容或其他html内容
	 * @param html
	 * @return
	 */
	public static String relaxed(String html) {
		return Jsoup.clean(html, Whitelist.relaxed());
	}
 
	/**
	 * 去掉所有标签，返回纯文字.适用于textarea，input
	 * @param html
	 * @return
	 */
	public static String pureText(String html) {
		return Jsoup.clean(html, Whitelist.none());
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String unsafe = "<table><tr><td>1</td></tr></table>" +
				"<img src='' alt='' />" +
				"<p><a href='http://example.com/' onclick='stealCookies()'>Link</a>" +
				"<object></object>" +
				"<span></span>"+
				"<div></div>"+
				"<span>hahha</span>"+
				"<span> </span>"+
				"<p> </p>"+
				"<p>23</p>"+
				"<span></span>"+
				"<p style=\"margin: 0cm 0cm 0.0001pt; font-size: 10.5pt; text-align: right; line-height: 150%; word-break: break-all;\" align=\"right\"></p>"+
				"<script>alert(1);</script>" +
				"</p>";
		String safe = HtmlCleanUtil.filter(unsafe);
		
		System.out.println("safe: " + safe);
		System.out.println("@@@jieguo: " + removeNullTag(safe));
		
	}

    
    /*--------------------------------------------------------------------------------------*
     * Description:	自定义对用户输入内容进行过滤的标签
     * Method:		filter
     * Author:		1.0 created by lijiaxuan at 2015年10月7日 下午12:40:03
     *--------------------------------------------------------------------------------------*/
    public static String filter(String html) {
        if(CommonUtil.isStringNull(html)) return "";
        String baseUri = "http://baseuri";
        return Jsoup.clean(html, baseUri, user_content_filter).replaceAll("src=\"http://baseuri", "src=\"");
    }
    
    public static String imgFileter(String html) {
        if(CommonUtil.isStringNull(html)) return "";
        String baseUri = "http://baseuri";
        //user_content_filter = Whitelist.relaxed();
        return Jsoup.clean(html, baseUri, user_content_filter).replaceAll("src=\"http://baseuri", "src=\"");
    }
    
    /*--------------------------------------------------------------------------------------*
     * Description:	TODO
     * Method:		removeNullTag
     * Author:		1.0 created by lijiaxuan at 2015年10月7日 下午11:20:07
     *--------------------------------------------------------------------------------------*/
    public static String removeNullTag(String html){
    	if(CommonUtil.isStringNull(html)) return "";
    	Pattern p1 = Pattern.compile("<(span|p|div)>(\\s*)</(span|p|div)>");
    	Matcher m1 = p1.matcher(html);
    	while (m1.find()) {
    		html = html.replace( m1.group(0), "");
    	}
    	return html;
    }
 
    
    /*--------------------------------------------------------------------------------------*
     * Description:	TODO
     * Method:		removeTag
     * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午2:41:36
     *--------------------------------------------------------------------------------------*/
    public static String removeTag(String html, String tag, String regex){
    	String regEx_html="</?"+tag+"+>"; //定义HTML标签的正则表达式 
    	Pattern pattern = Pattern.compile(regex);
    	String res[] = html.split(regEx_html);
		if (res == null) {
			  return html;
		}
		int size = res.length;
		for (int i = 0; i < size; i++){
			if (res[i] == null || res[i].equals("")){
				continue;
			} 
			Matcher matcher = pattern.matcher(res[i]);
			if (matcher.find()) {	//如果匹配，则删除该段标签的所有内容
				html = html.replace(res[i], "");
			}
		}
		return html; 
    }
    
    /*--------------------------------------------------------------------------------------*
     * Description:	TODO
     * Method:		replaceBlank
     * Author:		1.0 created by lijiaxuan at 2015年10月7日 下午11:07:00
     *--------------------------------------------------------------------------------------*/
    public static String removeBlank(String str, String level) {
    	if(CommonUtil.isStringNull(str)) return "";
    	String regex = "";
    	if (CommonUtil.isStringNull(level)){
    		level = CommonVariables.COMMON_LEVEL_01;
    	} 	
    	String dest = "";
    	if (level.equals(CommonVariables.COMMON_LEVEL_01)){
    		regex = "\r|\n";
    	} else if (level.equals(CommonVariables.COMMON_LEVEL_02)){
    		regex = "\t|\r|\n";
    	} else if (level.equals(CommonVariables.COMMON_LEVEL_03)){
    		regex = "\\s*";
    	} else if (level.equals(CommonVariables.COMMON_LEVEL_04)){
    		regex = "\\s*|\t|\r|\n";
    	}
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(str);
    	dest = m.replaceAll("");
    	return dest;
    }
}