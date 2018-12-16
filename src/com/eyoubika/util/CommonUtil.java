package com.eyoubika.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;

import javax.servlet.ServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.Md5Util;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

/*==========================================================================================*
 * Description:	定义了通用工具包
 * Class:		CommonUtil
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-14 15:15:33
 *==========================================================================================*/
public class CommonUtil {
	// eyoubika 网址
	// public static final String HOST= "http://104.236.250.46:8080";
	public static final String HOST = "http://localhost:8080";
	public static final String WEBSITE = HOST + "/eyoubika/";
	// eyoubika 目录
	public static final String WEB_ROOT = System
			.getProperty("eyoubika.webRoot");
	public static final String APP_PATH = CommonUtil.class.getResource("")
			.getPath().toString()
			+ "../../../../../";
	public static final String APPCLASS_PATH = CommonUtil.class.getResource("")
			.getPath().toString()
			+ "../../";
	public static final String APPSRC_PATH = APP_PATH + "src/";

	public static final String WEB_INVEST_PATH = "web/info/investment/";
	// public static final String APP_DIR =
	// "/Users/lijiaxuan/ljx728/workspace/eyoubika/";
	// public static final String APP_DIR =
	// "/Users/lijiaxuan/ljx728/eyoubika/";
	// public static final String APP_DIR =
	// "/Users/lijiaxuan/ljx728/workspace/eyoubika/";
	// eyoubika 模型文件
	// public static final String PROJECT_DIR =
	// "/Users/lijiaxuan/ljx728/workspace/eyoubika/";
	public static final String PROJECT_DIR = "/Users/lijiaxuan/eyoubika/eybk_server/eyoubika/";
	public static final String PROJECTSRC_DIR = PROJECT_DIR + "src/";
	public static final String MODEL_FILE = "Model.txt";
	public static final String MODEL_CONFIG_DIR = PROJECT_DIR
			+ "src/com/eyoubika/model/resource/config/";
	public static final String MODEL_RES_DIR = PROJECT_DIR
			+ "src/com/eyoubika/model/resource/";
	// public static final String APP_DIR = "/home/eyoubika/";
	// public static final String CONF_DIR =
	// "/var/lib/tomcat7/webapps/eyoubika/WEB-INF/classes/conf/";
	// public static final String CONF_DIR = APP_DIR + "src/conf/";
	// public static final String LOG_DIR = APP_DIR + "log/";
	// public static final String LOG_DIR =
	// "/Users/lijiaxuan/ljx728/resource/log/";
	// eyoubika 资源目录
	public static final String RESOURCE_DIR = WEB_ROOT + "resource/";
	public static final String WEB_DIR = WEB_ROOT + "web/";
	public static final String TRANS_IMG_DIR = RESOURCE_DIR + "trans/img/";
	public static final String TRANS_REPORT_DIR = RESOURCE_DIR
			+ "trans/report/"; // "/Users/lijiaxuan/ljx728/"; //
	// public static final String resourceDir =
	// "/Users/lijiaxuan/ljx728/resource/";
	public static final String SPIDER_IMG_DIR = RESOURCE_DIR + "spider/img/";
	public static final String SPIDER_HTML_DIR = RESOURCE_DIR + "spider/html/";
	public static final String SYSTEM_LOG_DIR = RESOURCE_DIR + "system/log/";
	public static final String SPIDER_LOG_DIR = RESOURCE_DIR + "spider/log/";

	public static final String KCHART_DATA_DIR = RESOURCE_DIR
			+ "system/data/quotations/kchart/";
	public static final String CCHART_DATA_DIR = RESOURCE_DIR
			+ "system/data/quotations/cchart/";
	public static final String INVESTMENT_HTML_DIR = WEB_DIR
			+ "info/investment/";
	public static final String INVESTMENT_SEO_DIR = WEB_DIR + "seo/investment/";
	private static Logger logger = null; // log记录对象
	// eyoubika 日志文件
	public static String LOG_SYSTEM_FILE = "eyoubika_log";
	public static String LOG_SPIDER_FILE = "spider";

	// public static final String IMG_DIR = APP_PATH + "resource/img/";
	// public static final String resourceDir =
	// "/home/lijiaxuan/eyoubka/resource/";
	/*--------------------------------------------------------------------------------------*
	 * Description:	Print infomation
	 * Method:		info
	 * Input:		@param message
	 * Output:		void
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 4:05:42 PM
	 *--------------------------------------------------------------------------------------*/
	public static void info(String message) {
		logger.info(message);
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	Print debug infomation
	 * Method:		debug
	 * Input:		@param message
	 * Output:		void
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 4:17:42 PM
	 *--------------------------------------------------------------------------------------*/
	public static void debug(String message) {
		logger.debug(message);
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	去除字符串中的中文字符
	 * Method:		removeCNChar
	 * Author:		1.0 created by lijiaxuan at 2015年6月1日 下午9:49:10
	 *--------------------------------------------------------------------------------------*/
	public static String removeCNChar(String itemlife) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(itemlife);
		return m.replaceAll("");
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNumFromString
	 * Author:		1.0 created by lijiaxuan at 2015年10月7日 上午11:14:16
	 *--------------------------------------------------------------------------------------*/
	public static String getNumFromString(String itemlife, String sp) {
		if (sp == null) {
			sp = ",";
		}
		String regEx = "[0-9\\.]+";
		String res = "";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(itemlife);
		int i = 0;
		while (m.find()) {
			if (i == 0) {
				res += m.group();
			} else {
				res += sp + m.group();
			}
			i++;
		}
		return res;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getDateFromString
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 上午11:33:27
	 *--------------------------------------------------------------------------------------*/
	public static String extractDateFromString(String itemlife, String sp) {
		if (sp == null) {
			sp = ",";
		}
		String regEx = "[-0-9]+";
		String res = "";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(itemlife);
		int i = 0;
		if (m.find()) {
			res = m.group();
		}
		return getFormatDate(res, null);
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	Print error infomation
	 * Method:		error
	 * Input:		message
	 * Output:		void
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 4:18:43 PM
	 *--------------------------------------------------------------------------------------*/
	public static void error(String message) {
		logger.debug(message);
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	Get the date one day earlier than the specified date.
	 * Method:		getPreDate
	 * Input:		date
	 * Output:		Date
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 4:21:50 PM
	 *--------------------------------------------------------------------------------------*/
	public static Date getPreDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNPreDate
	 * Author:		1.0 created by lijiaxuan at 2015年10月23日 下午3:18:01
	 *--------------------------------------------------------------------------------------*/
	public static Date getNPreDate(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 0 - n);
		return calendar.getTime();
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	Get the date one day later than the specified date.
	 * Method:		getLatDate
	 * Input:		date
	 * Output:		Date
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 4:30:51 PM
	 *--------------------------------------------------------------------------------------*/
	public static Date getLatDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	Calculate the date from year, month, day.
	 * Method:		getDate
	 * Input:		@param year
	 * Input:		@param month
	 * Input:		@param day
	 * Output:		Date
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 下午4:33:12
	 *--------------------------------------------------------------------------------------*/
	public static Date getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month, day);
		return calendar.getTime();
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getWeekInYearOfDate
	 * Author:		1.0 created by lijiaxuan at 2015年11月24日 上午11:42:27
	 *--------------------------------------------------------------------------------------*/
	public static int getWeekInYearOfDate(String date) {

		Calendar cal = Calendar.getInstance();

		cal.set(Integer.parseInt(date.substring(0, 4)),
				Integer.parseInt(date.substring(4, 6)),
				Integer.parseInt(date.substring(6, 8)));
		cal.setFirstDayOfWeek(Calendar.THURSDAY);
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		// cal.set
		int res = cal.get(Calendar.WEEK_OF_YEAR);
		if (res < 5) {
			res = 48 + res;
		} else {
			res -= 4;
		}
		return res;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getMonthInYearOfDate
	 * Author:		1.0 created by lijiaxuan at 2015年11月24日 上午11:43:42
	 *--------------------------------------------------------------------------------------*/
	public static int getMonthInYearOfDate(String date) {
		return Integer.parseInt(date.substring(4, 6));
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	Get current time (take minutes and seconds)
	 * Method:		getCurrentTime
	 * Input:		@return
	 * Output:		Timestamp
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 下午4:36:07
	 *--------------------------------------------------------------------------------------*/
	public static Timestamp getCurrentTime() {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		return timestamp;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getTimestamp
	 * Author:		1.0 created by lijiaxuan at 2015年7月27日 下午4:24:46
	 *--------------------------------------------------------------------------------------*/
	public static String getTimestamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sdf.format(date);
		return str;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		isInTime
	 * Author:		1.0 created by lijiaxuan at 2015年6月23日 下午6:42:07
	 *--------------------------------------------------------------------------------------*/
	public static boolean isInTime(String strDateBegin, String strDateEnd,
			Date date) {
		if (date == null) {
			date = Calendar.getInstance().getTime();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		// 截取当前时间时分秒
		int strDateH = Integer.parseInt(strDate.substring(11, 13));
		int strDateM = Integer.parseInt(strDate.substring(14, 16));
		int strDateS = Integer.parseInt(strDate.substring(17, 19));
		// 截取开始时间时分秒
		int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
		int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
		int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
		// 截取结束时间时分秒
		int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
		int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
		int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
		if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
			// 当前时间小时数在开始时间和结束时间小时数之间
			if (strDateH > strDateBeginH && strDateH < strDateEndH) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
					&& strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM == strDateBeginM
					&& strDateS >= strDateBeginS && strDateS <= strDateEndS) {
				return true;
			}
			// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
			else if (strDateH >= strDateBeginH && strDateH == strDateEndH
					&& strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
			} else if (strDateH >= strDateBeginH && strDateH == strDateEndH
					&& strDateM == strDateEndM && strDateS <= strDateEndS) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getDateFromString
	 * Input:		@param s
	 * Input:		@return
	 * Output:		Date
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 下午5:00:21
	 *--------------------------------------------------------------------------------------*/
	public static Date getDateFromString(String s) {
		if ((null != s) && (!"".equals(s))) {
			if (s.length() == 10 && s.charAt(4) == '/' && s.charAt(7) == '/') {
				return getDateFromString(s, "yyyy/MM/dd");
			} else if (s.length() == 8) {
				return getDateFromString(s, "yyyyMMdd");
			} else {
				return getDateFromString(s, "yyyy-MM-dd");
			}
		} else {
			return null;
		}
	}/**/

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getDateFromTimstamp
	 * Author:		1.0 created by lijiaxuan at 2015年11月18日 上午10:18:31
	 *--------------------------------------------------------------------------------------*/
	public static String getTimeFromTimstamp(String timestamp, String format) {
		if (timestamp == null) {
			timestamp = Long.toString(System.currentTimeMillis());
		}
		if (format == null || format.isEmpty())
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(timestamp)));
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getDateFromString
	 * Input:		@param s
	 * Input:		@param myFormat
	 * Input:		@return
	 * Output:		Date
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 下午5:00:23
	 *--------------------------------------------------------------------------------------*/
	public static Date getDateFromString(String s, String myFormat) {
		if ((null != s) && (!"".equals(s))) {
			SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
			Date date = new Date();
			try {
				date = sdf.parse(s);
			} catch (ParseException e) {
				throw new YbkException(YbkException.CODE000015,
						YbkException.DESC000015);
			}
			return date;
		} else {
			return null;
		}

	}/**/

	/*--------------------------------------------------------------------------------------*
	 * Description:	Calculate the span between two date.
	 * Method:		getDiffDays
	 * Input:		@param date1
	 * Input:		@param date2
	 * Input:		@return
	 * Output:		int
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 下午4:59:35
	 *--------------------------------------------------------------------------------------*/
	/*
	 * public static int getDiffDays(Date date1, Date date2) { SimpleDateFormat
	 * sdf = new SimpleDateFormat("yyyy-MM-dd"); //1 //SimpleDateFormat sdf =
	 * new SimpleDateFormat("yyyyMMdd"); //2 //SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyyMMddHHmmsss"); //3 //SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm"); //4 //SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //4 String startDate =
	 * sdf.format(date1); String endDate = sdf.format(date2); date1 =
	 * getDateFromString(startDate); date2 = getDateFromString(endDate); long ms
	 * = Math.abs(date2.getTime() - date1.getTime()); long day = ms / 1000 / 60
	 * / 60/ 24; return (int) day; }
	 */

	/*--------------------------------------------------------------------------------------*
	 * Description:	Get String from Double with permillage or not. precision: 2
	 * Method:		getFormatDouble
	 * Input:		@param doubleValue
	 * Input:		@return
	 * Output:		String
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 下午5:07:14
	 *--------------------------------------------------------------------------------------*/
	public static String getFormatDouble(Double doubleValue, int precison,
			boolean permillageFlag) {
		if (doubleValue == null) {
			return null;
		} else {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(permillageFlag); // with permillage or not
			nf.setMaximumFractionDigits(precison);
			nf.setMinimumFractionDigits(precison);
			return nf.format(doubleValue);
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	Get String from Double with permillage or not. precision: 2
	 * Method:		getFormatDouble
	 * Input:		@param doubleValue
	 * Input:		@return
	 * Output:		String
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 下午5:07:14
	 *--------------------------------------------------------------------------------------*/
	public static String getFormatDouble(Double doubleValue,
			boolean permillageFlag) {
		if (doubleValue == null) {
			return null;
		} else {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(permillageFlag); // with permillage or not
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			return nf.format(doubleValue);
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	Get String from Double with permillage or not. precision: 2
	 * Method:		getFormatDouble
	 * Input:		@param doubleValue
	 * Input:		@return
	 * Output:		String
	 * Author:		ljx728
	 * History:		1.0 created by ljx728 at 2015-5-14 下午5:07:14
	 *--------------------------------------------------------------------------------------*/
	public static String getFormatDouble(Double doubleValue, int precison) {
		if (doubleValue == null) {
			return null;
		} else {
			if (precison == 0) {
				precison = 12;
			}
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false); // with out permillage
			nf.setMaximumFractionDigits(precison);
			return nf.format(doubleValue);
		}
	}

	public static String getFormatDouble(Double doubleValue) {
		if (doubleValue == null) {
			return null;
		} else {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false); // with out permillage
			nf.setMaximumFractionDigits(2);
			return nf.format(doubleValue);
		}
	}

	public static String getFormatDate(Date date) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			return sf.format(date);
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getFormatDate
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午4:15:19
	 *--------------------------------------------------------------------------------------*/
	public static String getFormatDate(String date, String sp) {
		String dateStr = "";
		if (CommonUtil.isStringNull(sp)) {
			sp = "";
		}
		if (date.contains("-") || date.contains("/") || date.contains("年")
				|| date.contains(" ")) {
			String num[] = getNumFromString(date, null).split(",");
			for (int i = 0; i < num.length; i++) {
				if (i == 0) { // 年
					if (num[i].length() == 2) {
						num[i] = "20" + num[i];
					}
				} else { // 月、日
					if (num[i].length() == 1) {
						num[i] = sp + "0" + num[i];
					} else {
						num[i] = sp + num[i];
					}
				}
				dateStr += num[i];
			}
		} else if (CommonUtil.isStringDigit(date)) {

			dateStr = date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
					+ date.substring(6, 8);
		}
		return dateStr;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		isLastDayOfMonth
	 * Author:		1.0 created by lijiaxuan at 2015年11月25日 下午5:42:19
	 *--------------------------------------------------------------------------------------*/
	public static boolean isLastDayOfMonth(String dateStr) {
		Date date = getDateFromString(dateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getWeekdayFromDate
	 * Author:		1.0 created by lijiaxuan at 2015年11月24日 上午11:53:47
	 *--------------------------------------------------------------------------------------*/
	public static String getWeekdayFromDate(String date) {
		Date d = getDateFromString(date);
		SimpleDateFormat sf = new SimpleDateFormat("EEEE");
		return sf.format(d);
	}

	public static String getNowWeekday() {
		SimpleDateFormat sf = new SimpleDateFormat("EEEE");
		return sf.format(new Date());
	}

	public static String getNowYear() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy");
		return sf.format(new Date());
	}

	public static String getNowDate() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(new Date());
	}

	public static String getYesterDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(cal.getTime());
	}

	public static String getNowDate(String spilt) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy" + spilt + "MM"
				+ spilt + "dd");
		return sf.format(new Date());
	}

	public static String getNowTime() {
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		return sf.format(new Date());
	}

	public static String getNow() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(new Date());
	}

	public static String getNowTimeStamp() {
		return Long.toString(System.currentTimeMillis());
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	判断当前时间是否是整点（分钟，小时，天，周，月）
	 * Method:		isIntegralPoint
	 * Author:		1.0 created by lijiaxuan at 2015年10月27日 下午3:50:13
	 *--------------------------------------------------------------------------------------*/
	public static boolean isIntegralPoint(String type) {
		boolean res = false;
		String time = "";
		if (type.equals(CommonVariables.TIME_INTERVAL_MIN)) {
			SimpleDateFormat sf = new SimpleDateFormat("ss");
			time = sf.format(new Date());
			if (time.equals("00")) {
				res = true;
			}
		} else {

		}
		return res;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getHost
	 * Author:		1.0 created by lijiaxuan at 2015年9月30日 下午4:25:29
	 *--------------------------------------------------------------------------------------*/
	public static String getHost(String url) {
		if (url == null || url.trim().equals("")) {
			return "";
		}
		String host = "";
		Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
		Matcher matcher = p.matcher(url);
		if (matcher.find()) {
			host = matcher.group();
		}
		return host;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		isBeforeTime
	 * Author:		1.0 created by lijiaxuan at 2015年9月17日 下午3:28:26
	 *--------------------------------------------------------------------------------------*/
	public static boolean isBeforeTime(String time) {
		String nowTime = getNowTime();
		int nowH = Integer.parseInt(nowTime.substring(0, 2));
		int nowM = Integer.parseInt(nowTime.substring(3, 5));
		int nowS = Integer.parseInt(nowTime.substring(6, 8));

		int timeH = Integer.parseInt(time.substring(0, 2));
		int timeM = Integer.parseInt(time.substring(3, 5));
		int timeS = Integer.parseInt(time.substring(6, 8));

		if (nowH < timeH) {
			return true;
		} else if (nowH == timeH) {
			if (nowM < timeM) {
				return true;
			} else if (nowM == timeM) {
				if (nowS < timeS) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		isAfterTime
	 * Author:		1.0 created by lijiaxuan at 2015年9月17日 下午3:28:17
	 *--------------------------------------------------------------------------------------*/
	public static boolean isAfterTime(String time) {
		String nowTime = getNowTime();
		int nowH = Integer.parseInt(nowTime.substring(0, 2));
		int nowM = Integer.parseInt(nowTime.substring(3, 5));
		int nowS = Integer.parseInt(nowTime.substring(6, 8));

		int timeH = Integer.parseInt(time.substring(0, 2));
		int timeM = Integer.parseInt(time.substring(3, 5));
		int timeS = Integer.parseInt(time.substring(6, 8));

		if (nowH > timeH) {
			return true;
		} else if (nowH == timeH) {
			if (nowM > timeM) {
				return true;
			} else if (nowM == timeM) {
				if (nowS > timeS) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	判断是否在两个时刻之间。（已经修改）
	 * Method:		isInTime
	 * Author:		1.0 created by lijiaxuan at 2015年9月17日 上午10:29:49
	 *--------------------------------------------------------------------------------------*/
	public static boolean isInTime(String startTime, String endTime) {
		String nowTime = getNowTime();
		int nowH = Integer.parseInt(nowTime.substring(0, 2));
		int nowM = Integer.parseInt(nowTime.substring(3, 5));
		int nowS = Integer.parseInt(nowTime.substring(6, 8));

		int startH = Integer.parseInt(startTime.substring(0, 2));
		int startM = Integer.parseInt(startTime.substring(3, 5));
		int startS = Integer.parseInt(startTime.substring(6, 8));

		int endH = Integer.parseInt(endTime.substring(0, 2));
		int endM = Integer.parseInt(endTime.substring(3, 5));
		int endS = Integer.parseInt(endTime.substring(6, 8));

		if (nowH >= startH && nowH <= endH) {
			if (nowH > startH && nowH < endH) { // 时间之间
				return true;
			} else if (nowH == startH && nowM > startM && nowH < endH) { // 现时=初始时，现分>初始分，现时<结束时
				return true;
			} else if (nowH == startH && nowM == startM && nowS > startS
					&& nowH < endH) {
				return true;
			} else if (nowH == startH && nowM == startM && nowS == startS
					&& nowH < endH) {
				return true;
			} else if (nowH > startH && nowH == endH && nowM < endM) {
				return true;
			} else if (nowH > startH && nowH == endH && nowM == endM
					&& nowS < endS) {
				return true;
			} else if (nowH > startH && nowH == endH && nowM == endM
					&& nowS == endS) {
				return true;
			} else if (nowH == startH && nowH == endH && nowM > startM
					&& nowM < endM) { // 小时内比较
				return true;
			} else if (nowH == startH && nowH == endH && nowM == startM
					&& nowS >= startS && nowM < endM) { // 小时内比较
				return true;
			} else if (nowH == startH && nowH == endH && nowM == startM
					&& nowM == endM && nowS >= startS && nowS <= endS) { // 分钟内比较
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isStringNull(String verifyString) {
		return (null == verifyString || "".equals(verifyString));
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		isStringZero
	 * Author:		1.0 created by lijiaxuan at 2015年9月28日 下午11:39:47
	 *--------------------------------------------------------------------------------------*/
	public static boolean isStringZero(String verifyString) {
		if (null == verifyString || "".equals(verifyString)) {
			return false;
		}

		java.util.regex.Pattern p = java.util.regex.Pattern
				.compile("^(0+.?0+|0+)$");
		java.util.regex.Matcher m = p.matcher(verifyString);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}

	}

	
	public static boolean isStringDigit(String verifyString) { // 数字，小数点，负号在字首,以及科学计数法
		if (null == verifyString || "".equals(verifyString)) {
			return false;
		} else {
			java.util.regex.Pattern p = java.util.regex.Pattern
					.compile("^(-?\\d+\\.?\\d+|-?\\d|-?\\d+\\.?\\d+E\\d+)$");
			java.util.regex.Matcher m = p.matcher(verifyString);
			if (m.matches()) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static String buildJson(Object object) {
		String result = null;
		JSONObject jsonObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		if (object instanceof List) {
			map.put("data", object);
			jsonObject = JSONObject.fromObject(map);
			result = jsonObject.toString();
			((List) object).clear();
		} else {
			List<Object> list = new ArrayList<Object>();
			if (object != null) {
				list.add(object);
			}
			map.put("data", list);
			jsonObject = JSONObject.fromObject(map);
			result = jsonObject.toString();
			list.clear();
			list = null;
		}
		jsonObject = null;
		object = null;
		map.clear();
		map = null;
		return result;
	}

	public static String toFile(String content, String fileName, String fileDir) {
		if (fileDir == null) {
		}
		fileName = fileDir + fileName;
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName), "UTF-8"));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE970001,
					YbkException.DESC970001);
		}
		return fileName;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		appendFile
	 * Author:		1.0 created by lijiaxuan at 2015年11月24日 上午11:03:25
	 *--------------------------------------------------------------------------------------*/
	public static String appendFile(String content, String fileName,
			String fileDir) {
		if (fileDir == null) {
		}
		fileName = fileDir + fileName;
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName, true), "UTF-8"));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE970001,
					YbkException.DESC970001);
		}
		return fileName;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	删除单个文件
	 * Method:		deleteFile
	 * Author:		1.0 created by lijiaxuan at 2016年1月13日 下午3:45:50
	 *--------------------------------------------------------------------------------------*/
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	删除目录及目录下的文件
	 * Method:		deleteDirectory
	 * Author:		1.0 created by lijiaxuan at 2016年1月13日 下午3:51:46
	 *--------------------------------------------------------------------------------------*/
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i]
						.getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		fileExist
	 * Author:		1.0 created by lijiaxuan at 2015年7月11日 上午9:43:56
	 *--------------------------------------------------------------------------------------*/
	public static boolean isFileExists(String fileName, String fileDir) {
		if (new File(fileDir + fileName).exists()) {
			return true;
		} else {
			return false;
		}
	}

	public static String fromFile(String fileName, String fileDir) {
		if (fileDir == null) {
		}
		String fileContent = "";
		fileName = fileDir + fileName;
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				fileContent += tempString + "\n";
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return fileContent;
	}

	 
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getFiles
	 * Author:		1.0 created by lijiaxuan at 2016年1月19日 下午2:28:43
	 *--------------------------------------------------------------------------------------*/
	public static void getFiles(String filePath,  ArrayList<String> filelist){
		  File root = new File(filePath);
		  
		  File[] files = root.listFiles();
		  for(File file:files){     
			  if(file.isDirectory()){
				  getFiles(file.getAbsolutePath(), filelist);
				 // filelist.add(file.getAbsolutePath());
				 // System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
			  }else{
				 filelist.add(file.getAbsolutePath());
		    	 //System.out.println("显示"+filePath+"下所有子目录"+file.getAbsolutePath());
			  }     
		  }
		}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		renameFile
	 * Author:		1.0 created by lijiaxuan at 2015年7月17日 上午11:57:56
	 *--------------------------------------------------------------------------------------*/
	public static void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (!oldfile.exists()) {
				return;// 重命名文件不存在
			}
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				throw new YbkException(YbkException.CODE970010,
						YbkException.DESC970010);
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			throw new YbkException(YbkException.CODE970012,
					YbkException.DESC970012);
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		toLog
	 * Author:		1.0 created by lijiaxuan at 2015年6月4日 下午1:45:36
	 *--------------------------------------------------------------------------------------*/
	public static String toLog(String content) {

		String fileName = SYSTEM_LOG_DIR + LOG_SYSTEM_FILE + "_" + getNowDate()
				+ ".log";
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName, true), "UTF-8"));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE970001,
					YbkException.DESC970001);
		}
		return fileName;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		toLog
	 * Author:		1.0 created by lijiaxuan at 2015年7月30日 上午9:49:46
	 *--------------------------------------------------------------------------------------*/
	public static String toLog(String content, String fileType) {
		String fileName = "";
		if (fileType.equals(CommonVariables.LOG_TYPE_SPIDER)) {
			fileName = SPIDER_LOG_DIR + LOG_SPIDER_FILE + "_" + getNowDate()
					+ ".log";
		}

		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName, true), "UTF-8"));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE970001,
					YbkException.DESC970001);
		}
		return fileName;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getConfig
	 * Author:		1.0 created by lijiaxuan at 2015年6月23日 下午3:48:32
	 *--------------------------------------------------------------------------------------*/
	public static Properties getConfig(String configFile) {
		String fileName = "conf/" + configFile;
		Properties p = new Properties();
		InputStream inputStream = Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(fileName);
		if (inputStream == null) {
			throw new YbkException(YbkException.CODE970002,
					YbkException.DESC970002);
		} else {
			try {
				p.load(inputStream);
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new YbkException(YbkException.CODE970003,
						YbkException.DESC970003);
			}
		}
		return p;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildTransNo
	 * Author:		1.0 created by lijiaxuan at 2015年7月17日 下午7:29:05
	 *--------------------------------------------------------------------------------------*/
	public static String buildTransNo(String trans) {
		String res = "";
		String maxNo = RedisUtil.getMaxTransNo(trans);
		if (maxNo.length() == 1) {
			maxNo = "000" + maxNo;
		} else if (maxNo.length() == 2) {
			maxNo = "00" + maxNo;
		} else if (maxNo.length() == 3) {
			maxNo = "0" + maxNo;
		} else if (maxNo.length() == 4) {
		}
		res = CommonUtil.getNowDate() + trans + maxNo;
		return res;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getFileName
	 * Author:		1.0 created by lijiaxuan at 2015年7月17日 下午7:32:45
	 *--------------------------------------------------------------------------------------*/
	public static List<String> getFileNames(String path) {

		File file = new File(path);
		List<String> fileList = new ArrayList<String>();
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				fileList.add(tempList[i].getName());
			}
		}

		return fileList;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		upperFirst
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:21
	 *--------------------------------------------------------------------------------------*/
	public static String upperFirst(String name) {
		char[] cs = name.toCharArray();
		if (cs[0] >= 'a' && cs[0] <= 'z') {
			cs[0] -= 32;
		}
		return String.valueOf(cs);
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		lowerFirst
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 上午10:54:07
	 *--------------------------------------------------------------------------------------*/
	public static String lowerFirst(String name) {
		char[] cs = name.toCharArray();
		if (cs[0] >= 'A' && cs[0] <= 'Z') {
			cs[0] += 32;
		}
		return String.valueOf(cs);
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		upperAllAbbr
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午3:14:27
	 *--------------------------------------------------------------------------------------*/
	public static String buildAbbr(String name) {
		String res = "";
		char[] cs = name.toCharArray();
		int size = cs.length;
		for (int i = 0; i < size; i++) {
			if (cs[i] >= 'A' && cs[i] <= 'Z') {
				res += cs[i];
			}
		}
		return res;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		checkPnumber
	 * Author:		1.0 created by lijiaxuan at 2015年10月13日 下午2:23:48
	 *--------------------------------------------------------------------------------------*/
	public static boolean checkPnumber(String pnumber) {
		if (null == pnumber || "".equals(pnumber)) {
			return false;
		}
		String reg = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(reg);
		java.util.regex.Matcher m = p.matcher(pnumber);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		checkIdentifyNo
	 * Author:		1.0 created by lijiaxuan at 2015年10月13日 下午3:25:36
	 *--------------------------------------------------------------------------------------*/
	public static boolean checkIdentifyNo(String identifyNo) {
		if (null == identifyNo || "".equals(identifyNo)) {
			return false;
		}
		String reg = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(reg);
		java.util.regex.Matcher m = p.matcher(identifyNo);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	获取pdf内容
	 * Method:		getPdfFileText
	 * Author:		1.0 created by lijiaxuan at 2015年11月11日 下午3:01:59
	 *--------------------------------------------------------------------------------------*/
	public static String getPdfFileText(String fileName) throws IOException {
		PdfReader reader = new PdfReader(fileName);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		StringBuffer buff = new StringBuffer();
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i,
					new SimpleTextExtractionStrategy());
			buff.append(strategy.getResultantText());
		}
		return buff.toString();
	}

}