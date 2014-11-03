package com.readhtml.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
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
		CatalogFentcher cf=new ForCatalogFentcher("d:/1413352405140.html","gb2312");
		//CatalogFentcher cf=new ForContentFentcher("d:/1412926968625.html","gb2312");
		//CatalogFentcher cf=new SmartFentcher("d:/1411953924640.html","utf-8");
		//cf.getCatalog();
		
		
		//CatalogFentcher cf=new SmartFentcher("d:/1413250053468.html","gb2312");
		//CatalogFentcher cf=new ExceptiveFentcher("d:/1413250053468.html","gb2312");
		
		
		FileUtils.writeStringToFile(new File("d:/t112312.txt"), cf.getCatalog().toString());
		
		//System.out.println(cf.getCatalog());
	}


	
}
