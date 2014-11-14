package com.readhtml.test2;

import java.util.List;

public interface CatalogFentcher {
	
	public List<CatalogNode> getCatalog(String filePath,String encoding);
}
