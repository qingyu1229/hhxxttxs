package com.html.path;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;

public class GetPath {
	
	 static String text="华社电美国总统奥巴马当地时间9日启程赴北京";
	
	public static void main(String[] args) {
		String url="http://world.huanqiu.com/article/2014-11/5197815.html";
		
		Document doc= fentch(url);
		/*StringBuffer accum=new StringBuffer();
		StringBuffer path= checkTextPath(doc.body(),accum);
		System.out.println(path);*/
		
		Element body_element = doc.body();
		
		/**
		 * 网页去噪
		 */
		body_element.getElementsByTag("script").remove();
		body_element.getElementsByTag("style").remove();
		body_element.getElementsByTag("link").remove();
		body_element.getElementsByTag("img").remove();
		body_element.getElementsByTag("input").remove();
		body_element.getElementsByTag("object").remove();
		body_element.getElementsByTag("textarea").remove();
		body_element.getElementsByTag("a").attr("href", "javascript:void(0)").remove();
		body_element.getElementsByAttributeValue("display", "none").remove();
		body_element.getElementsByAttributeValueContaining("style", "display:none");
		body_element.getElementsByAttributeValueContaining("style", "overflow: hidden");
		
		
		doSecoreToElement(body_element);
		StringBuffer strAccum=new StringBuffer();
		StringBuffer path=  checkContentPath(body_element,strAccum);
		System.out.println(path);
		//System.out.println(body_element);
	}
	
	public static Document fentch(String url){
		Document doc=null;
		try {
			doc=Jsoup.connect(url).timeout(3000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	
	public static StringBuffer checkTextPath(Element element,StringBuffer path){
		Elements childrens= element.children();	
		//遍历所有子节点
		for(Element child:childrens){
			//如果节点中包含字符
			if(child.hasText()){
				//如果包含目标字符串
				if(child.text().contains(text)){
					String tagName=child.tagName();
					boolean hasIdAttr=child.hasAttr("id");//是否含有id属性
					boolean hashClassAttr=child.hasAttr("class");//是否含有class属性
					path.append(tagName);
					if(hasIdAttr){//优先考虑id属性
						path.append("#"+child.attr("id"));
					}else if(hashClassAttr){//如果包含class属性
						String classAttr=child.attr("class").trim().replace(" ", ".");
						path.append("."+classAttr);
					}
					path.append(">");
					return checkTextPath(child,path);
				}
			}
		}
		return 	path;
	}
	
	public static int doSecoreToElement(Element element){
		boolean hasText=element.hasText();
		
		if(!hasText){//如果节点没有内容
			element.attr("secore", "0");
			return 0;
		}else{
			Elements children=element.children();
			if(children.size()==0){//不含有子节点
				return Rate.doRate(element);
			}else{//含有子节点
				int accum=0;
				for(Element child:children){
					accum+=doSecoreToElement(child);
				}
				element.attr("secore", String.valueOf(accum));
				return accum;
			}
		}
	}
	
	public static StringBuffer checkContentPath(Element element,StringBuffer StrAccum){
		Element maxSecoreElement=getMaxSecoreChild(element);
		if(maxSecoreElement==null){
			return StrAccum;
		}else{
		String tagName=maxSecoreElement.tagName();
		boolean hasIdAttr=maxSecoreElement.hasAttr("id");//是否含有id属性
		boolean hashClassAttr=maxSecoreElement.hasAttr("class");//是否含有class属性
		StrAccum.append(tagName);
		if(hasIdAttr){//优先考虑id属性
			StrAccum.append("#"+maxSecoreElement.attr("id"));
		}else if(hashClassAttr){//如果包含class属性
			String classAttr=maxSecoreElement.attr("class").trim().replace(" ", ".");
			StrAccum.append("."+classAttr);
		}
		StrAccum.append(">");
		return checkContentPath(maxSecoreElement,StrAccum);
		}
		
	}
	
	public static Element getMaxSecoreChild(Element element){
		if(element.childNodeSize()==0){
			return null;
		}
		Elements children=element.children();
		Element maxSecoreElement=null;
		int secore=0;
		for(Element e:children){
		    String strSecore=e.attr("secore");
		    if(strSecore==null){
		    	continue;
		    }
		    
		    if(Integer.valueOf(strSecore)>secore){
		    	maxSecoreElement=e;
		    	secore=Integer.valueOf(strSecore);
		    }
		}
		return maxSecoreElement;
	}
	
}








