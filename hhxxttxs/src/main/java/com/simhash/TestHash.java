package com.simhash;

import java.math.BigInteger;

public class TestHash {
	
	
	
	public static int hashbits = 128;
	 
	 
	 public static void main(String[] args) {
		 String s="中国";
		 BigInteger i=hash(s);
		 System.out.println(i);
		 
	}

	 public static BigInteger hash(String source) {
	        if (source == null || source.length() == 0) {
	            return new BigInteger("0");
	        } else {
	            char[] sourceArray = source.toCharArray();
	            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
	            BigInteger m = new BigInteger("1000003");
	            BigInteger mask = new BigInteger("2").pow(hashbits).subtract(
	                    new BigInteger("1"));
	            for (char item : sourceArray) {
	                BigInteger temp = BigInteger.valueOf((long) item);
	                x = x.multiply(m).xor(temp).and(mask);
	            }
	            x = x.xor(new BigInteger(String.valueOf(source.length())));
	            if (x.equals(new BigInteger("-1"))) {
	                x = new BigInteger("-2");
	            }
	            return x;
	        }
	    }
}
