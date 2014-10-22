package com.liang.selector;

import java.util.Map;

public interface Selector {
	
	public String select(String selectText);
	
	public Map<String,String> select(Map<String,String> map);
}
