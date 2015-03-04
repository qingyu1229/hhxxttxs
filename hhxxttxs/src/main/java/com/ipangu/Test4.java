package com.ipangu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test4 {
	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File("e:\\1112832.xls"));
		HSSFWorkbook wfb = new HSSFWorkbook();
		HSSFSheet sheet1 = wfb.createSheet("s1");

		FileInputStream in = new FileInputStream("e:\\11.xls");
		HSSFWorkbook wb = new HSSFWorkbook(in);
		HSSFSheet sheet = wb.getSheetAt(0);
		int totalRows = sheet.getLastRowNum();
		for (int i = 383; i < totalRows; i++) {
			String str = sheet.getRow(i).getCell(0).getStringCellValue();
			System.out.println(i);
			InfoFetcher fetcher = new InfoFetcher();
			try {
				String js = fetcher.fetch(str);
				JSONObject json = new JSONObject(js);
				JSONObject data = json.getJSONObject("data");
				JSONArray array = data.getJSONArray("dataList");
				sheet1.createRow(i);
				sheet1.getRow(i).createCell(0).setCellValue(str);
				for (int m = 0; m < array.length(); m++) {
					JSONObject info = (JSONObject) array.get(m);
					JSONArray contactList = info.getJSONArray("contactList");
					if (contactList != null) {
						for (int j = 0; j < contactList.length(); j++) {
							JSONObject phones = (JSONObject) contactList.get(j);
							String name = String.valueOf(phones.get("name"));
							sheet1.getRow(i).createCell(1 + (j) * 2)
									.setCellValue(name);
							String phone = String.valueOf(phones.get("phoneList"));
							sheet1.getRow(i).createCell(2 + (j) * 2).setCellValue(phone);
							 System.out.println(name+":"+phone);
						}
					}
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				wfb.write(fos);
				fos.flush();
				fos.close();
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				wfb.write(fos);
				fos.flush();
				fos.close();
				e.printStackTrace();
			}
			
			// sheet1.createRow(i-1).createCell(0).setCellValue(str);

			// System.out.println(str);
		}
		wfb.write(fos);
		fos.flush();
		fos.close();
		

	}
}
