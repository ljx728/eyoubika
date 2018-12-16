package com.eyoubika.util;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class WebFetchUtil{

	//static String url="http://www.cnblogs.com/zyw-205520/archive/2012/12/20/2826402.html";

    private static Document getDocument(String url){
    	 Document doc = null;
    	 try {
            doc = Jsoup.connect(url).get();
             
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
    	 return doc;
    }
    
    private static String getBody(Document doc) throws IOException {
        return doc.body().toString();
    }
    
    private static Map<String, String> getAllLinks(Document doc){
    	Map<String, String> map = new HashMap<String, String>();
         Elements ListDiv = doc.getElementsByAttributeValue("class","postTitle");
         for (Element element :ListDiv) {
             Elements links = element.getElementsByTag("a");
             for (Element link : links) {
                 String linkHref = link.attr("href");
                 String linkText = link.text().trim();
                 map.put(linkText, linkHref);
                 System.out.println(linkHref);
                 System.out.println(linkText);
             }
         }
    	return map;
    }

    
    /**
     * 获取指定博客文章的内容
     */
    public static void Blog() {
        Document doc;
        try {
            doc = Jsoup.connect("http://www.cnblogs.com/zyw-205520/archive/2012/12/20/2826402.html").get();
            Elements ListDiv = doc.getElementsByAttributeValue("class","postBody");
            for (Element element :ListDiv) {
                System.out.println(element.html());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}