package com.ycyl.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 校验类
 * @author 梅波
 *
 */
public class CheckUtil {
	
	/**
	 * 检查签名是否匹配
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature,
			String timestamp,String nonce){
		String[] arr = new String[]{Constants.TOKEN,timestamp,nonce};
		
		//排序,将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		
		//生成字符串
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<arr.length;i++){
			sb.append(arr[i]);
		}
		
		//sha1加密
		String temp = getSha1(sb.toString());
		
		//返回和微信签名的比较的结果
		if (temp != null) {
			return temp.equals(signature);
		} else {
			return false;
		}
	}
	
	/**
	 * sha1加密
	 * @param str
	 * @return
	 */
	public static String getSha1(String str){
	    if (null == str || 0 == str.length()){
	        return null;
	    }
	    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	            'a', 'b', 'c', 'd', 'e', 'f'};
	    try {
	        MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
	        mdTemp.update(str.getBytes("UTF-8"));
	         
	        byte[] md = mdTemp.digest();
	        int j = md.length;
	        char[] buf = new char[j * 2];
	        int k = 0;
	        for (int i = 0; i < j; i++) {
	            byte byte0 = md[i];
	            buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
	            buf[k++] = hexDigits[byte0 & 0xf];
	        }
	        return new String(buf);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
