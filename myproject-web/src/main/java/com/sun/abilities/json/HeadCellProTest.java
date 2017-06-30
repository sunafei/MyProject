package com.sun.abilities.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

public class HeadCellProTest {
	List<HeadCell> headCells = new ArrayList<>();

	/**
	 * 通过迭代以目录格式解析层级关系
	 * @author SunAFei
	 */
	@Test
	public void chart() {
		String str = "";
		buildChart(headCells, str);
	}

	private void buildChart(List<HeadCell> headCells, String str) {
		str += "++";
		for (int i = 0; i < headCells.size(); i++) {
			HeadCell headCell = headCells.get(i);
			if (headCell.getChildren() != null && headCell.getChildren().size() > 0) {
				System.out.println(str + headCell.getId());
				buildChart(headCell.getChildren(), str);
			} else {
				System.out.println(str + headCell.getId());
			}
		}
	}

	@Test
	public void proHeadCell2Json() {
		JsonConfig config = new JsonConfig();
		// config.setExcludes(new String[]{});
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		try {
			String array = JSONArray.fromObject(headCells, config).toString();
			System.out.println(array);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void proJson2HeadCell() {
		JSONArray array = perpareHeadCell2Json();
		List<HeadCell> headCells = new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = array.getJSONObject(i);
			headCells.add(buildTierObj(object));
		}
	}

	private HeadCell buildTierObj(JSONObject object) {
		HeadCell hc = new HeadCell();
		if (!StringUtils.equals("[]", object.getString("children"))) {
			hc = (HeadCell) JSONObject.toBean(object, HeadCell.class);
			hc.setChildren(new ArrayList<HeadCell>());
			hc.setParent(null);
			JSONArray array = object.getJSONArray("children");
			for (int i = 0; i < array.size(); i++) {
				HeadCell subHeadCell = buildTierObj(array.getJSONObject(i));
				subHeadCell.setParent(hc);
				hc.getChildren().add(subHeadCell);
			}
		} else {
			hc = (HeadCell) JSONObject.toBean(object, HeadCell.class);
			hc.setParent(null);
		}
		return hc;
	}

	public JSONArray perpareHeadCell2Json() {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		try {
			JSONArray array = JSONArray.fromObject(headCells, config);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Before
	public void setUp() throws Exception {
		Message message = new Message("name", 1);
		HeadCell hc1 = new HeadCell();
		HeadCell hc2 = new HeadCell();
		HeadCell hc11 = new HeadCell();
		HeadCell hc12 = new HeadCell();
		HeadCell hc21 = new HeadCell();
		HeadCell hc111 = new HeadCell();
		HeadCell hc112 = new HeadCell();

		hc111.setId("hc111");
		hc111.setMessage(message);

		hc112.setId("hc112");
		hc112.setMessage(message);

		hc11.setId("hc11");
		hc11.setMessage(message);

		hc12.setId("hc12");
		hc12.setMessage(message);

		hc21.setId("hc21");
		hc21.setMessage(message);

		hc1.setId("hc1");
		hc1.setMessage(message);

		hc2.setId("hc2");
		hc2.setMessage(message);

		List<HeadCell> hcs11 = new ArrayList<>();
		hcs11.add(hc111);
		hcs11.add(hc112);
		hc11.setChildren(hcs11);
		hc111.setParent(hc11);
		hc112.setParent(hc11);

		List<HeadCell> hcs1 = new ArrayList<>();
		hcs1.add(hc11);
		hcs1.add(hc12);
		hc1.setChildren(hcs1);
		hc11.setParent(hc1);
		hc12.setParent(hc1);

		List<HeadCell> hcs2 = new ArrayList<>();
		hcs2.add(hc21);
		hc2.setChildren(hcs2);

		headCells.add(hc1);
		headCells.add(hc2);
	}

	@After
	public void tearDown() throws Exception {
		headCells.clear();
	}

	/**
	 * 在JSON-LIB中，要转换的对象包含自身对象时，会抛出异常There is a cycle in the
	 * hierarchy，解决办法,如下，这样不会保存自关联属性
	 * 
	 * @author SunAFei
	 */
	public void problem1() {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	}

	/**
	 * 自定义要被转换的字段
	 * 
	 * @author SunAFei
	 */
	public void problem2() {
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if (arg1.equals("id") || arg1.equals("serialNumber") || arg1.equals("productName")) {
					return false;
				} else {
					return true;
				}
			}
		});
	}

	/**
	 * 解决延迟加载产生异常的问题(net.sf.json.JSONException:
	 * java.lang.reflect.InvocationTargetException)
	 * 
	 * @author SunAFei
	 */
	public void problem3() {
		JsonConfig config = new JsonConfig();
		// 解决延迟加载产生异常的问题
		config.setExcludes(new String[] { "handler", "hibernateLazyInitializer" });
	}

	/**
	 * 解决数据库查询结果中，Date转换的问题(net.sf.json.JSONException:
	 * java.lang.reflect.InvocationTargetException)
	 * 
	 * @author SunAFei
	 */
	public void problem4() {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			@Override
			public Object processArrayValue(Object obj, JsonConfig jsonconfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				if (value == null)
					return "";
				// 注意：在判断几个父子级类型时要先判断子类型再判断父类型
				if (value instanceof java.sql.Date) {
					String str = DateFormat.getDateInstance(DateFormat.DEFAULT).format(value);
					return str;
				} else if (value instanceof java.sql.Timestamp || value instanceof java.util.Date) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String str = format.format(value);
					return str;
				}
				return value.toString();
			}
		});
	}

	/**
	 * 有些字段的类型是枚举类型，可以在转换的时候将值设置为枚举类的value或者是label
	 * 
	 * @author SunAFei
	 */
	public void problem5() {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Gender.class, new JsonValueProcessor() {
			@Override
			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				if (value instanceof Gender) {
					Gender tmpValue = (Gender) value;
					return tmpValue.getValue();
				}
				return value.toString();
			}

			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	public enum Gender {
		// 通过括号赋值,而且必须带有一个参构造器和一个属性跟方法，否则编译出错
		// 赋值必须都赋值或都不赋值，不能一部分赋值一部分不赋值；如果不赋值则不能写构造器，赋值编译也出错
		MAN("MAN"), WOMEN("WOMEN");

		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		Gender(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
