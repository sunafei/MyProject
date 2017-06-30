package com.sun.tag.theme;

public abstract class AbsBody extends AbsHtml {
	
	@Override
	@SuppressWarnings("unchecked")
	public AbsBody setInnerHTML(String innerHTML) {
		super.setInnerHTML(innerHTML);
		
		return this;
	}
}
