package com.jmx.test;

import java.util.ArrayList;
import java.util.List;

public class Persion implements PersionMBean {

	private String name="liang";
	//private int age=20;
	List<String> list=new ArrayList<String>();
	
	@Override
	public String getName() {
		System.out.println("Name:"+this.name);
		return this.name;
	}

	@Override
	public void setName(String name) {
		System.out.println("I get a name:"+name);
		this.name=name;
	}

	@Override
	public int getAge() {
		//System.out.println("Age:"+this.age);
		list.add("..");
		return list.size();
	}

	

}
