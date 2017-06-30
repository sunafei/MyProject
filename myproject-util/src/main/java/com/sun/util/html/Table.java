package com.sun.util.html;


public class Table extends AbsTag {
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < getChildtags().size(); ++i) {
			sb.append(((Tag) getChildtags().get(i)).toString());
		}
		return "<table " + getAttributesString() + ">" + sb.toString() + getText() + "</table>";
	}
}