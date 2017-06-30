package com.sun.util.excel;

public interface ICellConvert {
	public <T> T convert(Object o,Class<T> cls);
}
