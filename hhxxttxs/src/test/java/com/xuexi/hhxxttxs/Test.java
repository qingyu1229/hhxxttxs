package com.xuexi.hhxxttxs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Test {
	public final static void test(String s) {
		try {
			byte[] b= FileUtils.readFileToByteArray(new File(s));
			
			FileUtils.writeByteArrayToFile(new File("c:/test.xls"), b);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		test("d:/aa.rar");
	}
}
