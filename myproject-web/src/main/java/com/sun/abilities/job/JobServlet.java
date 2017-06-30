package com.sun.abilities.job;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang3.StringUtils;

public class JobServlet extends HttpServlet {
	private static final long serialVersionUID = 7546468102910470779L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String jobs = config.getInitParameter("jobs");
		if (StringUtils.isBlank(jobs)) {
			return;
		}
		
		String[] classes = jobs.split(",");
		for (int i = 0; i < classes.length; i++) {
			Cron cron = null;
			try {
				cron = (Cron)Class.forName(classes[i]).newInstance();
				QuartzManager.addJob(cron.getJobName(), classes[i], cron.getTime(), cron.getParam());
			} catch (Exception e) {
				throw new ServletException("无法注册计划任务，请检查。classes="+classes[i],e);
			}
		}
	}
}
