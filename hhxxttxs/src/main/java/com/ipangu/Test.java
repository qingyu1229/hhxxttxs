package com.ipangu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Test {
	
	HttpClient httpClient;
	public static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19";  
	
	public String fetch() throws ParseException, IOException{
		HttpPost httpost = new HttpPost("http://m.ctrip.com/restapi/soa2/10324/hotel/product/roomget");
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("biz", "1"));
		nvps.add(new BasicNameValuePair("contentType", "json"));
		nvps.add(new BasicNameValuePair("contrl", "2"));
		nvps.add(new BasicNameValuePair("icldrid", "0"));
		nvps.add(new BasicNameValuePair("id", "434984"));
		nvps.add(new BasicNameValuePair("needRoom", "false"));
		nvps.add(new BasicNameValuePair("num", "1"));
		nvps.add(new BasicNameValuePair("pay", "0"));
		nvps.add(new BasicNameValuePair("priceBiz", "0"));
		nvps.add(new BasicNameValuePair("cityId", "0"));
		nvps.add(new BasicNameValuePair("dstId", "0"));
		nvps.add(new BasicNameValuePair("inDay", "2015-02-02"));
		nvps.add(new BasicNameValuePair("outDay", "2015-02-03"));
		
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		HttpResponse response = httpClient.execute(httpost);
		HttpEntity entity = response.getEntity();
		int statusCode= response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		String s= EntityUtils.toString(entity);
		System.out.println(s);
		
		return "";
	}
}
