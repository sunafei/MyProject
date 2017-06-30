package com.sun.abilities.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 默认的任务计划实现
 * @author SunAFei
 *
 */
public abstract class CronImpl implements Cron {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//开辟一个新的线程来做
		execute(context.getJobDetail().getJobDataMap());
	}

	/**
	 * 执行调度
	 * @author SunAFei
	 * @param jobDataMap
	 */
	public abstract void execute(JobDataMap jobDataMap);

}
