package com.sun.util.html;

public class TH extends AbsCell {
	public String toString() {
		return "<th " + getAttributesString() + ">" + getText() + "</th>";
	}
}