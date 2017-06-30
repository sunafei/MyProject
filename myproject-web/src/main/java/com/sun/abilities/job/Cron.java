package com.sun.abilities.job;

import java.util.Map;

import org.quartz.Job;

public interface Cron extends Job{

	/**
	 * 任务名称
	 * @return
	 */
	String getJobName();

	/**
	 * cron表达式
	 *  一个cron表达式有至少6个（也可能是7个）由空格分隔的时间元素。从左至右，这些元素的定义 如下：   
		1．秒（0–59）   
		2．分钟（0–59）   
		3．小时（0–23）   
		4．月份中的日期（1–31）   
		5．月份（1–12或JAN–DEC）   
		6．星期中的日期（1–7或SUN–SAT）   
		7．年份（1970–2099）
	 * 格式：秒 分 时 日 月 周
	 * 例：
	 * "0 0/5 * * * ?" 为每5分钟触发一次
	 * "0 5 10 * * ?" 为每天10点05触发一次
	 * @return
	 */
	String getTime();

	/**
	 * 参数
	 * @return
	 */
	Map<String, Object> getParam();
}
