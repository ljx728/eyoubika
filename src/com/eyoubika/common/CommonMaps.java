package com.eyoubika.common;
import java.util.HashMap;
import java.util.Map;

/*==========================================================================================*
 * Description:	通用map
 * Class:		CommonMaps
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年6月18日 下午5:03:28
 *==========================================================================================*/
public class CommonMaps{
	public static Map<String, String> exchangeMap = new HashMap<String, String>();
	public static Map<String, String> tradeDayMap = new HashMap<String, String>();
	static {
		exchangeMap.put(CommonVariables.EXCHANGE_NANJING, "南京交易所");
		exchangeMap.put(CommonVariables.EXCHANGE_NANFANG, "南方交易所");
		exchangeMap.put(CommonVariables.EXCHANGE_ZHONGNAN, "中南文交所");
		exchangeMap.put(CommonVariables.EXCHANGE_FULITE, "福丽特交易所");
		exchangeMap.put(CommonVariables.EXCHANGE_JINMAJIA, "金马甲交易所");
		exchangeMap.put(CommonVariables.EXCHANGE_JIANGSU, "江苏文交所");
		exchangeMap.put(CommonVariables.EXCHANGE_SHANGHAI, "上海交易所");
		exchangeMap.put(CommonVariables.EXCHANGE_HUAXIA, "华夏文交所");
		exchangeMap.put(CommonVariables.EXCHANGE_HUAZHONG, "华中交易所");
		exchangeMap.put(CommonVariables.EXCHANGE_YIJIAO, "艺交所");
		
		exchangeMap.put("南京交易所", CommonVariables.EXCHANGE_NANJING);
		exchangeMap.put("南方交易所", CommonVariables.EXCHANGE_NANFANG);
		exchangeMap.put("中南文交所", CommonVariables.EXCHANGE_ZHONGNAN);
		exchangeMap.put("福丽特交易所", CommonVariables.EXCHANGE_FULITE);
		exchangeMap.put("金马甲交易所", CommonVariables.EXCHANGE_JINMAJIA);
		exchangeMap.put("江苏文交所", CommonVariables.EXCHANGE_JIANGSU);
		exchangeMap.put("上海交易所", CommonVariables.EXCHANGE_SHANGHAI);
		exchangeMap.put("华夏文交所", CommonVariables.EXCHANGE_HUAXIA);
		exchangeMap.put("艺交所", CommonVariables.EXCHANGE_YIJIAO);
		
		tradeDayMap.put(CommonVariables.EXCHANGE_NANJING, CommonVariables.COMMON_LEVEL_04); //周六晚收盘
		tradeDayMap.put(CommonVariables.EXCHANGE_NANFANG, CommonVariables.COMMON_LEVEL_04); //周六晚收盘
		tradeDayMap.put(CommonVariables.EXCHANGE_ZHONGNAN, CommonVariables.COMMON_LEVEL_04); //周六晚收盘
		tradeDayMap.put(CommonVariables.EXCHANGE_FULITE, CommonVariables.COMMON_LEVEL_03); //周五晚收盘
		tradeDayMap.put(CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.COMMON_LEVEL_04); //周六晚收盘
		tradeDayMap.put(CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.COMMON_LEVEL_03); //周五晚收盘
		tradeDayMap.put(CommonVariables.EXCHANGE_YIJIAO, CommonVariables.COMMON_LEVEL_04); //周六晚收盘
		
	}
	
	public static String exchangeIds[] = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
		CommonVariables.EXCHANGE_FULITE, CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO};
	
	public static String exchangeIdStr = CommonVariables.EXCHANGE_NANJING + "," +  CommonVariables.EXCHANGE_NANFANG + "," +  CommonVariables.EXCHANGE_ZHONGNAN + "," + 
			CommonVariables.EXCHANGE_FULITE + "," + CommonVariables.EXCHANGE_JINMAJIA + "," +  CommonVariables.EXCHANGE_SHANGHAI + "," +  CommonVariables.EXCHANGE_YIJIAO;
}
