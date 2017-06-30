package com.sun.util.html;

import java.util.List;
import java.util.Map;

public abstract interface Tag {
	public abstract String getId();

	public abstract void setId(String paramString);

	public abstract String getText();

	public abstract void setText(String paramString);

	public abstract Map<String, String> getAttributes();

	public abstract void setAttributes(Map<String, String> paramMap);

	public abstract void setAttributes(String paramString1, String paramString2);

	public abstract String toString();

	public abstract List<Tag> getChildtags();

	public abstract void addChildtag(Tag paramTag);

	public abstract <T extends Tag> T getBeforeTag(Class<T> paramClass);

	public abstract <T extends Tag> T getNextTag(Class<T> paramClass);

	public abstract void setBeforeTag(Tag paramTag);

	public abstract void setNextTag(Tag paramTag);
}