package com.html.selector;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class FilterLocator {
	private static FilterLocator filterLocator=new FilterLocator();
	HashMap<String, Filter> filters = new HashMap<String, Filter>();
	
	public Filter getFilter(String url) {
		String host=getHost(url);
		if(filters.containsKey(host)) {
			return filters.get(host);
		}
		return null;
	}
	
	public void register(String host, Filter filter) {
		filters.put(host, filter);
	}

	public void unregister(String host) {
		filters.remove(host);
	}
	
	private FilterLocator(){}
	
	public static FilterLocator getInstance(){
		return filterLocator;
	}
	
	private  String getHost(String url) {
		String host = "";
		try {
			URL u = new URL(url);
			host = u.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return host;
	}
	
	
}
