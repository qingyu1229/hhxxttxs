package com.getBaiKe;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetWordFromDictall {
	
	
	
	
	public static void main(String[] args) {
		/*try {
			Document doc=Jsoup.connect("http://www.dictall.com/zt/G/w1.htm").get();
			Element e= doc.getElementById("catelist");
			System.out.println(e.text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		readLinksFromFile();
	}
	
	private static void readLinksFromFile(){
		try {
			String docStr= FileUtils.readFileToString(new File("d:/doc.txt"),"gbk");
			Document doc= Jsoup.parse(docStr);
			doc.setBaseUri("http://www.dictall.com/");
			Elements aElements= doc.getElementById("cateDiv1").getElementsByTag("a");
			
			for(Element e:aElements){
				String href=e.absUrl("href");
				//
				String text=e.text();
				System.out.println("text:"+text+"   href:"+href);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
