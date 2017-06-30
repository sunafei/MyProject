 package com.sun.util.html;
 
 public class TD extends AbsCell
 {
   public String toString()
   {
     return "<td " + getAttributesString() + ">" + getText() + "</td>";
   }
 }