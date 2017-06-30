package com.sun.util.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbsTag implements Tag {
	protected String id = "";
	protected String text = "";
	protected Map<String, String> attributes = new HashMap();
	protected List<Tag> childtags = new ArrayList();
	private Tag beforeTag;
	private Tag nextTag;

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void setAttributes(String key, String value) {
		this.attributes.put(key, value);
	}

	protected String getAttributesString() {
		StringBuffer sb = new StringBuffer();
		Set keys = this.attributes.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			sb.append(key + "=\"" + ((String) this.attributes.get(key)) + "\" ");
		}
		return sb.toString();
	}

	public abstract String toString();

	public List<Tag> getChildtags() {
		return this.childtags;
	}

	public void addChildtag(int index, Tag childtag) {
		if (!(this.childtags.isEmpty())) {
			((Tag) this.childtags.get(index)).setBeforeTag(childtag);
		}
		this.childtags.add(index, childtag);
	}

	public void addChildtag(Tag childtag) {
		if (!(this.childtags.isEmpty())) {
			((Tag) this.childtags.get(this.childtags.size() - 1)).setNextTag(childtag);
		}
		this.childtags.add(childtag);
	}

	public Tag getChildtagById(String id) {
		for (Iterator iterator = this.childtags.iterator(); iterator.hasNext();) {
			Tag tag = (Tag) iterator.next();
			if (tag.getId().equals(id))
				return tag;
		}
		return null;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public <T extends Tag> T getBeforeTag(Class<T> cls) {
		return ((T) cls.cast(this.beforeTag));
	}

	public void setBeforeTag(Tag t) {
		this.beforeTag = t;
		((AbsTag) super.getClass().cast(t)).nextTag = this;
	}

	public <T extends Tag> T getNextTag(Class<T> cls) {
		return ((T) cls.cast(this.nextTag));
	}

	public void setNextTag(Tag t) {
		this.nextTag = t;
		((AbsTag) super.getClass().cast(t)).beforeTag = this;
	}
}