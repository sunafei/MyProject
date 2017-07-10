package com.sun.restful.exception;

public class AppException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	// ----异常类型
	/** 提示 */
	public static final int TYPE_INFO = -1;
	/** 错误 */
	public static final int TYPE_ERROR = -2;
	/** 崩溃 */
	public static final int TYPE_FAIL = -3;
	/** 超时 */
	public static final int CODE_OUT_TIME = -4;

	// ----异常编码
	/** 系统异常 */
	public static final int CODE_SYS = 0;
	/** 登录异常 */
	public static final int CODE_LOGIN = 1;
	/** 校验异常 */
	public static final int CODE_CHECK_FORM = 2;

	// 异常编码
	private int code;
	// 异常类型
	private int type;

	/**
	 * 服务端异常
	 * 
	 * @param code 异常编码
	 * @param msg 异常信息
	 * @param type 异常类型
	 */
	public AppException(int code, String msg, int type) {
		super(msg);
		this.code = code;
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
