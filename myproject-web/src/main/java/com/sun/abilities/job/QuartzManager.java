package com.sun.abilities.job;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 定时任务管理类
 * @author firefly
 */
public class QuartzManager {

	private static final Set<String> jobs = new HashSet<String>();
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "defaultJobGroup";
	private static String TRIGGER_GROUP_NAME = "defaultTriggerGroup";

	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * @param jobName 任务名
	 * @param jobClass 任务
	 * @param time 时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void addJob(String jobName, String jobClass, String time, Map<String, Object> param) {
		String _jobName = JOB_GROUP_NAME + " : " + jobName;
		if (jobs.contains(_jobName)) {
			throw new RuntimeException("定时任务名称已经被使用了：" + _jobName);
		}
		
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, Class.forName(jobClass));// 任务名，任务组，任务执行类
			if (param != null) {
				jobDetail.getJobDataMap().putAll(param);
			}
			
			CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);// 触发器名,触发器组
			trigger.setCronExpression(time);// 触发器时间设定
			
			sched.scheduleJob(jobDetail, trigger);
			jobs.add(_jobName);
			
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加一个定时任务
	 * @param jobName 任务名
	 * @param jobGroupName 任务组名
	 * @param triggerName 触发器名
	 * @param triggerGroupName 触发器组名
	 * @param jobClass 任务
	 * @param time 时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String jobClass, String time) {
		String _jobName = jobGroupName + " : " + jobName;
		if (jobs.contains(_jobName)) {
			throw new RuntimeException("定时任务名称已经被使用了：" + _jobName);
		}
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组
			trigger.setCronExpression(time);// 触发器时间设定
			sched.scheduleJob(new JobDetail(jobName, jobGroupName, Class.forName(jobClass)), trigger);
			jobs.add(_jobName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * @param jobName
	 * @param time
	 */
	public static void modifyJobTime(String jobName, String time, Map<String, Object> param) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName, TRIGGER_GROUP_NAME);
			if (trigger == null) {
				return;
			}
			
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				String jobClass = sched.getJobDetail(jobName, JOB_GROUP_NAME).getJobClass().getName();
				removeJob(jobName);
				
				addJob(jobName, jobClass, time, param);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改一个任务的触发时间
	 * 
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 */
	public static void modifyJobTime(String triggerName, String triggerGroupName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerName, triggerGroupName);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger ct = (CronTrigger) trigger;
				ct.setCronExpression(time);// 修改时间
				sched.resumeTrigger(triggerName, triggerGroupName);// 重启触发器
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * @param jobName
	 */
	public static void removeJob(String jobName) {
		// 删除临时变量中的任务名，防止无法添加
		String _jobName = JOB_GROUP_NAME + " : " + jobName;
		if (jobs.contains(_jobName)) {
			jobs.remove(_jobName);
		}
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器
			sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器
			sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 移除一个任务
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 */
	public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		// 删除临时变量中的任务名，防止无法添加
		String _jobName = jobGroupName + " : " + jobName;
		if (jobs.contains(_jobName)) {
			jobs.remove(_jobName);
		}
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
			sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
			sched.deleteJob(jobName, jobGroupName);// 删除任务
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 启动所有定时任务
	 */
	public static void startJobs() {
		try {
			gSchedulerFactory.getScheduler().start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭所有定时任务
	 */
	public static void shutdownJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}