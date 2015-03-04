package com.jedis;

import static org.junit.Assert.*;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {

	@Test
	public void test_pool() {
		Jedis jedis= JedisUtil.getInstance().getJedis();
		
		jedis.set("key2", "v2");
	}
	
	@Test
	public void test(){
		
		
		
	}
}
