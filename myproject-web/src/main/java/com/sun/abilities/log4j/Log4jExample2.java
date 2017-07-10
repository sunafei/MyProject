package com.sun.abilities.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 指定某个类的输出路径
 * @author SunAFei
 *
 */
public class Log4jExample2 {
	private static final Logger LOG = LoggerFactory.getLogger(Log4jExample2.class);

	public static void main(String[] args) {
		LOG.error("aaaa");
		LOG.info("sss");
		LOG.debug("sssssssss");
	}
}
