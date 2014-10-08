package com.readhtml.test;

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
		Document doc= getDocument(filePath,encoding);
		Elements elements= doc.getElementsByAttributeValueStarting("class", "MsoToc");
		if(elements.size()>0){
			return new ForCatalogFentcher(filePath,encoding).getCatalog();
		}else{
			return new ForContentFentcher(filePath,encoding).getCatalog();
		}
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
