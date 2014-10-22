package com.liang.selector;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupSelector implements Selector {
	
	private Document doc;
	
	public JsoupSelector(String htmlStr){
		doc=Jsoup.parse(htmlStr);
	}
	
	
	
	@Override
	public String select(String selectText) {
		return doc.select(selectText).text();
	}

	@Override
	public Map<String, String> select(Map<String, String> map) {
		
		return null;
	}

}
