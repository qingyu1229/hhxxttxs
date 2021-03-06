package com.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Test {
	
	public static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19";  
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost("http://my.oschina.net/u/1032350/fellow");
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		/*nvps.add(new BasicNameValuePair("biz", "1"));
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
		nvps.add(new BasicNameValuePair("outDay", "2015-02-03"));*/
		
		//nvps.add(new BasicNameValuePair("Cookie", "oscid=WEn2ruDRkA5zbxnHQ2DapdSxQXpnogPfPDSuDwCfdaW%2F8jJlXoSyupJfptQ5iaclJuN9iX9FbAjjstJ8WXfDID4xKE4nmcY97An2TmYXIdeR5jJEXDMwADTEXOJ70Zi8"));
		
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		httpost.setHeader("Cookie", "oscid=WEn2ruDRkA5zbxnHQ2DapdSxQXpnogPfPDSuDwCfdaW%2F8jJlXoSyupJfptQ5iaclJuN9iX9FbAjjstJ8WXfDID4xKE4nmcY97An2TmYXIdeR5jJEXDMwADTEXOJ70Zi8");
		
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
		int statusCode= response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		String s= EntityUtils.toString(entity);
		System.out.println(s);
		
	}
}
