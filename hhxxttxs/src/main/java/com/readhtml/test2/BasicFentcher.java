package com.readhtml.test2;

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


public class BasicFentcher implements CatalogFentcher{

	private String filePath;
	private String encoding;
	private boolean GETCATALOGELEMENTSFROMCATALOG=true;
	
	Map<Integer,String> contentMap=new HashMap<Integer,String>();
	List<CatalogNode> list=new ArrayList<CatalogNode>();
	Map<Integer,CatalogNode> map=new HashMap<Integer,CatalogNode>();
	
	
	public Elements getCatalogElements() {
		Elements elements=getCatalogElementsFromCatalog();
		if(elements==null){
			elements=getCatalogElementsFromContent();
			GETCATALOGELEMENTSFROMCATALOG=false;
		}
		return elements;
	}

	/**
	 * 从内容中抽取目标节点的集合
	 * @return
	 */
	public Elements getCatalogElementsFromContent(){
		Document doc = getDocument(filePath,encoding);
		Elements elements=formatDocumentToElements(doc);
		
		Elements h_elements=new Elements();
		Elements c_elements=new Elements();
		for(int i=0;i<elements.size();i++){
			Element e=elements.get(i);
			String tagName=e.tagName();
			if(("h1".equals(tagName)||"h2".equals(tagName)||"h3".equals(tagName)||"h4".equals(tagName)||"h5".equals(tagName))){
				h_elements.add(e);
				String content=c_elements==null?"":c_elements.toString();
				contentMap.put(h_elements.size()-2, content);
				if(c_elements!=null){
					c_elements.empty();
					c_elements.clear();
				}
			}else {
				if(h_elements.size()>0){
					c_elements.add(e.clone());
				}
			}
		}
		//处理最后一段
		String content=c_elements==null?"":c_elements.toString();
		contentMap.put(h_elements.size()-1, content);
		return h_elements;
	}
	
	/**
	 * 依据目录抽取目标节点的集合
	 * @return
	 */
	public Elements getCatalogElementsFromCatalog(){
		Elements elements = null;
		Document doc = getDocument(filePath, encoding);
		if (doc.select("p.MsoTocHeading") != null) {
			doc.select("p.MsoTocHeading").remove();
		}

		elements = doc.body().getElementsByAttributeValueStarting("class",
				"MsoToc");

		if(elements==null){
			return null;
		}
		Elements c_elements = elements.clone();
		System.out.println(c_elements.size());
		List<String> hrefList = new ArrayList<String>();

		for (int i = 0; i < elements.size(); i++) {
			Elements a_elements = elements.get(i).getElementsByTag("a");
			for (Element e : a_elements) {
				String href = e.attr("href");
				if (href.startsWith("#_Toc")) {
					hrefList.add(href.replace("#", ""));
				}
			}
		}

		Elements p_elements = doc.select("body > div > p,body > div > div,body > div > table");
		// System.out.println("listSize:"+hrefList.size()+"   p_elementsSize:"+p_elements.size());

		int j = 0;
		boolean isCatalog = false;
		Elements temp_elements = new Elements();
		
		for (int i = 0; i < p_elements.size(); i++) {
			Elements pa_elements = p_elements.get(i).getElementsByTag("a");
			if (pa_elements.size() > 0) {

				for (Element e : pa_elements) {
					String name = e.attr("name");
					if (hrefList.get(j).equals(name)) {
						isCatalog = true;
						break;
					}
				}
			}

			if (isCatalog) {
				contentMap.put(j++, temp_elements.toString());
				temp_elements.empty();
				temp_elements.clear();
				isCatalog = false;
				continue;
			} else {
				temp_elements.add(p_elements.get(i));
			}
		}
		String content = c_elements == null ? "" : temp_elements.toString();
		contentMap.put(contentMap.size(), content);
		return c_elements;
	}
	
	
	/**
	 * 依据文件路径和编码将文件封装成DOCUMENT
	 * @param filePath
	 * @param encoding
	 * @return
	 */
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
	
	@Override
	public List<CatalogNode> getCatalog(String filePath, String encoding) {
		
		this.filePath=filePath;
		this.encoding=encoding;
		
		Elements elements = getCatalogElements();

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
			cn.setContent(contentMap.get(i));
			cn.setText(cleanoutNodeValue(element.text()));
			if (last_num < num) {
				cn.setP_id(i - 1);
				cn.setOrder(1);
			} else if (last_num == num) {
				cn.setP_id(map.get(i - 1).getP_id());
				cn.setOrder(map.get(i - 1).getOrder() + 1);
			} else if (last_num > num) {
				cn.setP_id(getPIdByDepth(last_num - num, map.get(i - 1).getId(),map));
				cn.setOrder(getOrderByDepth(last_num - num, map.get(i - 1).getId(),map));
			}
			map.put(i, cn);
			list.add(cn);
			last_num = num;
		}
		return list;
	}
	
	/**
	 * 从目录中抽取时将出现多余的字符...
	 * @param v
	 * @return
	 */
	public String cleanoutNodeValue(String v){
		if(v.contains("...")){
			return v.split("\\...")[0];
		}
		return v;
	}
	
	/**
	 * 根据深度获取当前节点的PID
	 * @param depth
	 * @param id
	 * @param map
	 * @return
	 */
	protected int getPIdByDepth(int depth,int id,Map<Integer,CatalogNode> map){
		
		if(depth==0){
			return map.get(id).getP_id();
		}else{
			int pid= map.get(id).getP_id();
			if(map.get(pid)==null){
				return pid;
			}
			return getPIdByDepth(depth-1,pid,map);
			
		}
	}
	
	/**
	 * 依据深度获取当前节点的顺序
	 * @param depth
	 * @param id
	 * @param map
	 * @return
	 */
	protected int getOrderByDepth(int depth,int id,Map<Integer,CatalogNode> map){
		if(depth==0){
			return map.get(id).getOrder()+1;
		}else{
			int pid= map.get(id).getP_id();
			if(map.get(pid)==null){
				return map.get(id).getOrder();
			}
			return getOrderByDepth(depth-1,pid,map);
		}
	}
	
	/**
	 * 修改图片的路径，将本地路径改为网络路径
	 * @param document
	 */
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
	
	private Elements formatDocumentToElements(Document doc){
		Elements elements=new Elements();
		Elements rootElements=doc.getElementsByAttributeValueStarting("class", "Section");
		
		for(Element e:rootElements){
			elements.addAll(e.children());
		}
		return elements;
	}
	
	private int checkClassValue(Element element){
		if(GETCATALOGELEMENTSFROMCATALOG){
			String classValue = element.attr("class");
			if (classValue.matches("MsoToc\\d{1,2}")) {
				String numStr = classValue.replace("MsoToc", "");
				try {
					return Integer.valueOf(numStr);
				} catch (NumberFormatException e) {
					return -1;
				}
			}
			return -1;
		}else{
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
		
	}
}
