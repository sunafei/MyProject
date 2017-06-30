package com.sun.tag.theme;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ButtonGroup extends AbsButtonGroup {
	public static final String BUTTON_GROUP_CONFIG = "buttongroup.class";
	public static final String BUTTON_GROUP_DEFAULT = "btn-group";
	
	@Override
	public String getHtml() {
		this.setCls(StringUtils.isBlank(this.getCls()) ? BUTTON_GROUP_DEFAULT : this.getCls());
		HtmlGenerator.setStyle(this);
		
		StringBuilder builder = new StringBuilder("<div role=\"group\" aria-label=\"button group\"");
		HtmlGenerator.buildHtml(builder, this, false);
		builder.append(">");
		List<AbsHtml> buttons = this.getButtons();
		if (buttons != null) {
			for (int i = 0, j = buttons.size(); i < j; i++) {
				AbsHtml button = buttons.get(i);
				if (button instanceof AbsButton) {
					builder.append(button.getHtml());
				}
			}
		}
		builder.append("</div>");
		
		return builder.toString();
	}
}
