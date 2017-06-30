package com.sun.tag.theme;

import java.util.Properties;

/**
 * 主题工厂
 */
public class ThemeFactory {

	private static final String THEME_CONFIG = "theme";
	
	/**
	 * 系统采用主题
	 */
	private static AbsTheme theme = null;
	
	private ThemeFactory() {
	}
	
	/**
	 * 获取主题
	 * @return
	 */
	public static AbsTheme getTheme() {
		if (null == ThemeFactory.theme) {
			getTheme(null);
		}
		
		return ThemeFactory.theme;
	}
	
	/**
	 * 获取主题下的元素
	 * @param c html的Class
	 * @param <T> 泛型
	 * @return
	 */
	public static <T extends AbsHtml> T get(Class<T> c) {
		if (null == ThemeFactory.theme) {
			getTheme(null);
		}
		
		return ThemeFactory.theme.get(c);
	}
	
	/**
	 * 根据配置获取主题
	 * @param properties 主题配置信息
	 * @see SysException
	 */
	private static void getTheme(Properties properties) {
//		String themeConfig = properties.getProperty(THEME_CONFIG);
//		if (StringUtils.isBlank(themeConfig)) {
//			System.out.println("主题文件没有");
//		}
		
		synchronized (ThemeFactory.class) {
			if (null != ThemeFactory.theme) {
				return;
			}
			try {
				String clazz = Theme.class.getName();
				AbsTheme theme = (AbsTheme)Class.forName("com.sun.Theme.Theme").newInstance();
				theme.init();
				
				ThemeFactory.theme = theme;
			} catch (Exception e) {
			}
		}
	}
}