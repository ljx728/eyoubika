package com.eyoubika.common;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


/*==========================================================================================*
 * Description:	内存数据库基础类
 * Class:		RedisDao
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年7月14日 下午5:09:00
 *==========================================================================================*/
public class RedisDao {
	private ShardedJedisPool shardedJedisPool;

	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	public ShardedJedis getRedis() {
		ShardedJedis jedis = shardedJedisPool.getResource();
		return jedis;
	}

	/*
	 * public void freeRedis(){ shardedJedisPool.returnResource(
	 * shardedJedisPool.getResource()); }
	 */

	public Map<String, String> hgetAll(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		Map<String, String> map = jedis.hgetAll(key);
		shardedJedisPool.returnResource(jedis);
		return map;

	}

	public String hget(String key, String feild) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		String res = jedis.hget(key, feild);
		shardedJedisPool.returnResource(jedis);
		return res;
	}

	public void hmset(String key, Map<String, String> value) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		jedis.hmset(key, value);
		shardedJedisPool.returnResource(jedis);
	}

	public List<String> hvals(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		List<String> res = jedis.hvals(key);
		shardedJedisPool.returnResource(jedis);
		return res;
	}

	public List<String> hmget(String key, String field) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		List<String> res = jedis.hmget(key, field);
		shardedJedisPool.returnResource(jedis);
		return res;
	}

	public List<String> lrange(String key, int start, int end) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		List<String> res = jedis.lrange(key, start, end);
		shardedJedisPool.returnResource(jedis);
		return res;
	}

	public String lgetlast(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		String last = null;
		List<String> res = jedis.lrange(key, 0, 0);
		if (res != null && res.size() > 0) {
			last = res.get(0);
		}
		shardedJedisPool.returnResource(jedis);
		return last;
	}

	public List<String> lgetall(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		List<String> res = jedis.lrange(key, 0, -1);
		shardedJedisPool.returnResource(jedis);
		return res;
	}

	public void lpush(String key, String data) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		jedis.lpush(key, data);
		shardedJedisPool.returnResource(jedis);
	}

	public void del(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		jedis.del(key);
		shardedJedisPool.returnResource(jedis);
	}

	public Set<String> hkeys(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		// Set<String> keys = jedis.keys("*");
		Set<String> res = jedis.hkeys(key);
		shardedJedisPool.returnResource(jedis);
		return res;
	}

	public void set(String key, String value) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		jedis.set(key, value);
		shardedJedisPool.returnResource(jedis);
	}

	public String get(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		String res = jedis.get(key);
		shardedJedisPool.returnResource(jedis);
		return res;
	}

	public void testString() {

		// -----添加数据----------
		this.set("lili", "lijiaxuan");// 向key-->name中放入了value-->xinxin
		System.out.println(this.get("name"));// 执行结果：xinxin

		/*
		 * jedis.append("name", " is my lover"); //拼接
		 * System.out.println(jedis.get("name"));
		 * 
		 * jedis.del("name"); //删除某个键 System.out.println(jedis.get("name"));
		 * //设置多个键值对 jedis.mset("name","liuling","age","23","qq","476777389");
		 * jedis.incr("age"); //进行加1操作 System.out.println(jedis.get("name") +
		 * "-" + jedis.get("age") + "-" + jedis.get("qq"));
		 */

	}

	public void insert() {

	}

}
