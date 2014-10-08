package com.readhtml.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class CatalogBasicFentcher implements CatalogFentcher {
	List<CatalogNode> list=new ArrayList<CatalogNode>();
	Map<Integer,CatalogNode> map=new HashMap<Integer,CatalogNode>();
	
	@Override
	public List<CatalogNode> getCatalog() {
		
		return null;
	}
	
	public String cleanoutNodeValue(String v){
		if(v.contains("...")){
			return v.split("\\...")[0];
		}
		return v;
	}
	
	public abstract Elements getCatalogElements();
	
	public abstract int checkClassValue(Element e);
	
	public  Document getDocument(String filePath,String encoding){
		Elements elements=null;
		String fileString="";
		try {
			fileString = FileUtils.readFileToString(new File(
					filePath), encoding);
		} catch (IOException e) {
			return null;
		}
		Document doc = Jsoup.parse(fileString);
		return doc;
	}

}
