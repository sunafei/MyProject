package com.sun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class log4jTest {
	private static final Logger LOG = LoggerFactory.getLogger(log4jTest.class);

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			LOG.debug("debug: log4j is start .");
			LOG.info("info: log4j is start .");
			LOG.warn("warn: log4j is start .");
			LOG.error("error: log4j is start .");
		}
	}
}
