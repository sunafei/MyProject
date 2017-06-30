package com.sun.tag.theme;

import java.util.Map;

public abstract class AbsTheme {
	// 图标
	public static final String ICON_ADD = "add"; // 加(新增)
	public static final String ICON_MINUS = "minus"; // 减
	public static final String ICON_DELETE = "delete"; // 删除(垃圾箱)
	public static final String ICON_EDIT = "edit"; // 编辑
	public static final String ICON_SAVE = "save"; // 保存
	public static final String ICON_SUBMIT = "submit"; // 提交
	// 等等 一系列icon

	public static final String RESOURCE_RTF = "rtf";//rich text format
	public static final String RESOURCE_FILE = "file";
	public static final String RESOURCE_DATEPICKER = "datepicker";
	//一系列引入组件的名称
	
	private static String name;
	private static String contextPath;
	private static Map<String, String> icons;

	/**
	 * 初始化
	 * <p>
	 * 设置主题名称、CSS和脚本
	 */
	public abstract void init();

	/**
	 * 获取主题元素
	 * 
	 * @param c 主题元素需要实现的接口类
	 * @param <T> 继承Html
	 * @return
	 */
	public <T extends AbsHtml> T get(Class<T> c) {
//		String[] names = c.getName().split("\\.");
//		String name =  names[names.length-1];
		String elementName = c.getName().replace("Abs", "");
		try {
			return c.cast(Class.forName(elementName).newInstance());
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 获取主题资源
	 * 
	 * @param resource 资源,这里我们系统中是rtf,file,datepicker 这里引入的资源代表着需要引入的js，和css文件
	 * 可以做成标签的形式，可以在后面博客举出例子
	 * @return
	 */
	public abstract String getResource(String resource);

	/**
	 * 获取主题名称
	 * 
	 * @return
	 */
	public static String getName() {
		return name;
	}

	/**
	 * 设置主题名称
	 * 
	 * @param name 名称
	 */
	protected void setName(String name) {
		AbsTheme.name = name;
	}

	/**
	 * 获取请求上下文路径
	 * 
	 * @return
	 */
	public static String getContextPath() {
		return contextPath;
	}

	/**
	 * 设置请求上下文路径
	 * 
	 * @param contextPath 路径
	 */
	public void setContextPath(String contextPath) {
		AbsTheme.contextPath = contextPath;
	}

	/**
	 * 获取图标类
	 * 
	 * @param icon 图标
	 * @return
	 */
	public static String getIconCls(String icon) {
		if (null == icons) {
			return null;
		}

		return icons.get(icon);
	}

	/**
	 * 设置图标集合
	 * 
	 * @param icons 图表集合
	 */
	public void setIcons(Map<String, String> icons) {
		AbsTheme.icons = icons;
	}
}
