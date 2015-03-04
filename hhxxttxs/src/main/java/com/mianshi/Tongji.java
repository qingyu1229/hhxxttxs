package com.mianshi;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Tongji {
	
	
	public static void main(String[] args) {
		String path="F:\\test-image\\20150303\\";
		File file=new File(path);
		System.out.println(file.isFile());
		System.out.println(file.isDirectory());
		File[] imgFiles= file.listFiles();
		Long size=0L;
		
		Long[] sizes=new Long[10];
		Long max=0L;
		Long min=1425317674142L;
		
		Map<Integer,Long> map=new HashMap<Integer,Long>();
		
		
		
		for(File f:imgFiles){
			System.out.println(f.lastModified());
			Long lastModify=f.lastModified();
			/*if(f.lastModified()>max){
				max=f.lastModified();
			}
			if(f.lastModified()<min){
				min=f.lastModified();
			}*/
			
			for(int i=1;i<=48;i++){
				if(lastModify<=(1425312019554L+i*1797949)){
					
					Long s=map.get(i)==null?0: map.get(i)+f.length();
					map.put(i, s);
				}
			}
			
			//size+=f.length();
			/*for(int i=0;i<10;i++){
				if(sizes[i]==null||f.length()>sizes[i]){
					sizes[i]=f.length();
					break;
				}
			}*/
			//System.out.println(f.length());
		}
		Long maxSize=0L;
		
		for(Integer set:map.keySet()){
			System.out.println("=====================key:"+set);
			if(map.get(set)>maxSize){
				maxSize=map.get(set);
			}
			System.out.println(map.get(set));
		}
		
		System.out.println("maxSize:"+maxSize);
		System.out.println(maxSize/(60*30));
		//1797949
		
	}

}
