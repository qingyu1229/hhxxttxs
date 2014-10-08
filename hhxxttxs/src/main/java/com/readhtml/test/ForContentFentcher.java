package com.readhtml.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ForContentFentcher extends CatalogBasicFentcher {
	
	private String filePath;
	private String encoding;
	Map<Integer,String> contentMap;
	
	public ForContentFentcher(String filePath, String encoding) {
		this.filePath = filePath;
		this.encoding = encoding;
		contentMap=new HashMap<Integer,String>();
	}

	@Override
	public List<CatalogNode> getCatalog() {
		
		Elements elements=getCatalogElements();
		
		int last_num = 1;
		CatalogNode cn0 = new CatalogNode();
		cn0.setId(0);
		cn0.setText(cleanoutNodeValue(elements.get(0).text()));
		cn0.setP_id(-1);
		cn0.setOrder(1);
		cn0.setContent(contentMap.get(0));
		list.add(cn0);
		map.put(0, cn0);
		for (int i = 1; i < elements.size(); i++) {
			Element element = elements.get(i);
			int num = checkClassValue(element);
			CatalogNode cn = new CatalogNode();
			cn.setId(i);
			cn.setText(cleanoutNodeValue(element.text()));
			cn.setContent(contentMap.get(i));
			if (last_num < num) {
				cn.setP_id(i - 1);
				cn.setOrder(1);
			} else if (last_num == num) {
				cn.setP_id(map.get(i - 1).getP_id());
				cn.setOrder(map.get(i - 1).getOrder() + 1);
			} else if (last_num > num) {
				/*cn.setP_id(map.get(map.get(i - 1).getP_id()).getP_id());
				cn.setOrder(map.get(map.get(i - 1).getP_id()).getOrder() + 1);*/
				
				cn.setP_id(getPIdByDepth(last_num - num,map.get(i - 1).getId()));
				cn.setOrder(getOrderByDepth(last_num - num,map.get(i - 1).getId()));
				
			}
			map.put(i, cn);
			list.add(cn);
			last_num = num;
		}
		return list;
	}
	

	@Override
	public Elements getCatalogElements() {
		Document doc = getDocument(filePath,encoding);
		Elements elements=doc.body().child(0).children();
		Elements h_elements=new Elements();
		Elements c_elements=new Elements();
		int last_index=0;
		for(int i=0;i<elements.size();i++){
			Element e=elements.get(i);
			String tagName=e.tagName();
			if(("h1".equals(tagName)||"h2".equals(tagName)||"h3".equals(tagName)||"h4".equals(tagName)||"h5".equals(tagName))){
				h_elements.add(e);
				contentMap.put(h_elements.size()-2, c_elements.html());
				c_elements.empty();
				last_index=i;
			}else {
				if(h_elements.size()>0){
					c_elements.add(e.clone());
				}
			}
		}
		return h_elements;
	}



	@Override
	public int checkClassValue(Element element) {
		String tagname=element.tagName();
		if (tagname.matches("h\\d{1,2}")) {
			String numStr = tagname.replace("h", "");
			try {
				return Integer.valueOf(numStr);
			} catch (NumberFormatException e) {
				return -1;
			}
		}
		return -1;
	}
	
	private int getPIdByDepth(int depth,int id){
		if(depth==0){
			return map.get(id).getP_id();
		}else{
			int pid= map.get(id).getP_id();
			return getPIdByDepth(depth-1,pid);
		}
	}
	
	private int getOrderByDepth(int depth,int id){
		if(depth==0){
			return map.get(id).getOrder()+1;
		}else{
			int pid= map.get(id).getP_id();
			return getOrderByDepth(depth-1,pid);
		}
	}

}
