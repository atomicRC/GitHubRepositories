package com.tongxs.common;

public class StringUtils {
	
	public static long[] toLongArray(String[] StringArray){
		long[] longArray = null;
		try{
			if(StringArray != null){
	        longArray = new long[StringArray.length];
	        for (int idx = 0; idx < StringArray.length; idx++) {
	        	Long tmp = Long.parseLong(StringArray[idx]);
	        	longArray[idx] = tmp.longValue();
	        }

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return longArray;
	}
}
