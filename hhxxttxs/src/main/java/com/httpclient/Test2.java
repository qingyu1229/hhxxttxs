package com.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Test2 {

	public static void main(String[] args) {

		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpPost post = new HttpPost("http://www.baidu.com/");
		HttpGet get=new HttpGet("http://www.baidu.com/");
		
		try {
			//httpClient.set
			HttpResponse httpResponse = httpClient.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			//InputStream is = entity.getContent();
			
			String s= EntityUtils.toString(entity);
			System.out.println(s);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
