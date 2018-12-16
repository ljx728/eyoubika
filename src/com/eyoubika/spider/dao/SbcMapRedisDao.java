package com.eyoubika.spider.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.eyoubika.common.CommonMaps;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.RedisDao;
import com.eyoubika.info.domain.QuotationsDomain;
import com.eyoubika.sbc.dao.SbcBasicDao;
import com.eyoubika.sbc.dao.SbcPriceDao;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.sbc.domain.SbcIdMapDomain;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.util.RedisUtil;
public class SbcMapRedisDao extends RedisDao{
	
	public void addSbcMap(SbcIdMapDomain sbcIdMapDomain){	
		String sbcIndex= "s:";
		String ephIndex =  "e:";
		 this.getRedis().set(sbcIndex + sbcIdMapDomain.getEphId(), sbcIdMapDomain.getSbcId().toString());
		 this.getRedis().set(ephIndex + sbcIdMapDomain.getSbcId(), sbcIdMapDomain.getEphId());
	}
	
	public String findSbcId(String ephId){
		String sbcIndex= "s:";
		return  this.getRedis().get(sbcIndex + ephId);
	}
	
	public String findEphId(Integer sbcId){
		String ephIndex =  "e:";
		 ShardedJedis jds = this.getRedis();
		String ephId = jds.get(ephIndex + sbcId);
		this.getShardedJedisPool().returnResource(jds);
		return ephId;
	}
	
	public String findExId(String dzpid){
		String exIndex= "ex:";
		return  this.getRedis().get(exIndex + dzpid);
	}
	
	public String findEpid(String exId){
		String epIndex= "ep:";
		return  this.getRedis().get(epIndex + exId);
	}
	
}