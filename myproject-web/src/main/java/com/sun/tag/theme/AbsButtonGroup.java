package com.sun.tag.theme;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsButtonGroup extends AbsHtml {
	private int size = Button.SIZE_DEFAULT;
	private List<AbsHtml> buttons;
	/**
	 * @see Button#getSize
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @see Button#setSize
	 * @param size size
	 * @param <T> 泛型
	 */
	public <T extends AbsButtonGroup> T setSize(int size) {
		this.size = size;
		
		return (T)this;
	}
	
	/**
	 * 获取所有Button元素
	 * @return
	 */
	public List<AbsHtml> getButtons() {
		return this.buttons;
	}
	
	/**
	 * 设置所有Button元素
	 * @param buttons button集合
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsButtonGroup> T setButtons(List<AbsHtml> buttons) {
		this.buttons = buttons;
		
		return (T)this;
	}
	
	/**
	 * 添加Button元素
	 * @param button button
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsButtonGroup> T addButton(AbsHtml button) {
		if (null == this.buttons) {
			this.buttons = new ArrayList<AbsHtml>();
		}
		
		this.buttons.add(button);
		
		return (T)this;
	}
	
	/**
	 * 插入Button元素
	 * @param index 插入位置
	 * @param button Button元素
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsButtonGroup> T insertButton(int index, AbsHtml button) {
		if (null == this.buttons) {
			this.buttons = new ArrayList<AbsHtml>();
		}
		
		this.buttons.add(index, button);
		
		return (T)this;
	}
	
	@Override
	public AbsButtonGroup disable() {
		super.disable();
		if (buttons != null ) {
			for (AbsHtml button : buttons) {
				button.disable();
			}
		}
		
		return this;
	}
	
	@Override
	public AbsButtonGroup enable() {
		super.enable();
		if (buttons != null) {
			for (AbsHtml button : buttons) {
				button.enable();
			}
		}
		
		return this;
	}

}
