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

	protected int getPIdByDepth(int depth,int id){
		
		
		
		if(depth==0){
			return map.get(id).getP_id();
		}else{
			int pid= map.get(id).getP_id();
			if(map.get(pid)==null){
				return pid;
			}
			return getPIdByDepth(depth-1,pid);
			
		}
	}
	
	protected int getOrderByDepth(int depth,int id){
		if(depth==0){
			return map.get(id).getOrder()+1;
		}else{
			int pid= map.get(id).getP_id();
			if(map.get(pid)==null){
				return map.get(id).getOrder();
			}
			
			return getOrderByDepth(depth-1,pid);
		}
	}
	
	protected void modifyImageSrc(Document document){
		if(document==null){
			return;
		}
		Elements elements=document.getElementsByTag("img");
		if(elements==null){
			return;
		}
		for(Element element:elements){
			String srcString=element.attr("src");
			boolean bl= srcString.startsWith("file");
			if(bl){
				String[] ps=srcString.split("\\\\");
				String path= ps[ps.length-2]+"/"+ps[ps.length-1];
				element.attr("src", "upload/"+path);
			}else{
				
				element.attr("src", "upload/"+srcString);
			}
		}
	}
	
}
