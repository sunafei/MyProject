package com.sun.tag.theme;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * AbsHtml接口
 */
public abstract class AbsHtml {

	public static final String ID_SUFFIX = "_";

	protected Boolean visible;
	protected Boolean disabled;
	protected String innerAbsHtml;
	protected String events;
	protected Map<String, Object> attributes;
	protected String id;
	protected String name;
	protected String cls;
	protected Map<String, String> style;
	protected String title;
	protected String script;
	protected String width;
	protected String height;

	/**
	 * 获取AbsHtml
	 * 
	 * @return
	 */
	public abstract String getHtml();

	/**
	 * 是否可见
	 * 
	 * @return
	 */
	public Boolean isVisible() {
		if (null == visible) {
			return Boolean.TRUE;
		}

		return visible;
	}

	/**
	 * 设置可见性
	 * 
	 * @param visible 是否可见
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T setVisible(Boolean visible) {
		this.visible = visible;

		return (T) this;
	}

	/**
	 * 是否被禁用
	 * <p>
	 * 默认否
	 * 
	 * @return
	 */
	public Boolean isDisabled() {
		if (null == disabled) {
			return Boolean.FALSE;
		}

		return disabled;
	}

	/**
	 * 设置是否禁用元素
	 * 
	 * @param disabled 是否禁用元素
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T setDisabled(Boolean disabled) {
		this.disabled = disabled;

		return (T) this;
	}

	/**
	 * 禁用元素
	 * 
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T disable() {
		this.disabled = Boolean.TRUE;

		return (T) this;
	}

	/**
	 * 启用元素
	 * 
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T enable() {
		this.disabled = Boolean.FALSE;

		return (T) this;
	}

	/**
	 * 获取AbsHtml元素的innerAbsHtml
	 * 
	 * @return
	 */
	public String getInnerHTML() {
		return this.innerAbsHtml;
	}

	/**
	 * 设置AbsHtml元素的innerAbsHtml
	 * 
	 * @param innerAbsHtml innerAbsHtml
	 * @param <T> 泛型
	 * @return not null
	 */
	public <T extends AbsHtml> T setInnerHTML(String innerAbsHtml) {
		this.innerAbsHtml = innerAbsHtml;

		return (T) this;
	}

	/**
	 * 获取事件
	 * 
	 * @return
	 */
	public String getEvents() {
		return this.events;
	}

	/**
	 * 
	 * 设置事件
	 * 
	 * @param events 事件
	 * @param <T> 泛型
	 *            <p>
	 *            例如一个input元素的events是：onclick="a()" onblur="b()"，则会形成：
	 *            <input onclick="a()" onblur="b()" />
	 * @return
	 */
	public <T extends AbsHtml> T setEvents(String events) {
		this.events = events;

		return (T) this;
	}

	/**
	 * 
	 * 添加事件
	 * 
	 * @param <T> 泛型
	 * @param events 事件
	 * @return
	 */
	public <T extends AbsHtml> T addEvents(String events) {
		if (StringUtils.isNotBlank(events)) {
			if (null == this.events) {
				this.events = events;
			} else {
				this.events += (events.indexOf(" ") == 0 ? events : (" " + events));
			}
		}

		return (T) this;
	}

	/**
	 * 获取AbsHtml元素属性
	 * 
	 * @return
	 */
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	/**
	 * 获取AbsHtml元素属性
	 * 
	 * @param name 属性名
	 * @return
	 */
	public Object getAttribute(String name) {
		if (null == this.attributes) {
			return null;
		}

		return this.attributes.get(name);
	}

	/**
	 * 设置AbsHtml元素属性
	 * 
	 * @param attributes 属性集合
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;

		return (T) this;
	}

	/**
	 * 设置AbsHtml元素属性
	 * 
	 * @param name 属性名
	 * @param value 属性值
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T setAttribute(String name, Object value) {
		if (null == this.attributes) {
			this.attributes = new HashMap<String, Object>();
		}
		this.attributes.put(name, value);

		return (T) this;
	}

	/**
	 * 获取AbsHtml元素id
	 * 
	 * @return
	 */
	public String getId() {
		if (StringUtils.isNotBlank(this.id)) {
			return this.id;
		}

		if (StringUtils.isNotBlank(this.getName())) {
			this.setId(this.getName() + ID_SUFFIX);
		}

		return this.id;
	}

	/**
	 * 设置AbsHtml元素id
	 * 
	 * @param id id
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T setId(String id) {
		this.id = id;

		return (T) this;
	}

	/**
	 * 获取AbsHtml元素的name属性
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置AbsHtml元素的name
	 * 
	 * @param name AbsHtml元素的name
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T setName(String name) {
		this.name = name;

		return (T) this;
	}

	/**
	 * 获取AbsHtml元素的class
	 * 
	 * @return
	 */
	public String getCls() {
		return this.cls;
	}

	/**
	 * 设置AbsHtml元素的class
	 * 
	 * @param cls AbsHtml元素的class
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T setCls(String cls) {
		this.cls = cls;

		return (T) this;
	}

	/**
	 * 添加AbsHtml元素的class，不覆盖已有的class
	 * 
	 * @param cls AbsHtml元素的class
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T addCls(String cls) {
		if (StringUtils.isNotBlank(cls)) {
			if (null == this.cls) {
				this.cls = cls;
			} else {
				this.cls += (cls.indexOf(" ") == 0 ? cls : (" " + cls));
			}
		}

		return (T) this;
	}

	/**
	 * 获取AbsHtml元素的样式
	 * 
	 * @return
	 */
	public String getStyle() {
		//if (CollectionUtils.isEmpty(style)) {//org.springframework.util.CollectionUtils
		if (style == null) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		for (Iterator<String> iterator = this.style.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			String value = style.get(key);
			builder.append(key);
			builder.append(":");
			builder.append(value);
			if (!value.endsWith(";")) {
				builder.append(";");
			}

		}

		return builder.toString();
	}

	/**
	 * 获取AbsHtml元素的样式
	 * 
	 * @param key style的键
	 * @return
	 */
	public String getStyle(String key) {
		if (null == style) {
			return null;
		}

		return style.get(key);
	}

	/**
	 * 添加AbsHtml元素的样式
	 * 
	 * @param key 键
	 * @param value 值
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T addStyle(String key, String value) {
		if (StringUtils.isBlank(key) || key.indexOf(":") != -1 || key.indexOf(";") != -1 || StringUtils.isBlank(value) || value.indexOf(":") != -1) {
			throw new RuntimeException("违法操作!");
		}
		if (null == style) {
			style = new HashMap<String, String>();
		}
		style.put(key, value);

		return (T) this;
	}

	/**
	 * 获取AbsHtml元素的title
	 * 
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 设置AbsHtml元素的title
	 * 
	 * @param title AbsHtml元素的title
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T setTitle(String title) {
		this.title = title;

		return (T) this;
	}

	/**
	 * 获取脚本
	 * 
	 * @return
	 */
	public String getScript() {
		return this.script;
	}

	/**
	 * 
	 * 设置脚本
	 * 
	 * @param script 脚本
	 *            <p>
	 *            例如select级联,需要在页面加载完成后,
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T setScript(String script) {
		this.script = script;

		return (T) this;
	}

	/**
	 * 增加脚本
	 * 
	 * @param script 脚本
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T addScript(String script) {
		if (StringUtils.isBlank(this.script)) {
			this.script = script;
		} else {
			this.script = this.script + script;
		}

		return (T) this;
	}

	/**
	 * 元素宽度，这个值会被设置到元素的样式上，请带上单位px或em等
	 * 
	 * @return
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 设置宽度
	 * 
	 * @param width 宽度
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T setWidth(String width) {
		this.width = width;

		return (T) this;
	}

	/**
	 * 元素高度
	 * 
	 * @return
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * 设置高度
	 * 
	 * @param height 高度
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsHtml> T setHeight(String height) {
		this.height = height;

		return (T) this;
	}
}