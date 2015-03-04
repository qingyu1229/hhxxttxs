package com.httpclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class CheckUrl {

	public static void main(String[] args) throws IOException {
		FileOutputStream fos=new FileOutputStream(	new File("e:\\11.xlsx"));
		XSSFWorkbook wfb = new XSSFWorkbook();
		XSSFSheet sheet1= wfb.createSheet("s1");
		int count=0;
		FileInputStream in = new FileInputStream("D:\\test.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(in);
		// XSSFSheet sheet = wb.getSheetAt(0);
		
		XSSFSheet sheet = wb.getSheetAt(0);
		int totalRows = sheet.getLastRowNum(); 
		for(int i=1;i<totalRows;i++){
			String str= sheet.getRow(i).getCell(2).getStringCellValue();
			System.out.println("访问URL:"+str);
			boolean bl= checkUrl(str);
			if(!bl){
				System.out.println("访问出错："+str);
				sheet1.createRow(count++).createCell(0).setCellValue(str);
			}
		}
		wfb.write(fos);
		fos.flush();
		fos.close();
		wfb.close();
	}
	
	public static boolean checkUrl(String url){
		try {
			Document doc= Jsoup.connect(url).timeout(5000).get();
			if(doc!=null){
				return true;
			}
		} catch (IOException e) {
			return false;
			//e.printStackTrace();
		}
		return false;
	}
	
/*	
	public static void main(String[] args) throws IOException{
		FileOutputStream fos=new FileOutputStream(	new File("D:\\test2.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet= wb.createSheet("s1");
		sheet.createRow(0).createCell(0).setCellValue("ddddddddddddddddddddddd");
		wb.write(fos);
		fos.flush();
		fos.close();
		wb.close();
	}*/
	

}
