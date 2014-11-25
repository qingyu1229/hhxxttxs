package com.jedis;

import static org.junit.Assert.*;

import org.junit.Test;

public class RedisTest {

	@Test
	public void test() {
		
		System.out.println(RedisClient.getClient().set("key","123456"));
	    System.out.println(RedisClient.getClient().get("key"));
		
	}

}
