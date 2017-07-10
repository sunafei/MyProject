package com.sun.plugin.aspose;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据格式化工具类
 * 
 */
public class DataFormat {
	private static final Logger LOG = LoggerFactory.getLogger(DataFormat.class);
	private DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
	private DecimalFormat floatFormat = new DecimalFormat("0.00");
	
	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = new SimpleDateFormat(dateFormat);
	}

	public DecimalFormat getFloatFormat() {
		return floatFormat;
	}

	public void setFloatFormat(DecimalFormat floatFormat) {
		this.floatFormat = floatFormat;
	}

	public void setFloatFormat(String floatFormat) {
		this.floatFormat = new DecimalFormat(floatFormat);
	}

	/**
	 * 转换
	 * 
	 * @param o
	 *            要转换的对象
	 * @return
	 */
	public String toString(Object o) {
		String s = "";
		if (o == null) {
			s = "";
		} else if (o instanceof Date) {
			s = dateFormat.format(o);
		} else if (o instanceof Float) {
			s = floatFormat.format(o);
		} else {
			s = o.toString();
		}
		LOG.debug("转换结果为{}", s);
		return s;
	}
}
