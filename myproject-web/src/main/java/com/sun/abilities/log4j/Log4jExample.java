package com.sun.abilities.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jExample {
	private static final Logger LOG = LoggerFactory.getLogger(Log4jExample.class);

	public static void main(String[] args) {
		LOG.error("aaaa");
		LOG.info("sss");
		LOG.debug("sssssssss");
	}
}
