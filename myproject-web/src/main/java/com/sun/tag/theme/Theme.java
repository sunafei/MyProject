package com.sun.tag.theme;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Theme extends AbsTheme {
	public static final String NAME = "bootstrap";//主题名称，可以是EasyUI
	
	@Override
	public void init() {
		this.setName(NAME);
		
		Map<String, String> icons = new HashMap<String, String>();
		icons.put(Theme.ICON_ADD, "glyphicon glyphicon-plus");
		icons.put(Theme.ICON_MINUS, "glyphicon glyphicon-minus");
		icons.put(Theme.ICON_DELETE, "glyphicon glyphicon-trash");
		icons.put(Theme.ICON_EDIT, "glyphicon glyphicon-edit");
		icons.put(Theme.ICON_SAVE, "glyphicon glyphicon-floppy-disk");
		
		this.setIcons(icons);
	}

	@Override
	public String getResource(String resource) {
		String contextPath = Theme.getContextPath();
		StringBuilder builder = new StringBuilder();
		//引入资源，关于本主题的所有资源，这里可能就做为一个系统的head内容
		builder.append("<meta charset=\"utf-8\">\n");
		builder.append("<meta name=\"renderer\" content=\"webkit\">\n");
		builder.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
		builder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
		builder.append("<link href=\"" + contextPath + "/system/css/bootstrap.min.css\" rel=\"stylesheet\" charset=\"UTF-8\">\n");
		builder.append("<link href=\"" + contextPath + "/system/css/bootstrap-theme.min.css\" rel=\"stylesheet\" charset=\"UTF-8\">\n");
		builder.append("<script src=\"" + contextPath + "/system/script/modernizr-2.8.3.min.js\" charset=\"UTF-8\"></script>\n");
		builder.append("<script src=\"" + contextPath + "/system/script/jquery-1.11.1.min.js\" charset=\"UTF-8\"></script>\n");
		builder.append("<script src=\"" + contextPath + "/system/script/bootstrap.min.js\" charset=\"UTF-8\"></script>\n");
		if (StringUtils.isNotBlank(resource)) {
			String[] resources = resource.split(",");
			for (int i = 0; i < resources.length; i++) {//这里用的是我们寻找的一些前端组件
				String res = resources[i];
				if (RESOURCE_DATEPICKER.equals(res)) {
					builder.append("<link href=\"" + contextPath + "/system/css/bootstrap-datetimepicker.min.css\" rel=\"stylesheet\" charset=\"UTF-8\">\n");
					builder.append("<script src=\"" + contextPath + "/system/script/bootstrap-datetimepicker.min.js\" charset=\"UTF-8\"></script>\n");
					builder.append("<script src=\"" + contextPath + "/system/script/bootstrap-datetimepicker.zh-CN.js\" charset=\"UTF-8\"></script>\n");
				} else if (RESOURCE_FILE.equals(res)) {
					builder.append("<script src=\"" + contextPath + "/system/script/bootstrap-filestyle.js\"></script>\n");
				} else if (RESOURCE_RTF.equals(res)) {
					builder.append("<script src=\"" + contextPath + "/system/script/ueditor/ueditor.config.js\"></script>");
					builder.append("<script src=\"" + contextPath + "/system/script/ueditor/ueditor.all.js\"></script>");
				}
			}
		}
		return builder.toString();
	}
}
