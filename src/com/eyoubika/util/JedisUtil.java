package com.eyoubika.util;  
  
import java.util.HashMap;  
import java.util.Map;  
  
import java.util.Properties;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  


import com.eyoubika.common.CommonVariables;

import redis.clients.jedis.Jedis;  
import redis.clients.jedis.JedisPool;  
import redis.clients.jedis.JedisPoolConfig;  

  
/** 
 * Redis工具类,用于获取RedisPool. 
 * 参考官网说明如下： 
 * You shouldn't use the same instance from different threads because you'll have strange errors. 
 * And sometimes creating lots of Jedis instances is not good enough because it means lots of sockets and connections, 
 * which leads to strange errors as well. A single Jedis instance is not threadsafe! 
 * To avoid these problems, you should use JedisPool, which is a threadsafe pool of network connections. 
 * This way you can overcome those strange errors and achieve great performance. 
 * To use it, init a pool: 
 *  JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost"); 
 *  You can store the pool somewhere statically, it is thread-safe. 
 *  JedisPoolConfig includes a number of helpful Redis-specific connection pooling defaults. 
 *  For example, Jedis with JedisPoolConfig will close a connection after 300 seconds if it has not been returned. 
 * @author wujintao 
 */  
public class JedisUtil  {  
   // protected static Logger log = LoggerFactory.getLogger(getClass());  
    /** 
     * 私有构造器. 
     */  
	private JedisPool pool;
	
	
    public JedisPool getPool() {
		return pool;
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

	private JedisUtil() {  
    	JedisPool pool = getPool();
    }  
    private static Map<String,JedisPool> maps  = new HashMap<String,JedisPool>();     
      
    /** 
     * 获取连接池. 
     * @return 连接池实例 
     */  
    private static JedisPool getPool(String ip,String port) {  
        String key = ip+":" +port;  
        //JedisPool pool = null;  
         Properties p = CommonUtil.getConfig("redis.properties");
   	 //Redis服务器IP
         if (ip == null){
        	  ip = p.getProperty("redis.host");//"127.0.0.1";//"localhost";//"127.0.0.1";//
         }
         if (port == null){
        	 port = p.getProperty("redis.port");
         }
        //Redis的端口号
        if(!maps.containsKey(key)) {  
            JedisPoolConfig config = new JedisPoolConfig();  
            //config.setMaxIdle(Integer.parseInt(p.getProperty("redis.maxTotal")));  
            config.setMaxIdle(Integer.parseInt(p.getProperty("redis.maxTotal")));  
            config.setMaxWaitMillis(Integer.parseInt(p.getProperty("redis.maxTotal")));  
            config.setTestOnBorrow(Boolean.parseBoolean(p.getProperty("redis.testOnBorrow")));
            config.setTestOnReturn(Boolean.parseBoolean(p.getProperty("redis.testOnReturn")));
            try{    
                /** 
                 *如果你遇到 java.net.SocketTimeoutException: Read timed out exception的异常信息 
                 *请尝试在构造JedisPool的时候设置自己的超时值. JedisPool默认的超时时间是2秒(单位毫秒) 
                 */  
                pool = new JedisPool( config, ip);// new JedisPool(config, ip, port, "2");  //,RedisConfig.getTimeout()
                maps.put(key, pool);  
            } catch(Exception e) {  
                e.printStackTrace();  
            }  
        }else{  
            pool = maps.get(key);  
        }  
        return pool;  
    }  
  
    /** 
     *类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例 
     *没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。 
     */  
    private static class RedisUtilHolder{  
        /** 
         * 静态初始化器，由JVM来保证线程安全 
         */  
        private static JedisUtil instance = new JedisUtil();  
    }  
  
    /** 
     *当getInstance方法第一次被调用的时候，它第一次读取 
     *RedisUtilHolder.instance，导致RedisUtilHolder类得到初始化；而这个类在装载并被初始化的时候，会初始化它的静 
     *态域，从而创建RedisUtil的实例，由于是静态的域，因此只会在虚拟机装载类的时候初始化一次，并由虚拟机来保证它的线程安全性。 
     *这个模式的优势在于，getInstance方法并没有被同步，并且只是执行一个域的访问，因此延迟初始化并没有增加任何访问成本。 
     */  
    public static JedisUtil getInstance() {  
        return RedisUtilHolder.instance;  
    }  
      
    /** 
     * 获取Redis实例. 
     * @return Redis工具类实例 
     */  
    public static Jedis getJedis(String ip,String port) {  
        Jedis jedis  = null;  
        int count =0;  
        do{  
            try{   
                jedis = getPool(ip,port).getResource();  
                //log.info("get redis master1!");  
            } catch (Exception e) {  
               // log.error("get redis master1 failed!", e);  
                 // 销毁对象    
                getPool(ip,port).returnBrokenResource(jedis);    
            }  
            count++;  
        }while(jedis==null && count < 2);
       // }while(jedis==null&&count<BaseConfig.getRetryNum());  
        return jedis;  
    }  
    
    public static Jedis getJedis() {  
        Jedis jedis  = null;  
        int count =0;  
        do{  
            try{   
                jedis = getPool(null,null).getResource();  
            } catch (Exception e) {  
               // log.error("get redis master1 failed!", e);  
                 // 销毁对象    
                getPool(null,null).returnBrokenResource(jedis);    
            }  
            count++;  
        }while(jedis==null && count < 2);
       // }while(jedis==null&&count<BaseConfig.getRetryNum());  
        return jedis;  
    }  
  
    /** 
     * 释放redis实例到连接池. 
     * @param jedis redis实例 
     */  
    public static void closeJedis(Jedis jedis,String ip,String port) {  
        if(jedis != null) {  
            getPool(ip,port).returnResource(jedis);  
        }  
    }  
    
    public static void closeJedis(Jedis jedis) {  
        if(jedis != null) {  
            getPool(null, null).returnResource(jedis);  
        }  
    }  
    
    //----------
    public static void getCurNo(){
		Jedis redis = jedisPool.getResource();
		redis.select(CommonVariables.RDB_TRANSACTION);
		//initTransNo();
		autoInc();
		String curNo = redis.get("trans:OPAP:curNo");
		returnResource(redis);
		System.out.println("curNo : " + curNo);
	}
	
	public static void autoInc(){
		Jedis redis = jedisPool.getResource();
		redis.select(CommonVariables.RDB_TRANSACTION);
		redis.incr("trans:OPAP:curNo".getBytes());
		returnResource(redis);
	}
	
	public static void initTransNo(){
		Jedis redis = jedisPool.getResource();
		String key = "trans:OPAP:curNo";
		redis.select(1);
		redis.set(key, "0");
		returnResource(redis);
	}
		    
	public static String getMaxTransNo(String trans){
		Jedis redis = jedisPool.getResource();
		redis.select(CommonVariables.RDB_TRANSACTION);
		String key = "trans:"+ trans + ":curNo";
		if (redis.get(key)==null){
			redis.set(key, "1");
		} else {
			redis.incr(key.getBytes());
		}
		System.out.println("curNo : " + redis.get(key));
		returnResource(redis);	
		return redis.get(key);
	}
}  