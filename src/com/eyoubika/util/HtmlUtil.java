package com.eyoubika.util;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*==========================================================================================*
 * Description:	定义了MD5工具包
 * Class:		HTMLUtil
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-7-8 10:30:32
 *==========================================================================================*/
public class HtmlUtil{
	public static String delHTMLTag(String htmlStr){   
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式   
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式   
           
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);   
        Matcher m_style=p_style.matcher(htmlStr);   
        htmlStr=m_style.replaceAll(""); //过滤style标签   
           
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);   
        Matcher m_html=p_html.matcher(htmlStr);   
        htmlStr=m_html.replaceAll(""); //过滤html标签   
          
        htmlStr=htmlStr.replace(" ","");  
        htmlStr=htmlStr.replaceAll("\\s*|\t|\r|\n","");  
        htmlStr=htmlStr.replace("“","");  
        htmlStr=htmlStr.replace("”","");  
        htmlStr=htmlStr.replaceAll("　","");  
            
        return htmlStr.trim(); //返回文本字符串   
    }  
	
	public static List<String> getHTMLTagContent(String htmlStr, String tag){
		if (htmlStr == null){
			return null;
		}
		  String regEx_html="</?"+tag+"+>"; //定义HTML标签的正则表达式   
		  String res[] = htmlStr.split(regEx_html);
		  if (res == null) {
			  return null;
		  }
		  List<String> contents= new ArrayList<String>();
		  int size = res.length;
		  for (int i = 0; i < size; i++){
			  
			  if (res[i] == null || res[i].equals("")){
				  continue;
			  }
			  contents.add(res[i]);
		  }
		  return contents; 
		  
	}
	
	
	
	public static String subHTMLString(String htmlStr, String tag, int num, int maxLength){
		  String regEx_html="</?"+tag+"+>"; //定义HTML标签的正则表达式 
		  String res[] = htmlStr.split(regEx_html);
		  String subHtmlString = "";
		  if (res == null) {
			  return null;
		  }
		  int size = res.length;
		  int contentSize = 0;
		  for (int i = 1; i < size  ; i++){			  
			  if (res[i] == null || res[i].equals("")){
				  continue;
			  }
			  if (num != 0 && i >= num){		//标签个数达到上限
				  break;
			  }
			  if (maxLength != 0){
					  if (contentSize >= maxLength){
						  //字符串长度达到上限
						  break;
					  }
					  if (res[i].length() + contentSize > maxLength){	//最后一个标签需要截取字符串
						  subHtmlString += "<"+tag+">" +  res[i].substring(0, maxLength - contentSize) + "</"+tag+">";
					  }else{
						  subHtmlString += "<"+tag+">" +  res[i] + "</"+tag+">";
					  }
					  contentSize += res[i].length();
			  } else {
				  subHtmlString += "<"+tag+">" +  res[i] + "</"+tag+">";
			  }		  
		  } 	 	
		  return subHtmlString;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildInfoHtml
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午2:56:02
	 *--------------------------------------------------------------------------------------*/
	public static String buildInfoHtml(String htmlStr, String title){
		if (CommonUtil.isStringNull(title)){
			title = "易邮币卡官网资讯";
		}
		String html =  "<html><head><title> "+ title + "</title>"
				+ "<link rel=\"stylesheet\" href=\"http://www.eyoubika.com/web/css/article.css\">"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />"
				+ "</head><body>"					
				+ htmlStr
				+ "</body></html>";
		return html;		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildInvestHtml
	 * Author:		1.0 created by lijiaxuan at 2015年11月26日 下午5:21:13
	 *--------------------------------------------------------------------------------------*/
	public static String buildInvestHtml(String htmlStr, String title){
		
		if (CommonUtil.isStringNull(title)){
			title = "易邮币卡交易行情_彩蛋信息";
		}
		
		String html =  "<html><head><title> "+ title + "</title>"
				+ "<meta name=\"keywords\" content=\"彩蛋,易邮币卡,交易所,电子盘\" />"
				+ "<meta name=\"description\" content=\"彩蛋信息科技有限公司，专业的易邮币卡行情分析。提供全方位的邮币卡电子盘资讯，快捷免费开户。\" />"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />"
				+ "<link rel=\"apple-touch-icon\" sizes=\"120x120\" href=\"http://www.eyoubika.com/web/images/iphone.png\">"
				+ "<link rel=\"apple-touch-icon\" sizes=\"152x152\" href=\"http://www.eyoubika.com/web/images/ipad.png\">"
				+ "<link rel=\"stylesheet\" href=\"http://www.eyoubika.com/web/styles/vendor.css\">"
				+ "<link rel=\"stylesheet\" href=\"http://www.eyoubika.com/web/styles/main.css\">"
				+ "<script src=\"http://www.eyoubika.com/web/scripts/vendor.js\"></script>"
				+ "<script src=\"http://www.eyoubika.com/scripts/util.js\"></script>"
				+ "<script src=\"http://www.eyoubika.com/scripts/config.js\"></script>"
				+ "<script src=\"http://www.eyoubika.com/web/scripts/effect.js\"></script>"	         
				+ "</head><body>"	
				+ ""
				+ htmlStr
				+ "</body></html>";
			
		return html;
	}
		 
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildInvestSeoHtml
	 * Author:		1.0 created by lijiaxuan at 2016年1月11日 下午3:08:16
	 *--------------------------------------------------------------------------------------*/
	public static String buildInvestSeoHtml(String htmlStr, String title){
		if (CommonUtil.isStringNull(title)){
			title = "彩蛋易邮币卡交易行情";
		}
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<html>");
		strBuffer.append("    <head>");
		strBuffer.append("        <title>"+title+"</title>");
		strBuffer.append("        <meta charset=\"utf-8\">");
		strBuffer.append("        <meta content=\"彩蛋信息科技有限公司，专业的易邮币卡行情分析。提供全方位的邮币卡电子盘资讯，快捷免费开户。\" name=\"description\">");
		strBuffer.append("        <meta content=\"易邮币卡,邮币卡,彩蛋,交易所,电子盘\" name=\"keywords\">");
		strBuffer.append("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		strBuffer.append("        <link rel=\"apple-touch-icon\" sizes=\"120x120\" href=\"http://www.eyoubika.com/web/images/iphone.png\">");
		strBuffer.append("        <link rel=\"apple-touch-icon\" sizes=\"152x152\" href=\"http://www.eyoubika.com/web/images/ipad.png\">");
		strBuffer.append("        <link rel=\"stylesheet\" href=\"http://www.eyoubika.com/web/styles/vendor.css\">");
		strBuffer.append("        <link rel=\"stylesheet\" href=\"http://www.eyoubika.com/web/styles/main.css\">");
		strBuffer.append("        <script src=\"http://www.eyoubika.com/web/scripts/vendor.js\"></script>");
		strBuffer.append("        <script src=\"http://www.eyoubika.com/scripts/util.js\"></script>");
		strBuffer.append("        <script src=\"http://www.eyoubika.com/scripts/config.js\"></script>");
		strBuffer.append("        <script src=\"http://www.eyoubika.com/web/scripts/effect.js\"></script> ");
		strBuffer.append("    </head>");
		strBuffer.append("    <body>");
		strBuffer.append("        <div class=\"pure-g wapper head\">");
		strBuffer.append("            <div class=\"pure-u-1-4 logo\">");
		strBuffer.append("                <a href=\"http://www.eyoubika.com\" title=\"易邮币卡\" rel=\"nofollow\"><img src=\"http://www.eyoubika.com/web/images/logo.png\" alt=\"易邮币卡\" height=\"35px\"></a>");
		strBuffer.append("            </div>");
		strBuffer.append("            <div class=\"pure-u-3-4\">");
		strBuffer.append("                <div class=\"menu\"><a href=\"http://www.eyoubika.com/web/kh/index.html\" title=\"开户\" rel=\"nofollow\">开户</a></div>");
		strBuffer.append("                <div class=\"menu\"><a href=\"http://www.eyoubika.com/web/download.html\">下载</a></div>");
		strBuffer.append("                <div class=\"menu active\"><a href=\"http://www.eyoubika.com\">资讯</a></div>");
		strBuffer.append("            </div>");
		strBuffer.append("        </div>");
		strBuffer.append("        <!-- menu bar -->");
		strBuffer.append("        <div class=\"pure-g uh-submenu\">");
		strBuffer.append("        <div class=\"wapper\">");
		strBuffer.append("            <ul class=\"pure-u-1\" >");
		strBuffer.append("                <li><a class=\"zhzx\" href=\"http://www.eyoubika.com\">综合资讯</a></li>");
		strBuffer.append("                <li><a class=\"dpdp\" href=\"http://www.eyoubika.com\">大盘点评</a></li>");
		strBuffer.append("                <li><a class=\"tzfx\" href=\"http://www.eyoubika.com\">投资分析</a></li>");
		strBuffer.append("                <li><a clss=\"xpsg\" href=\"http://www.eyoubika.com/web/shengou.html\">新品申购</a></li>");
		strBuffer.append("                <li><a class=\"xptg\" href=\"http://www.eyoubika.com/web/tuoguan.html\">新品托管</a></li>");
		strBuffer.append("                <li id=\"exchangerMenu\">文交所</li>");
		strBuffer.append("            </ul>");
		strBuffer.append("            </div>");
		strBuffer.append("        </div>");
		strBuffer.append("        <div class=\"pure-g uh-submenuMobile\">");
		strBuffer.append("        <div class=\"wapper\">");
		strBuffer.append("            <ul class=\"pure-u-1\" >");
		strBuffer.append("                <li><a class=\"zhzx\" href=\"http://www.eyoubika.com\">资讯</a></li>");
		strBuffer.append("                <li><a class=\"dpdp\" href=\"http://www.eyoubika.com\">点评</a></li>");
		strBuffer.append("                <li><a class=\"tzfx\" href=\"http://www.eyoubika.com\">分析</a></li>");
		strBuffer.append("                <li><a clss=\"xpsg\" href=\"http://www.eyoubika.com/web/shengou.html\">申购</a></li>");
		strBuffer.append("                <li><a class=\"xptg\" href=\"http://www.eyoubika.com/web/tuoguan.html\">托管</a></li>              ");
		strBuffer.append("                <li id=\"exchangerMobileMenu\">文交所</li>");
		strBuffer.append("            </ul>");
		strBuffer.append("            </div>");
		strBuffer.append("        </div>");
		strBuffer.append("        <div class=\"pure-g uh-submenuBlock\"></div>");
		strBuffer.append("        <div class=\"uh-exchangermenu\">");
		strBuffer.append("            <div class=\"wapper\">");
		strBuffer.append("                <div><a class=\"exchangerName\" exId=\"001000\" href=\"http://www.eyoubika.com/web/exchanger.html\">南京</a></div>");
		strBuffer.append("                <div><a class=\"exchangerName\" exId=\"002000\" href=\"http://www.eyoubika.com/web/exchanger.html\">南方</a></div>");
		strBuffer.append("                <div><a class=\"exchangerName\" exId=\"004000\" href=\"http://www.eyoubika.com/web/exchanger.html\">福丽特</a></div>");
		strBuffer.append("                <div><a class=\"exchangerName\" exId=\"003000\" href=\"http://www.eyoubika.com/web/exchanger.html\">中南</a></div>");
		strBuffer.append("                <div><a class=\"exchangerName\" exId=\"005000\" href=\"http://www.eyoubika.com/web/exchanger.html\">金马甲</a></div>");
		strBuffer.append("                <div><a class=\"exchangerName\" exId=\"010000\" href=\"http://www.eyoubika.com/web/exchanger.html\">艺交所</a></div>");
		strBuffer.append("                <div><a class=\"exchangerName\" exId=\"007000\" href=\"http://www.eyoubika.com/web/exchanger.html\">上海</a></div>");
		strBuffer.append("                <div class=\"exchangerName\" id=\"close\">收起</div>");
		strBuffer.append("            </div>");
		strBuffer.append("        </div>");
		strBuffer.append("        </div>");
		strBuffer.append(" <div class=\"uh-content\">");
		strBuffer.append("            <div class=\"pure-g wapper\">");
		strBuffer.append("                <div class=\"pure-u-1 pure-u-md-16-24 \">");
		strBuffer.append("                    <div class=\"uh-article\">");
		
		strBuffer.append(htmlStr);
		strBuffer.append(" <div class=\"pure-u-8-24 hide\">");
		strBuffer.append("                        <div class=\"uh-banner\">");
		strBuffer.append("                            <div class=\"pic\"><img src=\"http://www.eyoubika.com/web/images/down.jpg\"></div>");
		strBuffer.append("                            <div class=\"title\">易邮币卡 手机客户端</div>");
		strBuffer.append("                            <div class=\"desc\">查询、资讯、交易一个App就够了</div>");
		strBuffer.append("                        </div>");
		strBuffer.append("                    </div>");
		strBuffer.append("                </div>");
		strBuffer.append("            </div>");
		strBuffer.append("            <div class=\"contact-qq hide\" onclick=\"window.open('http://wpa.qq.com/msgrd?v=3&uin=82142130&site=qq&menu=yes', '_blank')\">");
		strBuffer.append("                <a> 联系我们</a>");
		strBuffer.append("            </div>");
		strBuffer.append("            <div class=\"footer\">");
		strBuffer.append("                <div class=\"wapper pure-g\">");
		strBuffer.append("                    <div class=\"pure-u-1-4 col hide\">");
		strBuffer.append("                        <ul>");
		strBuffer.append("                            <li class=\"title\">关于</li>");
		strBuffer.append("<li>易邮币卡</li>");
		strBuffer.append("                            <li>彩蛋公司</li>");
		strBuffer.append("                            <li>用户协议</li>");
		strBuffer.append("                        </ul>");
		strBuffer.append("                    </div>");
		strBuffer.append("                    <div class=\"pure-u-1-4 col hide\">");
		strBuffer.append("                        <ul>");
		strBuffer.append("                            <li class=\"title\"> 下载 </li>");
		strBuffer.append("                            <li><a href=\"https://itunes.apple.com/app/yi-you-bi-ka/id1057006343?mt=8\" target=\"_blank\">iOS 客户端</a></li>");
		strBuffer.append("                            <li>Android 客户端</li>");
		strBuffer.append("                        </ul></div>");
		strBuffer.append("                        <div class=\"pure-u-1-4 col hide\">");
		strBuffer.append("                            <ul>");
		strBuffer.append("                                <li class=\"title\">  社交 </li>");
		strBuffer.append("                                <li>微信公众号</li>");
		strBuffer.append("                                <li><a href=\"http://weibo.com/eyoubika?wvr=5\" target=\"_blank\">官方微博</a></li>");
		strBuffer.append("                            </ul></div>");
		strBuffer.append("                            <div class=\"pure-u-1-4 col hide\">");
		strBuffer.append("                                <ul>");
		strBuffer.append("                                    <li class=\"title\">  联系 </li>");
		strBuffer.append("                                    <li>电话 0571-85226989</li>");
		strBuffer.append("                                    <li>邮箱 help@eyoubika.com</li>");
		strBuffer.append("                                    <li>地址 杭州市经开区金沙湖1号</li>");
		strBuffer.append("                                </ul>");
		strBuffer.append("                            </div>");
		strBuffer.append("                        </div>");
		strBuffer.append("                        <div class=\"pure-u-1 call show\" onclick=\"location.href='tel:0571-85226989';\">");
		strBuffer.append("                            <div class=\"pure-u-1\"><img src=\"http://www.eyoubika.com/web/images/phone-call.svg\" width=\"30px\"></div>");
		strBuffer.append("                            <div class=\"pure-u-1\"><a href=\"tel:0571-85226989\">拨打电话 0571-85226989</a></div>");
		strBuffer.append("                        </div>");
		strBuffer.append("                        <div class=\"pure-u-1 copyright\">2015©彩蛋 备案号 浙ICP备 15022997</div>");
		strBuffer.append("                    </div>");
		strBuffer.append("                </div>");
		strBuffer.append("            </body>");
		strBuffer.append("        </html>");
		return strBuffer.toString();
				
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		stuffImgUrl
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午6:24:37
	 *--------------------------------------------------------------------------------------*/
	public static String stuffImgUrl(String html, String host){
	    	String regex="<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"; //定义HTML标签的正则表达式 
	    	Pattern pattern = Pattern.compile(regex); 	
	    	Matcher matcher = pattern.matcher(html);
	    	String url = "";
			while (matcher.find()) {	//如果匹配，则删除该段标签的所有内容
				url = matcher.group(1);
				if (!url.contains("http://")){
					html = html.replace(url, "http://" + host + url);
				}
				
			}
			return html; 
	    }
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	将html Source文件中的相对路径转化为绝对路径
	 * Method:		getAbsSource
	 * Author:		1.0 created by lijiaxuan at 2015年12月23日 上午9:48:04
	 *--------------------------------------------------------------------------------------*/
	public static String getAbsSource(String htmlText, String host) {
		Pattern ATTR_PATTERN = Pattern.compile("<img[^<>]*?\\ssrc=['\"]?(.*?)['\"]?\\s.*?>",Pattern.CASE_INSENSITIVE);
		//Pattern ATTR_PATTERN = Pattern.compile("<img[^<>]*?\\ssrc=['\"]?((?!(www\\.|https?://))\\w)['\"]?\\s.*?>",Pattern.CASE_INSENSITIVE);
		
		Matcher matcher = ATTR_PATTERN.matcher(htmlText);
	    List<String> list = new ArrayList<String>(); // 装载了匹配整个的Tag
	    List<String> list2 = new ArrayList<String>(); // 装载了src属性的内容
	    while (matcher.find()) {
	        list.add(matcher.group(0));
	        list2.add(matcher.group(1));
	    }
	    StringBuilder sb = new StringBuilder();
	    sb.append(htmlText.split("<img")[0]); // 连接<img之前的内容
	    for (int i = 0; i < list.size(); i++) { // 遍历list
	    	
	        sb.append(list.get(i).replace(list2.get(i), // 对每一个Tag进行替换
	        		"http://" + host +"/"+ list2.get(i).substring(1)));
	    }
	   // sb.append(htmlText.split("(?:<img[^<>]*?\\ssrc=['\"]?((?!(www\\.|https?://))\\w)['\"]?\\s.*?>)+")[1]);
	    sb.append(htmlText.split("(?:<img[^<>]*?\\s.*?['\"]?\\s.*?>)+")[1]);
	    return sb.toString();
	}
}