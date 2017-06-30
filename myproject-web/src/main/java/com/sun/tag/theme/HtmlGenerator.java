package com.sun.tag.theme;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class HtmlGenerator {

	/**
	 * 通用的html属性生成方法
	 * 该方法会默认处理html的visible属性，如果其visible为true，将会添加样式display:none;
	 * @param builder
	 * @param html
	 * @return
	 */
	public static StringBuilder buildHtml(StringBuilder builder, AbsHtml html) {
		return buildHtml(builder, html, true);
	}

	/**
	 * 通用的html属性生成方法
	 * @param builder
	 * @param html
	 * @param processVisivle 是否处理visible属性
	 * @return
	 */
	public static StringBuilder buildHtml(StringBuilder builder, AbsHtml html, boolean processVisivle) {
		if (processVisivle && !html.isVisible()) {
			html.addStyle("display", "none");
		}
		if (html.isDisabled()) {
			builder.append(" disabled=\"disabled\"");
			html.addCls("disabled");
		}
		if (!html.isDisabled() && StringUtils.isNotBlank(html.getName())) {
			builder.append(" name=\"" + html.getName() + "\"");
		}
		if (StringUtils.isNotBlank(html.getId())) {
			builder.append(" id=\"" + html.getId() + "\"");
		}
		if (StringUtils.isNotBlank(html.getCls())) {
			builder.append(" class=\"" + html.getCls() + "\"");
		}
		if (StringUtils.isNotBlank(html.getStyle())) {
			builder.append(" style=\"" + html.getStyle() + "\"");
		}
		if (StringUtils.isNotBlank(html.getTitle())) {
			builder.append(" title=\"" + html.getTitle() + "\"");
		}
		
		Map<String, Object> attributes = html.getAttributes();
		if (attributes != null) {
			Iterator<Entry<String, Object>> iterator = attributes.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> attribute = iterator.next();
				Object v = attribute.getValue();
				
				builder.append(" " + attribute.getKey() + "=\"" + (null == v ? "" : String.valueOf(v)) + "\"");
			}
		}
		if (StringUtils.isNotBlank(html.getEvents())) {
			builder.append(" " + html.getEvents());
		}
		
		return builder;
	}
	
	/**
	 * 获取按钮默认class
	 * @param size 按钮大小
	 * @return
	 */
	public static String getButtonCls(int size) {
		return Button.BUTTON_DEFAULT + " " + HtmlGenerator.getButtonSize(size);
	}
	
	/**
	 * 获取按钮大小class
	 * @param size
	 * @return
	 */
	public static String getButtonSize(int size) {
		String css = null;
		switch (size) {
		case 5:
			css = "btn-lg";
			break;
		case 4:
			break;
		case 3:
			css = "btn-sm";
			break;
		case 2:
			css = "btn-xs";
			break;
		case 1:
			css = "btn-xs";
			break;
		default:
			css = "btn-sm";
			break;
		}
		
		return null == css ? "" : css;
	}
	
	/**
	 * 获得按钮的包含宽度=padding+'border-left-width'+'border-right-width'
	 * @param size 尺寸
	 * @return not null
	 */
	public static int getButtonInnerWidth(int size) {
		//border
		int width = 1*2;
		switch (size) {
		//padding
		case 5:
			width += 16*2;
			break;
		case 4:
			break;
		case 3:
			width += 10*2;
			break;
		case 2:
			width += 5*2;
			break;
		case 1:
			width += 5*2;
			break;
		default:
			width += 10*2;
			break;
		}
		return width;
	}
	
	/**
	 * 去除id中的 
	 * <p>.</p>
	 * <p>[</p>
	 * <p>]</p>
	 * @param id
	 * @return
	 */
	public static String getSimpleId(String id){
		return id.replace(".", "_").replace("[", "").replace("]", "").replace("_", "");
	}
	
	/**
	 * 将id转换成能让jquery选择器正确识别的形式
	 * @param selector
	 * @return
	 */
	public static String getJquerySelector(String selector){
		return selector.replace(".", "\\\\.").replace("[", "\\\\[").replace("]", "\\\\]");
	}
	
	public static StringBuilder getStyle(AbsHtml html) {
		StringBuilder builder = new StringBuilder();
		if (!html.isVisible()) {
			builder.append("display:none;");
		}
		if (StringUtils.isNotBlank(html.getWidth())) {
			builder.append("width:").append(html.getWidth()).append(";");
		}
		if (StringUtils.isNotBlank(html.getHeight())) {
			builder.append("height:").append(html.getHeight()).append(";");
		}
		
		return builder;
	}

	public static void setStyle(AbsHtml html) {
		if (!html.isVisible()) {
			html.addStyle("display", "none");
		}
		if (StringUtils.isNotBlank(html.getWidth())) {
			html.addStyle("width", html.getWidth());
		}
		if (StringUtils.isNotBlank(html.getHeight())) {
			html.addStyle("width", html.getHeight());
		}
	}
}