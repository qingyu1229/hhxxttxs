package com.readhtml.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ReadHtml {

	List<CatalogNode> list=new ArrayList<CatalogNode>();
	 Map<Integer,CatalogNode> map=new HashMap<Integer,CatalogNode>();
	
	
	public static void main(String[] args) throws IOException {
		String encoding = "gb2312";
		/*String fileString = FileUtils.readFileToString(new File(
				"d:/1411954094515.html"), encoding);
*/
		//CatalogFentcher cf=new ForCatalogFentcher("d:/1411954094515.html","gb2312");
		CatalogFentcher cf=new ForContentFentcher("d:/1411953924640.html","utf-8");
		//CatalogFentcher cf=new SmartFentcher("d:/1411953924640.html","utf-8");
		//cf.getCatalog();
		
		FileUtils.writeStringToFile(new File("d:/t11231.txt"), cf.getCatalog().toString());
		
		//System.out.println(cf.getCatalog());
	}

	public Elements getCatalogElements(String filepath,String encoding) throws IOException{
		Elements elements=null;
		String fileString = FileUtils.readFileToString(new File(
				filepath), encoding);
		Document doc = Jsoup.parse(fileString);
		elements = doc.body().getElementsByAttributeValueStarting(
				"class", "MsoToc");
		if(elements==null){
			
		}
		return elements;
	}
	
	
	
	public void getCatalog(Elements elements) {

		int last_num=0;
		
		CatalogNode cn0=new CatalogNode();
		cn0.setId(0);
		cn0.setText(cleanoutNodeValue(elements.get(0).text()));
		cn0.setP_id(0);
		cn0.setOrder(1);
		list.add(cn0);
		for (int i = 1; i < elements.size(); i++) {
			Element element = elements.get(i);
			String classValue = element.attr("class");
			int num= checkClassValue(classValue);
			CatalogNode cn=new CatalogNode();
			cn.setId(i);
			cn.setText(cleanoutNodeValue(element.text()));
			if(last_num<num){
				cn.setP_id(i-1);
				cn.setOrder(1);
			}else if(last_num==num){
				cn.setP_id(map.get(i-1).getP_id());
				cn.setOrder(map.get(i-1).getOrder()+1);
			}else if(last_num>num){
				cn.setP_id(map.get(map.get(i-1).getP_id()).getP_id());
				cn.setOrder(map.get(map.get(i-1).getP_id()).getOrder()+1);
			}
			map.put(i, cn);
			list.add(cn);
			last_num=num;
		}
		System.out.println(map);
	}

	public int checkClassValue(String classValue) {
		if (classValue.matches("MsoToc\\d{1,2}")) {
			String numStr = classValue.replace("MsoToc", "");
			try {
				return Integer.valueOf(numStr);
			} catch (NumberFormatException e) {
				return -1;
			}
		}
		return -1;
	}
	
	public String getNode(Element element){
		String classValue = element.attr("class");
		int num= checkClassValue(classValue);
		
		System.out.println(num);
		return "";
	}
	
	public String cleanoutNodeValue(String v){
		if(v.contains("...")){
			return v.split("\\...")[0];
		}
		return v;
	}

	public Elements fentchFromCatalog(Document doc){
		return doc.body().getElementsByAttributeValueStarting(
				"class", "MsoToc");
	}
	
	public Elements fentchFromContent(Document doc){
		Elements elements=doc.body().getAllElements();
		Elements h_elements=new Elements();
		for(Element e:elements){
			String tagName=e.tagName();
			if(("h1".equals(tagName)||"h2".equals(tagName)||"h3".equals(tagName)||"h4".equals(tagName)||"h5".equals(tagName))){
				h_elements.add(e);
			}
		}
		System.out.println(h_elements);
		return h_elements;
	}
	
}
