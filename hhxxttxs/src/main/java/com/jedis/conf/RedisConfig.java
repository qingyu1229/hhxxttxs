package com.jedis.conf;

public class RedisConfig {
	
	private static final int maxactive=500;
	private static final int maxidle=50;
	private static final int maxwait=50;
	
	private static final int timeout=3000;
	
	private static final int retryNum=5;
	
	
	private static final String ip="182.254.133.252";
	private static final int port=6379;

	public static int getMaxactive() {
		return maxactive;
	}

	public static int getMaxidle() {
		return maxidle;
	}

	public static int getMaxwait() {
		return maxwait;
	}

	public static int getTimeout() {
		return timeout;
	}

	

	public static int getRetrynum() {
		return retryNum;
	}

	public static String getIp() {
		return ip;
	}

	public static int getPort() {
		return port;
	}
	
	
	
}
