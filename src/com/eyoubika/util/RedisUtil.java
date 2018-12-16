package com.eyoubika.util;
import java.util.Properties;

import redis.clients.jedis.Jedis;  
import redis.clients.jedis.JedisPool;  
import redis.clients.jedis.JedisPoolConfig;  

import com.eyoubika.common.CommonVariables;


public class RedisUtil {
	static Properties p = CommonUtil.getConfig("redis.properties");
	//Redis服务器IP
	private static String ip = p.getProperty("redis.host").trim();//"127.0.0.1";//"localhost";//"127.0.0.1";//
	//Redis的端口号
	private static String port = p.getProperty("redis.port").trim();
	private static String appCode = "eyoubika";//"admin";
	//访问密码
	private static String AUTH = "";//"admin";
	//可用连接实例的最大数目，默认值为8；
	//如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;

	//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	private static String timeout = p.getProperty("redis.timeout").trim();

	//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			//config.setMaxActive(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			//config.setMaxWait(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ip, Integer.parseInt(port), Integer.parseInt(timeout)); //,auth
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 52      * 获取Jedis实例
	 * 53      * @return
	 * 54
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 70      * 释放jedis资源
	 * 71      * @param jedis
	 * 72
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	public static void getTransNo(String trans) {
		Jedis redis = jedisPool.getResource();
		redis.select(CommonVariables.RDB_TRANSACTION);
		//initTransNo();
		String key = "trans:" + trans + ":curNo";
		autoInc(trans);
		String curNo = redis.get(key);
		returnResource(redis);
		System.out.println("curNo : " + curNo);
	}

	public static void autoInc(String key) {
		Jedis redis = jedisPool.getResource();
		key = "trans:" + key + ":curNo";
		redis.select(CommonVariables.RDB_TRANSACTION);
		redis.incr(key.getBytes());
		returnResource(redis);
	}
	
	/*public static void initTransNo(){
		Jedis redis = jedisPool.getResource();
		String key = "trans:OPAP:curNo";
		redis.select(1);
		redis.set(key, "0");
		returnResource(redis);
	}*/

	public static String getMaxTransNo(String trans) {
		Jedis redis = jedisPool.getResource();
		redis.select(CommonVariables.RDB_TRANSACTION);
		String key = "trans:" + trans + ":curNo";
		if (redis.get(key) == null) {
			redis.set(key, "1");
		} else {
			redis.incr(key.getBytes());
		}
		System.out.println("curNo : " + redis.get(key));
		returnResource(redis);
		return redis.get(key);
	}
}