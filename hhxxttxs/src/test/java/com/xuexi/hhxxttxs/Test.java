package com.xuexi.hhxxttxs;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Test {

	public static void main(String[] args) {
		try {
			Document doc= Jsoup.connect("http://nyj.yw.gov.cn/zcfg/nyb/201407/t20140723_574953.html").get();
			Elements elements= doc.select("#myTable > tbody > tr:nth-child(1) > td > span");
			System.out.println(elements);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//Integer count=127;
		//System.out.println(Integer.toBinaryString(128));
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
