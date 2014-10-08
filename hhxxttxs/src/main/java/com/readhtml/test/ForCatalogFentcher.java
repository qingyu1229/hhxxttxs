package com.readhtml.test;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ForCatalogFentcher extends CatalogBasicFentcher {

	private String filePath;
	private String encoding;

	public ForCatalogFentcher(String filePath, String encoding) {
		this.filePath = filePath;
		this.encoding = encoding;
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
		list.add(cn0);
		map.put(0, cn0);
		for (int i = 1; i < elements.size(); i++) {
			Element element = elements.get(i);
			int num = checkClassValue(element);
			CatalogNode cn = new CatalogNode();
			cn.setId(i);
			cn.setText(cleanoutNodeValue(element.text()));
			if (last_num < num) {
				cn.setP_id(i - 1);
				cn.setOrder(1);
			} else if (last_num == num) {
				cn.setP_id(map.get(i - 1).getP_id());
				cn.setOrder(map.get(i - 1).getOrder() + 1);
			} else if (last_num > num) {
				cn.setP_id(map.get(map.get(i - 1).getP_id()).getP_id());
				cn.setOrder(map.get(map.get(i - 1).getP_id()).getOrder() + 1);
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
		Document doc = getDocument(filePath,encoding);
		doc.select("p.MsoTocHeading").remove();
		elements = doc.body().getElementsByAttributeValueStarting("class",
				"MsoToc");
		return elements;
	}

	/**
	 * 获取节点权值
	 */
	@Override
	public int checkClassValue(Element element) {
		String classValue=element.attr("class");
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
