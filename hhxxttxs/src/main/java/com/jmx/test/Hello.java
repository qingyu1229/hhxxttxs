package com.jmx.test;

public class Hello implements HelloMBean {

	
	private int private_v=100;
	public int public_v=100;
	public void sayHello() {  
	    System.out.println("hello, world");  
	    }  
	  
	    public int add(int x, int y) {  
	    return x + y;  
	    }  
	  
	    public String getName() {  
	    return this.name;  
	    }  
	  
	     public int getCacheSize() {  
	    return this.cacheSize;  
	    }  
	  
	     public int getv(){
	    	 return private_v;
	     }
	     
	    public synchronized void setCacheSize(int size) {  
	    this.cacheSize = size;  
	  
	    System.out.println("Cache size now " + this.cacheSize);  
	    }  
	  
	    private final String name = "liang";  
	    private int cacheSize = DEFAULT_CACHE_SIZE;  
	    private static final int DEFAULT_CACHE_SIZE = 200;  
	
	
}
