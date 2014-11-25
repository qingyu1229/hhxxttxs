package com.readhtml.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ForCatalogFentcher extends CatalogBasicFentcher {

	private String filePath;
	private String encoding;
	Map<Integer, String> contentMap;

	public ForCatalogFentcher(String filePath, String encoding) {
		this.filePath = filePath;
		this.encoding = encoding;
		contentMap = new HashMap<Integer, String>();
	}

	@Override
	public List<CatalogNode> getCatalog() {
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
				cn.setP_id(getPIdByDepth(last_num - num, map.get(i - 1).getId()));
				cn.setOrder(getOrderByDepth(last_num - num, map.get(i - 1)
						.getId()));
			}

			map.put(i, cn);
			list.add(cn);
			last_num = num;
		}
		return list;
	}

	/**
	 * 得到目录的节点集合
	 */
	public Elements getCatalogElements() {
		Elements elements = null;
		Document doc = getDocument(filePath, encoding);
		if (doc.select("p.MsoTocHeading") != null) {
			doc.select("p.MsoTocHeading").remove();
		}

		elements = doc.body().getElementsByAttributeValueStarting("class",
				"MsoToc");

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
	 * 获取节点权值
	 */
	@Override
	public int checkClassValue(Element element) {
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
	}

}
