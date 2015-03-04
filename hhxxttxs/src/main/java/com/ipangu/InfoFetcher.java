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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoFetcher {
	HttpClient httpClient ;
	
	public String fetch(String companyName) throws JSONException, ParseException, IOException{
		HttpPost httpost = new HttpPost("http://ipangu.baidu.com/ipangu-callout/oppres/oppResourceManage_query.action");
		httpClient = new DefaultHttpClient();
		
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		
		nvps.add(new BasicNameValuePair("queryCondition.urlType", "1"));
		nvps.add(new BasicNameValuePair("queryCondition.name", companyName));
		nvps.add(new BasicNameValuePair("queryCondition.custType", "-1"));
		
		nvps.add(new BasicNameValuePair("queryCondition.hintType", "-1"));
		nvps.add(new BasicNameValuePair("queryCondition.productLineId", "-1"));
		nvps.add(new BasicNameValuePair("queryCondition.abandonRemark", "-1"));
		nvps.add(new BasicNameValuePair("queryCondition.inPosid", "-1"));
		nvps.add(new BasicNameValuePair("queryCondition.abandonUserId", "-1"));
		nvps.add(new BasicNameValuePair("queryCondition.source1", "-1"));
		nvps.add(new BasicNameValuePair("queryCondition.source2", "-1"));
		nvps.add(new BasicNameValuePair("queryCondition.trade1", "-1"));
		nvps.add(new BasicNameValuePair("queryCondition.trade2", "-1"));
		nvps.add(new BasicNameValuePair("queryCondition.provId", "-1"));
		nvps.add(new BasicNameValuePair("queryCondition.countyId", "-1"));
		nvps.add(new BasicNameValuePair("curPage", "1"));
		nvps.add(new BasicNameValuePair("pageSize", "20"));
		nvps.add(new BasicNameValuePair("stoken", "ebba5151-28a9-4ac9-bdec-06c30e1a2615"));
		
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		httpost.addHeader("Cookie", "BAIDUID=AD15CCF7C292DE921EA94D425CEDA6FB:FG=1; BAIDUPSID=AD15CCF7C292DE921EA94D425CEDA6FB; uc_login_unique=b34e959968d32eb67b4611aa94f41a11; SIGNIN_UC=70a2711cf1d3d9b1a82d2f87d633bd8a01754866011; __cas__st__155=4f02d4e0283bd7507d7c91e51c7d915cb580f84cab3826633307a9227871bb980833923ca02a5ca36021fbcd; __cas__id__155=5381774; __cas__rn__=175486601; H_SF_JTZY_KKS=1; Hm_lvt_bdc3f84f9a2236f657905fe9446ad004=1425386253,1425474155; Hm_lpvt_bdc3f84f9a2236f657905fe9446ad004=1425474175; UCID_COOKIE_NAME=5381774; JSESSIONID=9CC84F1B72DA543A2BAC4D460A04FE38");
		System.out.println(companyName);
		HttpResponse response = httpClient.execute(httpost);
		HttpEntity entity = response.getEntity();
		int statusCode= response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		String s= EntityUtils.toString(entity);
		System.out.println(s);
		System.out.println("=============================================");
		return s;
		//return "";
	}
	
	
}
