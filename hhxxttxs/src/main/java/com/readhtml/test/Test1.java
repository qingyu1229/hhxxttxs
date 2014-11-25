package com.readhtml.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Test1 {

	@Test
	public void test() {
		Document doc = Jsoup
				.parse("<table><tbody><tr><td>dd</td><td>dd</td></tr></tbody></table><table><tbody><tr><td></td><td></td></tr></tbody></table>");
		Elements tables = doc.getElementsByTag("table");
		if (tables.size() > 0) {
			for (Element e : tables) {
				if ("".equals(e.text())) {
					e.remove();// 移除这个table
					System.out.println();
				}
			}
		}
	}

}
