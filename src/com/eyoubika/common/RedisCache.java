package com.eyoubika.common;
//import org.apache.ibatis.cache.Cache;

import redis.clients.jedis.Jedis;  
import redis.clients.jedis.JedisPool;  
import redis.clients.jedis.JedisPoolConfig;  

import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
//import redis.clients.jedis.BinaryJedisCluster;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  

import com.eyoubika.util.SerializeUtil;
import com.ibatis.sqlmap.engine.cache.CacheController;
import com.ibatis.sqlmap.engine.cache.CacheModel;

public class RedisCache implements CacheController{
	 private static Logger logger = LoggerFactory.getLogger(RedisCache.class);  
	    private Jedis redisClient=createReids();  
	     /** The ReadWriteLock. */    
	    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();   
	      
	    private String id;  
	      
	    public RedisCache(final String id) {    
	        if (id == null) {  
	            throw new IllegalArgumentException("Cache instances require an ID");  
	        }  
	        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id="+id);  
	        this.id = id;  
	    }    
	 
	    public String getId() {  
	        return this.id;  
	    }  
	  
	    public int getSize() {  
	     
	        return Integer.valueOf(redisClient.dbSize().toString());  
	    }  
	  
	    public void putObject(Object key, Object value) {  
	        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>putObject:"+key+"="+value);  
	        redisClient.set(SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));  
	    }  
	  
	    public void clear() {  
	          redisClient.flushDB();  
	    }  

	    public ReadWriteLock getReadWriteLock() {  
	        return readWriteLock;  
	    }  
	   
	    protected  static Jedis createReids(){  
	        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");  
	        return pool.getResource();  
	    }

		@Override
		public void configure(Properties arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void flush(CacheModel arg0) {
			// TODO Auto-generated method stub
			// getCache(cacheModel).removeAll(); 
			 redisClient.flushDB();  
		}

		@Override
		public Object getObject(CacheModel arg0, Object key) {
			 Object value = SerializeUtil.unserialize(redisClient.get(SerializeUtil.serialize(key.toString())));  
		        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>getObject:"+key+"="+value);  
		        return value;  
		}

		@Override
		public void putObject(CacheModel arg0, Object key, Object value) {
			 logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>putObject:"+key+"="+value);  
		        redisClient.set(SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));  
		}

		@Override
		public Object removeObject(CacheModel arg0, Object key) {
			return redisClient.expire(SerializeUtil.serialize(key.toString()),0);  	
		} 
	    
	    
}