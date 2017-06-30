package com.sun.abilities.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.springframework.util.Assert;

public class CronDemo extends CronImpl {
	private static Map<Integer, String> map = new HashMap<>();
	static {
		map.put(1, "一个好的程序员是那种过单行线马路都要往两边看的人。(Doug Linder)");
		map.put(2, "我想大部分人都知道通常一个程序员会具有的美德。当然了，有三种：懒惰，暴躁，傲慢。(Perl 语言发明者 Larry Wall)");
		map.put(3, "工作进度上越早落后，你就会有越充足的时间赶上。(Anonymous Scheduler)");
		map.put(4, "在水上行走和按需求文档开发软件都很容易——前提是它们都是冻结状态。(Edward V Berard)");
		map.put(5, "为了理解递归，我们首先要理解的是递归。(Anonymous)");
		map.put(6, "世上只有两类编程语言：那些拥有被人诟病的和那些没人用的。(Bjarne Stroustrup)");
		map.put(7, "代码纠错要比新编写代码困难一倍。因为，如果你写出了最聪明的代码，按此推算，你将没有更大的智慧来 debug 它。");
		map.put(8, "软件能够复用前，它必须要可用。(Ralph Johnson)");
	}

	@Override
	public String getJobName() {
		return "demo";
	}

	@Override
	public String getTime() {
		return  "0 0/1 * * * ?";//默认为每1分钟触发一次
	}

	@Override
	public Map<String, Object> getParam() {
		return new HashMap<String, Object>(){{put("1", "1");}};
	}

	@Override
	public void execute(JobDataMap jobDataMap) {
		Assert.isTrue(StringUtils.equals(jobDataMap.get("1").toString(), "1"));
		/*for (Integer key : map.keySet()) {
			System.out.println(map.get(key));
		}*/
		int a = (int)(Math.random() * map.size()) + 1;
		System.out.println(map.get(a));
	}

}
