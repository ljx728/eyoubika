package com.eyoubika.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.eyoubika.util.RedisUtil;

import redis.clients.jedis.Jedis;
public class testRedis{
	 private Jedis jedis; 
	 public void setup() {
		        //连接redis服务器，192.168.0.100:6379
		         //jedis = new Jedis("127.0.0.1", 6379);
		         jedis = RedisUtil.getJedis();
		        //权限认证
		          //jedis.auth("admin");  
		    }
	    public void testString() {
	    	this.setup();
	         //-----添加数据----------  
	    	jedis.set("name","xinxin");//向key-->name中放入了value-->xinxin  
	          System.out.println(jedis.get("name"));//执行结果：xinxin  
	          
	          jedis.append("name", " is my lover"); //拼接
	          System.out.println(jedis.get("name")); 
	          
	          jedis.del("name");  //删除某个键
	          System.out.println(jedis.get("name"));
	          //设置多个键值对
	          jedis.mset("name","liuling","age","23","qq","476777389");
	          jedis.incr("age"); //进行加1操作
	          System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));
	          
	      }
	    
	    public static void main(String args[]){
	    	testRedis rr = new testRedis();
	    	rr.testString();
	    	//rr.tt();
	    }
	    
	    public void tt(){
	  
	    Properties p = CommonUtil.getConfig("jdbc.properties");
	    System.out.println("driver:"+p.getProperty("driver")+",password:"+p.getProperty("password"));
	    }
}