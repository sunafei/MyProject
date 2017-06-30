package com.sun.tag.theme;

public abstract class AbsButton extends AbsHtml {

	/**
	 * 按钮类型：按钮
	 */
	public static final String BUTTON = "Button";
	
	/**
	 * 按钮类型：重置按钮
	 */
	public static final String RESET = "reset";
	
	/**
	 * 按钮类型：提交按钮
	 */
	public static final String SUBMIT = "submit";
	
	/**
	 * 按钮类型：查询按钮
	 */
	public static final String SEARCH = "search";
	
	/**
	 * 默认按钮大小
	 */
	public static final int SIZE_DEFAULT = 3;
	/**
	 * 比默认大小小一号
	 */
	public static final int SIZE_SMALL = 2;
	/**
	 * 比默认大小小两号
	 */
	public static final int SIZE_SMALLER = 1;
	/**
	 * 比默认大小大一号
	 */
	public static final int SIZE_LARGE = 4;
	/**
	 * 比默认大小大两号
	 */
	public static final int SIZE_LARGER = 5;

	private String type;
	private String icon;
	private int size = SIZE_DEFAULT;
	private Boolean active;
	

	/**
	 * 按钮类型
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * 设置按钮类型
	 * @param type type
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsButton> T setType(String type) {
		this.type = type;
		
		return (T)this;
	}
	
	/**
	 * 获取图标
	 * @return
	 */
	public String getIcon() {
		return this.icon;
	}

	/**
	 * 设置图标
	 * @param icon icon
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsButton> T setIcon(String icon) {
		this.icon = icon;
		
		return (T)this;
	}

	/**
	 * 获取按钮大小
	 * <p> 5 : 比4大一号 {@link #SIZE_LARGER}
	 * <p> 4 : 比普通大一号 {@link #SIZE_LARGE}
	 * <p> 3 : 普通大小 {@link #SIZE_DEFAULT}
	 * <p> 2 : 比普通小一号 {@link #SIZE_SMALL}
	 * <p> 1 : 比2小一号 {@link #SIZE_SMALLER}
	 * <p> 超出此范围的，默认大小算
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 设置按钮大小
	 * @param size size
	 * @param <T> 泛型
	 */
	public <T extends AbsButton> T setSize(int size) {
		this.size = size;
		
		return (T)this;
	}

	/**
	 * 是否处于激活状态
	 * 默认否
	 * @return
	 */
	public Boolean getActive() {
		if (null == active) {
			return Boolean.FALSE;
		}
		
		return active;
	}

	/**
	 * 设置是否处于激活状态
	 * @param active active
	 * @param <T> 泛型
	 * @return
	 */
	public <T extends AbsButton> T setActive(Boolean active) {
		this.active = active;
		
		return (T)this;
	}
}
