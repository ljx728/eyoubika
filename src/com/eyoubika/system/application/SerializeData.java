package com.eyoubika.system.application;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.sbc.dao.SbcPriceDao;
import com.eyoubika.sbc.domain.SbcPriceDomain;
import com.eyoubika.spider.dao.QuotesRedisDao;
import com.eyoubika.system.dao.*;
import com.eyoubika.system.domain.*;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
public class SerializeData{
	private QuotesRedisDao quotesRedisDao;
	private DbManagerAL  dbManagerAL;
	public void putQuotationsToDb(){
		SbcPriceDao sbcPriceDao = new SbcPriceDao();
		SbcPriceDomain sbcPriceDomain = new SbcPriceDomain();
		String[] exIds = {			
				CommonVariables.EXCHANGE_NANJING,
				CommonVariables.EXCHANGE_NANFANG,
				CommonVariables.EXCHANGE_ZHONGNAN,				
				CommonVariables.EXCHANGE_FULITE,
				CommonVariables.EXCHANGE_JINMAJIA,
				CommonVariables.EXCHANGE_JIANGSU,
				//CommonVariables.EXCHANGE_SHANGHAI,
				CommonVariables.EXCHANGE_HUAXIA,
				CommonVariables.EXCHANGE_HUAZHONG
				
		};
		int size = exIds.length;
		for (int i = 0; i < size; i ++){
			List<String> quotsList = quotesRedisDao.queryQuotations(exIds[i]);
			int listSize = quotsList.size();
			for (int j = 0; j < listSize; j++){
				sbcPriceDomain.init();
				HashMap<String, String> map = quotesRedisDao.jsonStringToMap(quotsList.get(j));
				if (CommonUtil.isStringDigit(map.get("averagePrice"))) sbcPriceDomain.setAveragePrice(Double.parseDouble(map.get("averagePrice")));
				if (CommonUtil.isStringDigit(map.get("currentPrice"))) sbcPriceDomain.setClosingPrice(Double.parseDouble(map.get("currentPrice")));
				if (CommonUtil.isStringDigit(map.get("dealVolume"))) sbcPriceDomain.setDealVolume(Integer.parseInt(map.get("dealVolume")));
				sbcPriceDomain.setExId(map.get("exId"));
				if (CommonUtil.isStringDigit(map.get("highestPrice"))) sbcPriceDomain.setHighestPrice(Double.parseDouble(map.get("highestPrice")));
				if (CommonUtil.isStringDigit(map.get("lowestPrice"))) sbcPriceDomain.setLowestPrice(Double.parseDouble(map.get("lowestPrice")));
				if (CommonUtil.isStringDigit(map.get("poq"))) sbcPriceDomain.setPoq(Integer.parseInt(map.get("poq")));
				sbcPriceDomain.setRecordDate(CommonUtil.getNowDate());
				if (CommonUtil.isStringDigit(map.get("riseValue"))) sbcPriceDomain.setRiseValue(Double.parseDouble(map.get("riseValue")));
				sbcPriceDomain.setSbcCode(map.get("sbcCode"));
				sbcPriceDomain.setSbcId(Integer.parseInt(map.get("sbcId")));
				sbcPriceDomain.setSbcName(map.get("sbcName"));
				if (CommonUtil.isStringDigit(map.get("totalValue"))) sbcPriceDomain.setTotalValue(Integer.parseInt(map.get("totalValue")));
	
				if (sbcPriceDao.findSbcPrice(sbcPriceDomain.getSbcId()) != null){
					sbcPriceDao.modifySbcPrice(sbcPriceDomain);
				} 
			}
		}		
	}
	
	public static void main(String[] params){
		//putQuotationsToDb();
		
	}
}
