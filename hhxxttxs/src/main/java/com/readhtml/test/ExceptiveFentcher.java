package com.readhtml.test;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExceptiveFentcher extends CatalogBasicFentcher{
	
	private String filePath;
	private String encoding;
	private final static String PNODETEXT="父目录";
	
	public ExceptiveFentcher(String filePath,String encoding){
		this.filePath=filePath;
		this.encoding=encoding;
	}

	@Override
	public List<CatalogNode> getCatalog() {
		List<CatalogNode> list=new ArrayList<CatalogNode>();
		Elements p_elements=getCatalogElements();
		CatalogNode cn0=new CatalogNode();
		cn0.setContent("");
		cn0.setId(0);
		cn0.setOrder(1);
		cn0.setP_id(-1);
		cn0.setText(PNODETEXT+0);
		list.add(cn0);
		int j=1;
		int lastPid=0;
		for(int i=1;i<p_elements.size();i++){
			Element e=p_elements.get(i);
			CatalogNode cn=new CatalogNode();
			cn.setId(i);
			if(i%10==0){
				cn.setContent("");
				cn.setOrder(i/10);
				cn.setP_id(-1);
				cn.setText(PNODETEXT+i/10);
				j=1;
				lastPid=i;
			}else{
				cn.setContent(e.toString());
				cn.setOrder(j++);
				cn.setP_id(lastPid);
				cn.setText("");
			}
			list.add(cn);
		}
		return list;
	}
	
	@Override
	public Elements getCatalogElements() {
		Document doc = getDocument(filePath,encoding);
		Elements p_elements= doc.select("body > div > p,body > div > div");
		System.out.println(p_elements);
		return p_elements;
	}

	@Override
	public int checkClassValue(Element e) {
		return 0;
	}
	
	
	
}
