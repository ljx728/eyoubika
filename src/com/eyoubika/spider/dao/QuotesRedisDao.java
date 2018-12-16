package com.eyoubika.spider.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.eyoubika.common.CommonMaps;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.RedisDao;
import com.eyoubika.info.domain.QuotationsDomain;
import com.eyoubika.sbc.dao.SbcBasicDao;
import com.eyoubika.sbc.dao.SbcPriceDao;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.util.RedisUtil;
public class QuotesRedisDao extends RedisDao{
	private List<QuotationsDomain> quotationsDomainList = new  ArrayList<QuotationsDomain>();
	private SbcPriceDao sbcPriceDao;
	private SbcBasicDao sbcBasicDao;
/*	private SbcMapRedisDao sbcMapRedisDao;
	
	public SbcMapRedisDao getSbcMapRedisDao() {
		return sbcMapRedisDao;
	}

	public void setSbcMapRedisDao(SbcMapRedisDao sbcMapRedisDao) {
		this.sbcMapRedisDao = sbcMapRedisDao;
	}*/

	public List<QuotationsDomain> getQuotationsDomainList() {
		return quotationsDomainList;
	}

	public void setQuotationsDomainList(List<QuotationsDomain> quotationsDomainList) {
		this.quotationsDomainList = quotationsDomainList;
	}

	public SbcPriceDao getSbcPriceDao() {
		return sbcPriceDao;
	}

	public void setSbcPriceDao(SbcPriceDao sbcPriceDao) {
		this.sbcPriceDao = sbcPriceDao;
	}

	public SbcBasicDao getSbcBasicDao() {
		return sbcBasicDao;
	}

	public void setSbcBasicDao(SbcBasicDao sbcBasicDao) {
		this.sbcBasicDao = sbcBasicDao;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getSbcId
	 * Author:		1.0 created by lijiaxuan at 2015年8月19日 上午10:39:30
	 *--------------------------------------------------------------------------------------*/
	public String getSbcId(String exId, String sbcCode){
		String key = CommonVariables.REDIS_KEY_SBCCODEMAP + exId;
		return this.hget(key, sbcCode);
	}
	public String getWeekHighestPrice(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "weekHighestPrice");	
	}
	public String getMonthHighestPrice(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "monthHighestPrice");	
	}
	public String getWeekLowestPrice(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "weekLowestPrice");	
	}
	public String getMonthLowestPrice(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "monthLowestPrice");	
	}
	public String getWeekDealVolume(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "weekDealVolume");	
	}
	public String getMonthDealVolume(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "monthDealVolume");	
	}
	public String getWeekDealValue(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "weekDealValue");	
	}
	public String getMonthDealValue(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "monthDealValue");	
	}

	public String getWeekOpenPrice(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "weeKOpenPrice");	
	}
	public String getMonthOpenPrice(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "monthOpenPrice");	
	}
	public String getDayClosePrice(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "dayClosePrice");	
	}
	public String getWeekClosePrice(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "weekClosePrice");	
	}
	public String getMonthClosePrice(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "monthClosePrice");	
	}
	public String getOpenPrice(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "openPrice");	
	}
	public String getClosePrice(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "closePrice");	
	}
	public String getMarketValue(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "marketValue");	
	}
	public String getSbcVolume(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "sbcVolume");	
	}
	public String getTotalValue(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "totalValue");	
	}
	public String getTotalVolume(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "totalVolume");	
	}
	public String getDealValue(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "dealValue");	
	}
	public String getDealVolume(String sbcId){
		String key = CommonVariables.REDIS_KEY_QUOTSMORE + sbcId;
		return this.hget(key, "dealVolume");	
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取行情
	 * Method:		addQuoteDomain
	 * Author:		1.0 created by lijiaxuan at 2015年8月18日 下午4:38:37
	 *--------------------------------------------------------------------------------------*/
	public void addQuoteDomain(QuotationsDomain quotationsDomain){
		Map<String,String> quote = new HashMap<String, String>();
		//Map<String,String> sbcExIdMap = new HashMap<String, String>();
		//Map<String,String> sbcCodeMap = new HashMap<String, String>();
		quote.put(quotationsDomain.getSbcId().toString(), ConverterUtil.objectToJson(quotationsDomain).toString());	
		//sbcExIdMap.put(quotationsDomain.getSbcId().toString(), quotationsDomain.getExId());
		//sbcCodeMap.put(quotationsDomain.getSbcCode(), quotationsDomain.getSbcId().toString());
		//添加到交易所行情库
		this.hmset(CommonVariables.REDIS_KEY_QUOTS + quotationsDomain.getExId(), quote);
		//添加到品种ID与交易所ID映射库里
		//this.hmset(CommonVariables.REDIS_KEY_SBCEXIDMAP,  sbcExIdMap);
		//添加到品种Code与sbcId映射库里
		//this.hmset(CommonVariables.REDIS_KEY_SBCCODEMAP + quotationsDomain.getExId(),  sbcCodeMap);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	初始化redis数据库，抓取行情前，初始化
	 * Method:		initQuoteRedis
	 * Author:		1.0 created by lijiaxuan at 2015年8月19日 下午4:03:46
	 *--------------------------------------------------------------------------------------*/
	public void initQuoteRedis(SbcBasicDomain sbcBasicDomain){
		Map<String,String> sbcExIdMap = new HashMap<String, String>();
		Map<String,String> sbcCodeMap = new HashMap<String, String>();	
		//Map<String,String> sbcQuoteMap = new HashMap<String, String>();	
		//String value = "{\"averagePrice\":\"-\",\"currentPrice\":\"-\",\"dealValue\":\"-\",\"dealVolume\":\"-\",\"exId\":\"001000\",\"highestPrice\":\"-\",\"lowestPrice\":\"-\",\"openingPrice\":\"-\",\"recordTime\":\"-\",\"riseScope\":\"-\",\"riseValue\":\"-\",\"sbcCode\":\""+ 
		//		sbcBasicDomain.getSbcCode()+"\",\"sbcId\":"+sbcBasicDomain.getSbcId()+",\"sbcName\":\"" + sbcBasicDomain.getSbcName() +"\",\"yesterClPrice\":\"-\"}";
		sbcExIdMap.put(sbcBasicDomain.getSbcId().toString(), sbcBasicDomain.getExId());
		sbcCodeMap.put(sbcBasicDomain.getSbcCode(), sbcBasicDomain.getSbcId().toString());
		//sbcQuoteMap.put(sbcBasicDomain.getSbcId().toString(), value);
		//添加到品种ID与交易所ID映射库里
		this.hmset(CommonVariables.REDIS_KEY_SBCEXIDMAP,  sbcExIdMap);
		//添加到品种Code与sbcId映射库里
		this.hmset(CommonVariables.REDIS_KEY_SBCCODEMAP + sbcBasicDomain.getExId(),  sbcCodeMap);
		
		//this.hmset(CommonVariables.REDIS_KEY_QUOTS + sbcBasicDomain.getExId(), sbcQuoteMap);
		//sbcQuoteMap = null;
		sbcExIdMap = null;
		sbcCodeMap = null;
	}
	

	/*public List<QuotationsDomain> queryQuoteList(){
		return quotationsDomainList;
	}
	*/
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		querySbcPriceBySbcList
	 * Author:		1.0 created by lijiaxuan at 2015年7月29日 下午3:41:21
	 *--------------------------------------------------------------------------------------*/
	public List<String> querySbcPriceBySbcList(List<Integer> sbcIdList, SbcMapRedisDao sbcMapRedisDao){
		List<String> resDomainList = new ArrayList<String>();
		int size = sbcIdList.size();
		for (int i = 0 ;i < size; i++){			
			String sbcId =  sbcIdList.get(i).toString();
			String exId = this.hget(CommonVariables.REDIS_KEY_SBCEXIDMAP,sbcId);	
			String res = this.hget(CommonVariables.REDIS_KEY_QUOTS + exId, sbcId);
			resDomainList.add(res); 	
		}
		return resDomainList;
	}
	
	public void printQuoteList(){
		int size = quotationsDomainList.size();
		for (int i = 0 ; i < size; i++){
			System.out.println("Beg: " + i);
			System.out.println(quotationsDomainList.get(i).toString());
			System.out.println("End: " + i);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		queryAllQuotations
	 * Author:		1.0 created by lijiaxuan at 2015年7月10日 上午11:52:30
	 *--------------------------------------------------------------------------------------*/
	public List<String> queryQuotations(String key){
		List<String> vals = this.hvals(CommonVariables.REDIS_KEY_QUOTS + key);
		return vals;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		queryQuotations
	 * Author:		1.0 created by lijiaxuan at 2015年7月12日 下午5:29:29
	 *--------------------------------------------------------------------------------------*/
	public String queryQuotations(String key, String field){
		List<String> vals = this.hmget(CommonVariables.REDIS_KEY_QUOTS + key , field);
		if (vals != null && vals.size() > 0){
			return vals.get(0);
		} else return null;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取当日的分时和分量信息。
	 * Method:		querySbcTendency
	 * Author:		1.0 created by lijiaxuan at 2015年8月27日 上午9:59:07
	 *--------------------------------------------------------------------------------------*/
	public List<String> querySbcTendency (String sbcId){
		List<String> quList =  this.lgetall(CommonVariables.REDIS_KEY_TIMESHARE + sbcId);	
		return quList;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		querySbcLastTendency
	 * Author:		1.0 created by lijiaxuan at 2015年9月12日 下午5:29:23
	 *--------------------------------------------------------------------------------------*/
	public String  querySbcLastTendency (String sbcId){
		String quList =  this.lgetlast(CommonVariables.REDIS_KEY_TIMESHARE + sbcId);	
		System.out.println("CommonVariables.REDIS_KEY_TIMESHARE + sbcId " + CommonVariables.REDIS_KEY_TIMESHARE + sbcId);
		return quList;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		addSbcTendency
	 * Author:		1.0 created by lijiaxuan at 2015年8月27日 下午2:42:36
	 *--------------------------------------------------------------------------------------*/
	public void addSbcTendency(String sbcId, String tendency){
		this.lpush(CommonVariables.REDIS_KEY_TIMESHARE + sbcId, tendency);
	}
	
	public HashMap<String, String> jsonStringToMap(String jsonString){
		HashMap<String, String> res = new HashMap<String, String>();
		if (CommonUtil.isStringNull(jsonString)){
			return null;
		}
		String[] keyValues = jsonString.split(",");
		for (int i= 0 ; i< keyValues.length; i++ ){
			if (i==0){
				//第一个字段左边的括号需要去除
				keyValues[i] = keyValues[i].replace("{", "");
			}
			String[] keyValue = keyValues[i].split(":");
			res.put(keyValue[0].replace("\"", ""), keyValue[1].replace("\"", ""));
		}
		return res;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		jsonStringToDomain
	 * Author:		1.0 created by lijiaxuan at 2015年10月14日 下午5:15:30
	 *--------------------------------------------------------------------------------------*/
	public QuotationsDomain jsonStringToDomain(String jsonString){
		QuotationsDomain res = new QuotationsDomain();
		if (CommonUtil.isStringNull(jsonString)){
			return null;
		}
		String[] keyValues = jsonString.split(",");
		res.setAveragePrice(keyValues[0].split(":")[1].replace("\"", ""));
		res.setCurrentPrice(keyValues[1].split(":")[1].replace("\"", ""));
		res.setDealValue(keyValues[2].split(":")[1].replace("\"", ""));
		res.setDealVolume(keyValues[3].split(":")[1].replace("\"", ""));
		res.setExId(keyValues[4].split(":")[1].replace("\"", ""));
		res.setHighestPrice(keyValues[5].split(":")[1].replace("\"", ""));
		res.setLowestPrice(keyValues[6].split(":")[1].replace("\"", ""));
		res.setOpeningPrice(keyValues[7].split(":")[1].replace("\"", ""));
		res.setRecordTime(keyValues[8].split(":")[1].replace("\"", ""));
		res.setRiseScope(keyValues[9].split(":")[1].replace("\"", ""));
		res.setRiseValue(keyValues[10].split(":")[1].replace("\"", ""));
		res.setSbcCode(keyValues[11].split(":")[1].replace("\"", ""));
		res.setSbcId(Integer.parseInt(keyValues[12].split(":")[1].replace("\"", "")));
		res.setSbcName(keyValues[13].split(":")[1].replace("\"", ""));
		res.setYesterClPrice(keyValues[14].replace("}", "").split(":")[1].replace("\"", ""));
		return res;
	}
	
	public QuotationsDomain listToDomain(List<String> string){
		QuotationsDomain quotationsDomain= new QuotationsDomain();
		quotationsDomain.setHighestPrice(string.get(1));
		return quotationsDomain;
	}
}