 package com.sun.util.html;
 
  
 public class TR extends AbsTag
 {
   public String toString()
   {
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < getChildtags().size(); ++i) {
       sb.append(((Tag)getChildtags().get(i)).toString());
     }
     return "<tr " + getAttributesString() + ">" + sb.toString() + getText() + "</tr>";
   }
 }