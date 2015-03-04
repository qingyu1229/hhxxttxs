package com.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetHtmlByJsoup {
	
	
	
	public static void main(String[] args) {
		try {
		/*	Document doc= Jsoup.connect("http://www.hlj.gov.cn/wjfg/szfdsj/index.shtml").get();
			Elements elements= doc.select("td.f14black>table>tbody>tr>td>ul>a");
			System.out.println(elements);*/
			
			String sss="<div class='dd ll'>ddddddddd</div>";
			
		   Document dd=	Jsoup.parse(sss);
			System.out.println(dd.select("div.dd.ll"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
