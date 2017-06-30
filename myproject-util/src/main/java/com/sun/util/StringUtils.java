package com.sun.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串操作工具类
 * @author firefly
 * @version 3.0
 */
public abstract class StringUtils {
	/** 常量 * */

	/** 常量空 */
	public static final String EMPTY = "";

	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	/** 以下是对字符的处理 * */

	/**
	 * 转换对象为字符串
	 * @param val
	 * @return
	 */
	public static String parseString(Object val){
		if(val==null)return EMPTY;
		if(String.class.isAssignableFrom(val.getClass())){
			return String.class.cast(val);
		}else if(Date.class.isAssignableFrom(val.getClass())){
			return sdf.format(Date.class.cast(val));
		}else if(Float.class.isAssignableFrom(val.getClass())){
			return BigDecimal.valueOf(Float.class.cast(val)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
		}else if(Double.class.isAssignableFrom(val.getClass())){
			return BigDecimal.valueOf(Double.class.cast(val)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
		}else{
			return val.toString();
		}
	}
	
	/**
	 * 比较一个值是否存在与另外一个数组中
	 * @param a
	 * @param val
	 * @return
	 */
	public static boolean equalsIgnoreCaseIn(String a,String ... val){
		for (String v : val) {
			if(org.apache.commons.lang.StringUtils.equalsIgnoreCase(a, v))return true;
		}
		return false;
	}
	
	/** 
     * 将一个字符串的首字母改为大写或者小写 
     * 
     * @param srcString 源字符串 
     * @param flag            大小写标识，ture小写，false大写
     * @return 改写后的新字符串 
     */ 
    public static String toLowerCaseInitial(String srcString, boolean flag) { 
            StringBuilder sb = new StringBuilder(); 
            if (flag) { 
                    sb.append(Character.toLowerCase(srcString.charAt(0))); 
            } else { 
                    sb.append(Character.toUpperCase(srcString.charAt(0))); 
            } 
            sb.append(srcString.substring(1)); 
            return sb.toString(); 
    } 

	/**
	 * 以2进制形式截取字符串 如果断位上正好是汉字则保留
	 * 
	 * @param str
	 *            原字符串
	 * @param len
	 *            需要截取的长度(字节长)
	 * @param more
	 *            被截取后所加的后缀
	 * @return
	 */
//	public static String subStrByBinary(String str, int len, String more) {
//		int reInt = 0;
//		String reStr = "";
//		if (isBlank(str)) {
//			return EMPTY;
//		}
//		char[] tempChr = str.toCharArray();
//		int i = 0;
//		for (; (i < tempChr.length && reInt < len); i++) {
//			String iStr = String.valueOf(tempChr[i]);
//			byte[] bytes = iStr.getBytes();
//			reInt += bytes.length;
//			reStr += tempChr[i];
//		}
//		if ((reInt >= len) && i < tempChr.length) {
//			reStr += more;
//		}
//		return reStr;
//	}
//    public static List<String> splitStrByBinary(String s, int length){
//    	List<String> resultStr= new ArrayList<String>();
//    	if(isBlank(s))return resultStr;
//		try {
//			byte[] bytes = s.getBytes("Unicode");
//	        
//			int n = 0; // 表示当前的字节数
//	        int i = 2; // 要截取的字节数，从第3个字节开始
//	        int j = 0;
//	        for (; i < bytes.length; i++){
//	            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
//	            if (i % 2 == 1){
//	                n++; // 在UCS2第二个字节时n加1
//	            }else{
//	                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
//	                if (bytes[i] != 0)
//	                {
//	                    n++;
//	                }
//	            }
//	            if(n > length){
//	    	        if (i % 2 == 1){
//	    	            if (bytes[i - 1] != 0) i = i - 1; 
//	    	            else  i = i + 1;
//	    	        }
//	            	n=0;
//	            	resultStr.add(new String(bytes, j, i-j, "Unicode"));
//	            	j=i;
//	            }
//	        }
//	        if(n>0){
//            	resultStr.add(new String(bytes, j, bytes.length-j, "Unicode"));
//	        }
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//        return resultStr;
//    }
    
	public static String subStrByBinary(String s, int length, String more){
		if(org.apache.commons.lang.StringUtils.isBlank(s))return EMPTY;
		String resultStr= null;
		try {
			byte[] bytes = s.getBytes("Unicode");
	        int n = 0; // 表示当前的字节数
	        int i = 2; // 要截取的字节数，从第3个字节开始
	        for (; i < bytes.length && n < length; i++){
	            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
	            if (i % 2 == 1){
	                n++; // 在UCS2第二个字节时n加1
	            }else{
	                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
	                if (bytes[i] != 0)
	                {
	                    n++;
	                }
	            }
	        }
	        // 如果i为奇数时，处理成偶数
	        if (i % 2 == 1){
	            // 该UCS2字符是汉字时，去掉这个截一半的汉字
	            if (bytes[i - 1] != 0)
	                i = i - 1;
	            // 该UCS2字符是字母或数字，则保留该字符
	            else
	                i = i + 1;
	        }
	        resultStr = new String(bytes, 0, i, "Unicode");
	        if(!org.apache.commons.lang.StringUtils.isBlank(more) && s.length()>resultStr.length())resultStr+=more;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return resultStr;
    }
	

	// 以下是针对String数组的操作

	/**
	 * 在字符串数组每个元素前附加字符串
	 */
	public static String[] beforeAppend(String[] strs, String adppendage) {
		if (ArrayUtils.isBlank(strs))
			return strs;
		String[] temp = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			temp[i] = adppendage + strs[i];
		}
		return temp;
	}

	/**
	 * 得到某个信息的摘要
	 * @param info
	 *            需要摘要的信息
	 * @param algorithm
	 *            (算法名,如 SHA-1 或MD5)
	 * @return 信息的摘要
	 */
	public static String generateDigest(String info, String algorithm) {
		String infoDigest = "";
		try {
			// 首先用生成一个MessageDigest类,确定计算方法
			java.security.MessageDigest alga = java.security.MessageDigest.getInstance(algorithm);
			// 添加要进行计算摘要的信息
			alga.update(info.getBytes());
			// 计算摘要
			byte[] digesta = alga.digest();
			// 转换成十六进制字符串
			infoDigest = byte2hex(digesta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoDigest;
	}
	
	/**
	 * 二行制转化成十六进制字符串
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			/*if (n < b.length - 1)
				hs = hs + ":";*/
		}
		return hs.toUpperCase();
	}

	/**
	 * 获得字符字节长度，1个汉字的GBK字节长=2
	 * @param str
	 * @return
	 */
	public static int byteLength(String str){
		return byteLength(str, "GBK");
	}
	/**
	 * 获得字符字节长度，1个汉字的GBK字节长=2，UTF-8字节长=3
	 * @param str
	 * @param charsetName
	 * @return
	 */
	public static int byteLength(String str,String charsetName){
		int byteLen = 0;
		if(str==null)return byteLen;
		try {
			byteLen = str.getBytes(charsetName).length;
		} catch (UnsupportedEncodingException e) {
			byteLen = str.getBytes().length;
		}
		return byteLen;
	}
}