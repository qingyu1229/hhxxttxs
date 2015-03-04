package com.jsoup;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CheckUrl {

	public static void main(String[] args) throws IOException {
		
		FileInputStream in=new FileInputStream("D:\\test.xlsx");
		System.out.println(in==null);
		XSSFWorkbook wb = new XSSFWorkbook(in);
		System.out.println("==");
		XSSFSheet sheet = wb.getSheetAt(0);
		Iterator<Row> iterator = sheet.rowIterator();
		while (iterator.hasNext()) {
			Row row = iterator.next();
			Cell cell = row.getCell(2);
			String str = cell.getStringCellValue();
			System.out.println(str);
		}
	}
}
