package com.html.selector;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlPage {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Document document;
	
	public HtmlPage(Document document){
		this.document=document;
	}
	public void createFromString(String htmlStr){
		document=Jsoup.parse(htmlStr);
	}
	
	public void createFromInputStream(File in,String charsetName){
		try {
		 document=	Jsoup.parse(in, charsetName);
		} catch (IOException e) {
			logger.error("初始化错误", e);
		}
	}
	
	public HtmlPage setBaseUri(String baseuri){
		document.setBaseUri(baseuri);
		return this;
	}
	
	public String getData(){
		
		
		return "";
	}
}
