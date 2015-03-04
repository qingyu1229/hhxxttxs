package com.readhtml.test2;

import java.util.List;

import org.jsoup.select.Elements;

public class CwgwzxmrxFentcher extends BasicFentcher{
	
	private String key="财务顾问咨询每日快讯";
	
	public CwgwzxmrxFentcher(){
		FentcherLocator.getInstance().register(key, this);
	}

	@Override
	public List<CatalogNode> getCatalog(String filePath, String encoding) {
		// TODO Auto-generated method stub
		return super.getCatalog(filePath, encoding);
	}

	@Override
	public Elements getCatalogElements() {
		// TODO Auto-generated method stub
		return super.getCatalogElements();
	}
	

	
	
}
