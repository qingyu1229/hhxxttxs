package com.getBaiKe;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetBaiKe {
	
	private static String keyWrodsFilePath="d:/keyWords.txt";
	private static String encode="gbk";
	private static String resultFilePath="d:/result.txt";
	
	public static void main(String[] args) {
		File file=new File(resultFilePath);
		List<String> list=getKeyWordsFromFile();
		for(String key:list){
			String tag= fetchFromBaike(key.trim());
			
			System.out.println(key+"=============================="+tag);
			writeToFile(key+" : "+tag+"\n",file);
		}
		
		/*new GetBaiKe().fetchFromBaike("纺织业");
		new GetBaiKe().fetchFromBaike("塑料制品业");
		new GetBaiKe().fetchFromBaike("黑色金属冶炼及压延加工业");
		new GetBaiKe().	fetchFromBaike("机械");
		new GetBaiKe().fetchFromBaike("家电");*/
		
	}

	private static  List<String> getKeyWordsFromFile(){
		File keyWordsFile=new File(keyWrodsFilePath);
		List<String> keyWords=new ArrayList<String>();
		
		try {
			keyWords=FileUtils.readLines(keyWordsFile ,encode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keyWords;
	}
	
	private static  String fetchFromBaike(String word){
		StringBuffer accumulator=new StringBuffer("http://baike.baidu.com/search/word?word=");
		String url=accumulator.append(word).append("&pic=1&sug=1").toString();
		System.out.println("fetch:"+word);
		try {
			/*try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			Document doc= Jsoup.connect(url).followRedirects(true).timeout(5000).get();
			if(doc==null){
				return "没有对应词条";
			}
			//System.out.println(doc);
			Elements tagElements= doc.select("dd#open-tag-item");
			
			if(tagElements==null||tagElements.size()==0){
				return "没有对应标签";
			}else{
				return tagElements.first().text();
			}
		} catch (IOException e) {
			System.out.println("fetch出错："+word);
			e.printStackTrace();
			return "error";
		}
	}
	
	private static  void writeToFile(String tag,File file){
		try {
			FileUtils.write(file, tag, true);
		} catch (IOException e) {
			System.out.println("write出错："+tag);
			e.printStackTrace();
		}
	}
}
