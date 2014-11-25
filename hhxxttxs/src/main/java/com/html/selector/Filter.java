package com.html.selector;

public interface Filter {
	
	public void onBefore();//从needlessElements中获取需要移除的Elements
	
	
	public void onExit();//退出前从needlessChars中移除多余的字符串
}
