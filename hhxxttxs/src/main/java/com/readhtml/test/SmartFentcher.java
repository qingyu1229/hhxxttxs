package com.readhtml.test;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SmartFentcher extends CatalogBasicFentcher {
	private String filePath;
	private String encoding;

	public SmartFentcher(String filePath, String encoding) {
		this.filePath = filePath;
		this.encoding = encoding;
	}
	
	
	@Override
	public List<CatalogNode> getCatalog() {
		List<CatalogNode> list=new ArrayList<CatalogNode>();
		try {
			list=new ForContentFentcher(filePath,encoding).getCatalog();
			
			if(list==null||list.size()==0){
				list=new ForCatalogFentcher(filePath,encoding).getCatalog();
			}
		} catch (Exception e) {
			list=new ExceptiveFentcher(filePath,encoding).getCatalog();
			e.printStackTrace();
		}
		if(list==null||list.size()==0){
			list=new ExceptiveFentcher(filePath,encoding).getCatalog();
		}
		return list;
	}
	
	@Override
	public Elements getCatalogElements() {
		return null;
	}

	@Override
	public int checkClassValue(Element e) {
		return 0;
	}

}
