package com.xuexi.hhxxttxs;

import org.apache.commons.lang3.math.NumberUtils;


public class Test {

	public static void main(String[] args) {
		Integer count=127;
		System.out.println(Integer.toBinaryString(128));
	}
	 public static byte[] intToByte4(int i) {  
	        byte[] targets = new byte[4];  
	        targets[3] = (byte) (i & 0xFF);  
	        targets[2] = (byte) (i >> 8 & 0xFF);  
	        targets[1] = (byte) (i >> 16 & 0xFF);  
	        targets[0] = (byte) (i >> 24 & 0xFF);  
	        return targets;  
	    } 
	 
	
}
