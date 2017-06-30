package com.sun.util.html;

public abstract class AbsCell extends AbsTag {
	protected int colspan = 1;
	protected int rowspan = 1;

	public int getColspan() {
		return this.colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public int getRowspan() {
		return this.rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	protected String getAttributesString() {
		StringBuffer sb = new StringBuffer(super.getAttributesString());
		if (this.colspan > 1)
			sb.append("colSpan=\"" + this.colspan + "\" ");
		if (this.rowspan > 1)
			sb.append("rowspan=\"" + this.rowspan + "\" ");
		return sb.toString();
	}

	public abstract String toString();
}