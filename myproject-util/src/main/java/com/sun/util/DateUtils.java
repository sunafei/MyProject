package com.sun.util;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 日期处理工具
 * @author firefly
 * @version 1
 */
public class DateUtils {
	private static final SimpleDateFormat sdf=new SimpleDateFormat();

	/**
	 * 将字符串转换为日期对象
	 * @param str 字符串
	 * @param formatStr 提供的日期格式数组
	 * @return java.sql.Date
	 * @throws ParseException 
	 */
	public static Date paress(String str,String ... formatStr) throws ParseException{
		for (int i = 0; i < formatStr.length; i++) {
			sdf.applyPattern(formatStr[i]);
			try {
				return sdf.parse(str);
			} catch (ParseException e) {}
		}
		throw new ParseException("could not parse str ["+str+"] formatStr ["+formatStr+"]", 0);
	}

	/**
	 * 将日期对象转换为字符串
	 * @param str 字符串
	 * @param formatStr 提供的日期格式数组
	 * @return
	 * @throws ParseException 
	 */
	public static String format(Date date,String ... formatStr) throws ParseException{
		String checkStr=null;
		for (int i = 0; i < formatStr.length; i++) {
			sdf.applyPattern(formatStr[i]);
			checkStr=sdf.format(date);
			try {
				if(sdf.parse(checkStr)!=null){
					return checkStr;
				}
			} catch (ParseException e) {}
		}
		throw new ParseException("could not parse date ["+date+"] formatStr ["+formatStr+"]", 0);
	}
}
