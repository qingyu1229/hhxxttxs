package com.jedis.test;

import redis.clients.jedis.Jedis;

import com.jedis.JedisUtil;

public class SetValues extends Thread{
	private String key;
	private String value;
	private Jedis jedis;
	
	
	public SetValues(String key,String value){
		this.key=key;
		this.value=value;
		jedis= JedisUtil.getInstance().getJedis();
	}
	
	@Override
	public void run() {
		
		jedis.set(key, value);
		
	}

	
}
