package com.sun.util.json;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/** 
* JSON 日期格式处理（java转化为JSON） 
* @author firefly
*/  
public class JsonDateValueProcessor implements JsonValueProcessor {  
 
   /** 
    * datePattern 
    */  
   private String datePattern = "yyyy-MM-dd";  
 
   /** 
    * JsonDateValueProcessor 
    */  
   public JsonDateValueProcessor() {  
       super();  
   }  
 
   /** 
    * @param format 
    */  
   public JsonDateValueProcessor(String format) {  
       super();  
       this.datePattern = format;  
   }  
 
   /** 
    * @param value 
    * @param jsonConfig 
    * @return Object 
    */  
   public Object processArrayValue(Object value, JsonConfig jsonConfig) {  
       return process(value);  
   }  
 
   /** 
    * @param key 
    * @param value 
    * @param jsonConfig 
    * @return Object 
    */  
   public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {  
       return process(value);  
   }  
 
   /** 
    * process 
    * @param value 
    * @return 
    */  
   private Object process(Object value) {  
       try {  
           if (value instanceof Date || value instanceof java.sql.Date) {  
               SimpleDateFormat sdf = new SimpleDateFormat(datePattern,Locale.CHINA);  
               return sdf.format((Date) value);  
           }
           return value == null ? "" : value.toString();  
       } catch (Exception e) {  
           return "";  
       }  
 
   }  
 
   /** 
    * @return the datePattern 
    */  
   public String getDatePattern() {  
       return datePattern;  
   }  
 
   /** 
    * @param pDatePattern the datePattern to set 
    */  
   public void setDatePattern(String pDatePattern) {  
       datePattern = pDatePattern;  
   }  
 
}  