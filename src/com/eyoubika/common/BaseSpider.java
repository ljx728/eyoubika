package com.eyoubika.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.RegexUtil;

/*==========================================================================================*
 * Description:	网络抓取程序
 * Class:		WebSpider
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-28 15:16:13
 *==========================================================================================*/
public class BaseSpider {
	protected String protocal;
	protected String port;
	protected String path;
	protected String host;
	protected String resourceDir;
	protected String imgDir;
	protected String htmlDir;
	protected String imgStartNo = "00000";

	public BaseSpider() {
	}

	public void init(String webName) {
		this.htmlDir = CommonUtil.SPIDER_HTML_DIR + webName + "/";
		this.imgDir = CommonUtil.SPIDER_IMG_DIR;
	}

	public void destroy() {

	}

	public void sleep(int span) {
		int max = 3000;
		int min = 1000;
		if (span == 0) {
			Random random = new Random();
			span = random.nextInt(max) % (max - min + 1) + min;
		}
		try {
			Thread.sleep(span);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		extractArticle
	 * Author:		1.0 created by lijiaxuan at 2015年6月2日 上午9:54:21
	 *--------------------------------------------------------------------------------------*/
	public String fetchTableContent(Element table) {
		Element tbody = table.select("tbody").first();
		Elements trs = tbody.select("tr");
		String tableContent = "";
		String trContent = "";
		for (int i = 0; i < trs.size(); i++) {
			Elements tds = trs.get(i).select("td");
			trContent = "";
			for (int j = 0; j < tds.size(); j++) {
				trContent += "<td>" + tds.get(j).text() + "</td>";
			}
			tableContent += "<tr>" + trContent + "</tr>";
		}
		tableContent = "<table>" + tableContent + "</table>";
		return tableContent;
	}

	public String FetchImageUrl(Element imgLink) {
		String imagesPath = imgLink.attr("src");
		return imagesPath.trim();
		// return "<img>" + imagesPath.trim() + "</img>";
	}

	public Connection buildHeader(Connection con) {
		con.header("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		con.header("Accept-Encoding", "gzip, deflate");
		con.header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		con.header("Connection", "keep-alive");
		con.header("Host", host);
		con.header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");
		return con;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	头信息设置
	 * Method:		buildHeader
	 * Author:		1.0 created by lijiaxuan at 2015年8月6日 下午2:57:19
	 *--------------------------------------------------------------------------------------*/
	public Connection buildHeader(Connection con, String type) {
		if (type.endsWith("phone")) {
			con.header("Accept-Encoding", "gzip");
			con.header("Connection", "keep-alive");
			con.header("Host", host);
			con.header("X-Unity-Version", "4.6.3f1");
			con.header("User-Agent",
					"Dalvik/1.6.0 (Linux; U; Android 4.4.4; M351 Build/KTU84P)");
		} else if (type.endsWith("web")) {
			con.header("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			con.header("Accept-Encoding", "gzip, deflate");
			con.header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			con.header("Connection", "keep-alive");
			con.header("Host", host);
			con.header("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");
		} else {
			con.header("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			con.header("Accept-Encoding", "gzip");
			con.header("Accept-Language", "zh-CN,zh;q=0.8");
			con.header("Cache-Control", "max-age=0");
			con.header("Connection", "keep-alive");
			con.header("Host", "mp.weixin.qq.com");
			con.header(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");
		}

		return con;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取网页源代码：根据type判断是否保存到本地。
	 * Method:		fetchDoucment
	 * Author:		1.0 created by lijiaxuan at 2015年06月12日 下午4:57:44
	 *--------------------------------------------------------------------------------------*/
	public Document fetchDoucment(String url, String type) {
		Document doc = null;
		if (type == null) {
			type = "both"; // web
		}
		if (type == "local") {
			File input = new File(this.htmlDir + url);
			try {
				doc = Jsoup.parse(input, "UTF-8", this.host);
			} catch (IOException e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980001,
						YbkException.DESC980001);
			}
		} else if (type == "web") {
			doc = fetchDocument(url);
		} else {
			File input = new File(this.htmlDir + fetchDocument(url, true, null));
			try {
				doc = Jsoup.parse(input, "UTF-8", this.host);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doc;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	普通的抓取网页源码爬虫
	 * Method:		fetchDocument
	 * Author:		1.0 created by lijiaxuan at 2015年06月10日 下午4:58:35
	 *--------------------------------------------------------------------------------------*/
	public Document fetchDocument(String url) {
		Document doc = null;
		sleep(0); // 动态睡眠不定长时间
		Connection con = Jsoup.connect(url).timeout(5000); // 建立连接
		con = buildHeader(con);
		try {
			doc = con.get();
		} catch (IOException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980001,
					YbkException.DESC980001 + " : " + url);
		}
		return doc;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	补全相对路径。
	 * Method:		buildUrl
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 上午11:29:52
	 *--------------------------------------------------------------------------------------*/
	public String buildUrl(String url) {
		if (CommonUtil.isStringNull(url))
			return "";
		if (!url.contains("http://")) {
			String regex = "^(\\.\\.?/)+"; // 定义HTML标签的正则表达式
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(url);
			while (matcher.find()) { // 如果匹配，替换url首部的../../或者./
				url = url.replace(matcher.group(0), "/");
			}
			url = "http://" + this.host + url;
		}
		return url;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	实时抓取数据
	 * Method:		fetchRealTimedoc
	 * Author:		1.0 created by lijiaxuan at 2015年8月19日 上午10:14:26
	 *--------------------------------------------------------------------------------------*/
	public Document fetchRealTimedoc(String url) {
		Document doc = null;
		Connection con = Jsoup.connect(url).timeout(5000); // 建立连接
		con = buildHeader(con);
		try {
			doc = con.ignoreContentType(true).get();
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980001,
					YbkException.DESC980001 + ": " + url);
		}
		return doc;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	根据type判断模拟手机抓取还是PC端抓取。
	 * Method:		fetchDocument
	 * Author:		1.0 created by lijiaxuan at 2015年8月6日 下午2:53:29
	 *--------------------------------------------------------------------------------------*/
	public Document fetchDocument(String url, String type) {
		Document doc = null;
		sleep(0);
		Connection con = Jsoup.connect(url).timeout(5000); // 建立连接
		con = buildHeader(con, type);
		try {
			doc = con.get();
		} catch (IOException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980001,
					YbkException.DESC980001);
		}
		return doc;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取网页并保存成指定文件类型。
	 * Method:		fetchDocument
	 * Author:		1.0 created by lijiaxuan at 2015年6月18日 下午5:00:44
	 *--------------------------------------------------------------------------------------*/
	public String fetchDocument(String url, boolean flag, String fileType) {
		String fileName = "";
		if (fileType == null) {
			fileType = ".html";
		}
		if (flag == true) {
			Document doc = null;
			sleep(0);
			Connection con = Jsoup.connect(url).timeout(5000); // 建立连接
			con = buildHeader(con);
			try {
				doc = con.get();
			} catch (IOException e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980001,
						YbkException.DESC980001);
			}
			fileName = doc.title() + fileType;
			CommonUtil.toFile(doc.toString().replace("&nbsp;", ""), fileName,
					htmlDir);
		} else {
			fileName = url + fileType;
		}
		return fileName;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	获取文件夹下的编号最大的文件名，并自动加一
	 * Method:		getMaxNo
	 * Author:		1.0 created by lijiaxuan at 2015年7月30日 上午9:25:01
	 *--------------------------------------------------------------------------------------*/
	public String getMaxNo(String path) {
		int maxNo = 0;// this.imgStartNo;
		File file = new File(path);
		List fileList = new ArrayList();
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()
					&& RegexUtil.isDigitString(tempList[i].getName())) {
				fileList.add(tempList[i].getName());
				if (maxNo < Integer.parseInt(tempList[i].getName())) {
					maxNo = Integer.parseInt(tempList[i].getName());
				}
			}
		}

		return "" + (maxNo + 1);
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	下载网页中的图片
	 * Method:		downloadImage
	 * Author:		1.0 created by lijiaxuan at 2015年7月18日 下午5:02:25
	 *--------------------------------------------------------------------------------------*/
	public boolean downloadImage(String urlPath, String fileName) {
		boolean flag = false;
		URL url = null;
		try {
			url = new URL(urlPath);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ：获取的路径
			// :http协议连接对象
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.setReadTimeout(6 * 10000);
		try {
			if (conn.getResponseCode() < 10000) {
				InputStream inputStream = conn.getInputStream();
				byte[] data = readStream(inputStream);
				if (data.length > (1024 * 10)) {
					FileOutputStream outputStream = new FileOutputStream(
							this.imgDir + fileName);
					outputStream.write(data);
					flag = true;
					outputStream.close();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	读取url中数据，并以字节的形式返回
	 * Method:		readStream
	 * Author:		1.0 created by lijiaxuan at 2015年7月18日 下午5:02:51
	 *--------------------------------------------------------------------------------------*/
	public byte[] readStream(InputStream inputStream) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		inputStream.close();
		return outputStream.toByteArray();
	}
}