package com.sun.tag.theme;

import java.util.UUID;

public class HtmlDemo {

	public static void main(String[] args) {
		String id1 = UUID.randomUUID().toString();
		String id2 = UUID.randomUUID().toString();
		String id3 = UUID.randomUUID().toString();
		Button button1 = ThemeFactory.get(AbsButton.class).setIcon("add").setActive(true).setId(id1).setTitle("新增").setInnerHTML("新增");
		Button button2 = ThemeFactory.get(AbsButton.class).setIcon("add").setActive(true).setId(id2);
		Button button3 = ThemeFactory.get(AbsButton.class).setIcon("add").setActive(true).setId(id3);
//		System.out.println(button1.getHtml());
//		System.out.println(button2.getHtml());
//		System.out.println(button3.getHtml());
		ButtonGroup bg = ThemeFactory.get(AbsButtonGroup.class).insertButton(0, button1).insertButton(1, button2).insertButton(2, button3);
		System.out.println(bg.getHtml());
	}

}
