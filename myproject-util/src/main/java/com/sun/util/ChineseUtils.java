package com.sun.util;

import org.apache.commons.lang.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ChineseUtils {
	/**
	 * 获得汉字拼音
	 * @param name
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getPiYin(String src) {  
        HanyuPinyinOutputFormat outFormat = new HanyuPinyinOutputFormat();  
        outFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        outFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        outFormat.setVCharType(HanyuPinyinVCharType.WITH_V);  
        try {  
            return PinyinHelper.toHanyuPinyinString(src, outFormat, "");  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
            e1.printStackTrace();  
            return src;  
        }  
    } 
	
	/**
	 * 获得拼音首字母
	 * @param name
	 * @return
	 */
	public static String getPinYinFirstLetter(String name){
		char[] str=StringUtils.defaultIfEmpty(name, "").toCharArray();
		String shouZiMu="";
		for ( char string : str) {
			shouZiMu+=getPiYin(String.valueOf(string)).charAt(0);
		}
		return shouZiMu;
	}
	
	public boolean isChinese(char c) {    
       Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);    
       if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS    
               || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS    
               || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A    
               || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION    
               || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION    
               || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {    
           return true;    
       }    
       return false; 
	}
	
	public boolean isChinese(String s) {    
		return s.matches("[\\u4E00-\\u9FA5]+");
	}
}
