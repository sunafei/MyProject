package com.sun.application.statistic.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.junit.Test;

import com.sun.application.statistic.basic.HeadCell;
import com.sun.application.statistic.component.Th;
import com.sun.util.ListUtils;

/**
 * 通过HeadCell对象集合构建一个复杂的table
 */
public class ProductTable {
	List<HeadCell> rowHeadCells = new ArrayList<HeadCell>();
	List<HeadCell> colHeadCells = new ArrayList<HeadCell>();

	/**
	 * 准备数据，构造一系列的headCell对象
	 */
	public void prepare() {
		// 新建一个横向表头
		HeadCell r1 = new HeadCell(UUID.randomUUID().toString(), "r1", null, 0);
		HeadCell r2 = new HeadCell(UUID.randomUUID().toString(), "r2", null, 0);
		HeadCell r3 = new HeadCell(UUID.randomUUID().toString(), "r3", null, 0);
		HeadCell r4 = new HeadCell(UUID.randomUUID().toString(), "r4", null, 0);
		HeadCell r5 = new HeadCell(UUID.randomUUID().toString(), "r5", null, 0);
		HeadCell r1_1 = new HeadCell(UUID.randomUUID().toString(), "r1_1", r1, 1);
		HeadCell r1_2 = new HeadCell(UUID.randomUUID().toString(), "r1_2", r1, 1);
		HeadCell r2_1 = new HeadCell(UUID.randomUUID().toString(), "r2_1", r2, 1);
		HeadCell r2_2 = new HeadCell(UUID.randomUUID().toString(), "r2_2", r2, 1);
		HeadCell r3_1 = new HeadCell(UUID.randomUUID().toString(), "r3_1", r3, 1);
		HeadCell r3_2 = new HeadCell(UUID.randomUUID().toString(), "r3_2", r3, 1);
		HeadCell r4_1 = new HeadCell(UUID.randomUUID().toString(), "r4_1", r4, 1);
		HeadCell r4_2 = new HeadCell(UUID.randomUUID().toString(), "r4_2", r4, 1);
		HeadCell r5_1 = new HeadCell(UUID.randomUUID().toString(), "r5_1", r5, 1);
		HeadCell r5_2 = new HeadCell(UUID.randomUUID().toString(), "r5_2", r5, 1);
		r1.getChildren().add(r1_1);
		r1.getChildren().add(r1_2);
		r2.getChildren().add(r2_1);
		r2.getChildren().add(r2_2);
		r3.getChildren().add(r3_1);
		r3.getChildren().add(r3_2);
		r4.getChildren().add(r4_1);
		r4.getChildren().add(r4_2);
		r5.getChildren().add(r5_1);
		r5.getChildren().add(r5_2);

		rowHeadCells.add(r1);
		rowHeadCells.add(r2);
		rowHeadCells.add(r3);
		rowHeadCells.add(r4);
		rowHeadCells.add(r5);

		// 新建一个纵向表头
		HeadCell c1 = new HeadCell(UUID.randomUUID().toString(), "c1", null, 0);
		HeadCell c2 = new HeadCell(UUID.randomUUID().toString(), "c2", null, 0);
		HeadCell c3 = new HeadCell(UUID.randomUUID().toString(), "c3", null, 0);
		HeadCell c4 = new HeadCell(UUID.randomUUID().toString(), "c4", null, 0);
		HeadCell c5 = new HeadCell(UUID.randomUUID().toString(), "c5", null, 0);
		HeadCell c1_1 = new HeadCell(UUID.randomUUID().toString(), "c1_1", c1, 1);
		HeadCell c1_2 = new HeadCell(UUID.randomUUID().toString(), "c1_2", c1, 1);
		HeadCell c2_1 = new HeadCell(UUID.randomUUID().toString(), "c2_1", c2, 1);
		HeadCell c2_2 = new HeadCell(UUID.randomUUID().toString(), "c2_2", c2, 1);
		HeadCell c3_1 = new HeadCell(UUID.randomUUID().toString(), "c3_1", c3, 1);
		HeadCell c3_2 = new HeadCell(UUID.randomUUID().toString(), "c3_2", c3, 1);
		HeadCell c4_1 = new HeadCell(UUID.randomUUID().toString(), "c4_1", c4, 1);
		HeadCell c4_2 = new HeadCell(UUID.randomUUID().toString(), "c4_2", c4, 1);
		HeadCell c5_1 = new HeadCell(UUID.randomUUID().toString(), "c5_1", c5, 1);
		HeadCell c5_2 = new HeadCell(UUID.randomUUID().toString(), "c5_2", c5, 1);
		c1.getChildren().add(c1_1);
		c1.getChildren().add(c1_2);
		c2.getChildren().add(c2_1);
		c2.getChildren().add(c2_2);
		c3.getChildren().add(c3_1);
		c3.getChildren().add(c3_2);
		c4.getChildren().add(c4_1);
		c4.getChildren().add(c4_2);
		c5.getChildren().add(c5_1);
		c5.getChildren().add(c5_2);

		colHeadCells.add(c1);
		colHeadCells.add(c2);
		colHeadCells.add(c3);
		colHeadCells.add(c4);
		colHeadCells.add(c5);
	}

	/**
	 * 生产一个table
	 */
	@Test
	public void getTable() {
		prepare();// 准备数据
		StringBuilder statHtml = new StringBuilder();
		// 创建thead标题行
		statHtml.append("<table border = '1' id='expTable' name='statTable'>");
		statHtml.append("<thead>");
		List<Th> ths = new ArrayList<Th>();
		// 这里获得的是纵向表头的最大深度，这是因为我们在生成表格的时候
		// 1.如果是二维表，那table的表头并不是我们的单元格对象，我们可以通过这个集合生成表头的rowspan
		// 2.我们获得每个th的rowspan，从级的方面考虑，除了最低一级的th，上层的th他的rowspan只能是1，
		// 那么最后一级是怎么判断的呢,我们是用最后一级的深度(deep)，拿最大的一级减去他的深度加1不就是他所占的rowspan吗
		int maxDeep = getMaxDeep(colHeadCells);
		getColThs(colHeadCells, ths, maxDeep);
		// 这个ths是用来做什么的呢
		// 在这里我们为th集合分了组，他的行号（rowNum）就是我们的tr，一行可以生产一个tr
		statHtml.append(productColTh(ths, rowHeadCells, colHeadCells));
		statHtml.append("</thead>");
		statHtml.append("<tbody>");
		String row = buildContext(rowHeadCells, colHeadCells);
		row = row.replace("</th><tr>", "</th>");
		statHtml.append(row);
		statHtml.append("</tbody>");
		statHtml.append("</table>");
		System.out.println(statHtml.toString());
	}

	/**
	 * 这里我们就要生产tbody的部分，先说思路
	 * 1.纵向也是有表头的，纵向表头也是有主子关系，但是他和横向表头不同，一个对象的一层叶子节点是在一个tr中，这生成tr就显得容易很多
	 * 2.整个生成过程和纵向表头是相反的，但是生成方式不同
	 * 
	 * @param rowHCs
	 * @param colHCs
	 * @return
	 */
	public String buildContext(List<HeadCell> rowHCs, List<HeadCell> colHCs) {
		List<HeadCell> colHeadCells = getColTh(colHCs);
		StringBuffer sb = new StringBuffer();
		int maxDeep = getMaxDeep(rowHCs);
		for (HeadCell childCell : rowHCs) {
			sb.append(bulidCol(childCell, colHeadCells, maxDeep));
			sb.append("</tr>");
		}
		return sb.toString();
	}

	/**
	 * 生成横向表头
	 * 
	 * @param headCell
	 * @param colHCs
	 * @param maxDeep
	 * @return
	 */
	public String bulidCol(HeadCell headCell, List<HeadCell> colHCs, int maxDeep) {
		StringBuffer sb = new StringBuffer();
		sb.append("<tr>");
		if (headCell.getChildren() != null && headCell.getChildren().size() > 0) {
			Integer rowNum = headCell.getRowNum() == 0 ? 1 : headCell.getRowNum();
			sb.append("<th id='" + headCell.getId() + "' rowspan='" + rowNum + "' deep='" + (headCell.getDeep() + 1) + "'>" + headCell.getName()
					+ "</th>");
			for (HeadCell childCell : headCell.getChildren()) {
				sb.append(bulidCol(childCell, colHCs, maxDeep));
			}
		} else {
			int rowNum = headCell.getRowNum() == 0 ? 1 : headCell.getRowNum();
			int colNum = maxDeep - headCell.getDeep() + 1;
			int deep = headCell.getDeep() + 1;
			sb.append("<th name='boottom' id='" + headCell.getId() + "' rowspan='" + rowNum + "' colspan='" + colNum + "' deep='" + deep + "'>"
					+ headCell.getName() + "</th>");
			for (HeadCell colCell : colHCs) {
				sb.append("<td>0.0</td>");
			}
		}
		return sb.toString();
	}

	/**
	 * 获得最底层的headCell的集合
	 * 
	 * @param colHCs
	 * @return
	 */
	public List<HeadCell> getColTh(List<HeadCell> colHCs) {
		List<HeadCell> bottomRowHead = new ArrayList<HeadCell>();
		for (HeadCell rowCell : colHCs) {
			bottomRowHead.addAll(builds(rowCell));
		}
		return bottomRowHead;
	}

	public static List<HeadCell> builds(HeadCell headCell) {
		List<HeadCell> bottom = new ArrayList<HeadCell>();
		if (headCell.getChildren() != null && headCell.getChildren().size() > 0) {
			for (HeadCell cheadCell : headCell.getChildren()) {
				bottom.addAll(builds(cheadCell));
			}
		} else {
			bottom.add(headCell);
		}
		return bottom;
	}

	public String productColTh(List<Th> ths, List<HeadCell> rowHCs, List<HeadCell> colHCs) {
		StringBuffer sb = new StringBuffer();
		boolean headtitle = true;
		int rowspan = getMaxDeep(colHCs) + 1;
		int colspan = getMaxDeep(rowHCs) + 1;
		try {
			Map<Integer, List<Th>> maps = ListUtils.groupByProperty(ths, "rowNum");// 按照对象中某个字段分组
			for (Entry<Integer, List<Th>> entry : maps.entrySet()) {
				sb.append("<tr>");
				if (headtitle) {
					sb.append("<th rowspan='" + rowspan + "' colspan='" + colspan + "'></th>");
					headtitle = false;
				}
				for (Th th : entry.getValue()) {// 生产纵向表头
					sb.append(th.getThStr());
				}
				sb.append("</tr>");
			}
		} catch (Exception e) {

		}
		return sb.toString();
	}

	public void getColThs(List<HeadCell> colHCs, List<Th> ths, int maxDeep) {
		for (HeadCell hc : colHCs) {
			if (hc.getChildren() != null && hc.getChildren().size() > 0) {
				Th th = new Th();
				th.setRowNum(hc.getDeep());
				th.setThId(hc.getId());
				th.setColspan(hc.getRowNum());
				th.setRowspan(1);
				if (hc.getParent() != null) {
					th.setpId(hc.getParent().getId());
				}
				th.setValue(hc.getName());
				ths.add(th);
				getColThs(hc.getChildren(), ths, maxDeep);
			} else {
				Th th = new Th();
				th.setRowNum(hc.getDeep());
				th.setThId(hc.getId());
				th.setColspan(hc.getRowNum());
				th.setRowspan(maxDeep - hc.getDeep() + 1);
				if (hc.getParent() != null) {
					th.setpId(hc.getParent().getId());
				}
				th.setValue(hc.getName());
				ths.add(th);
			}
		}
	}

	/**
	 * 求表格对象集合的最大深度
	 * 
	 * @param headCells 单元格集合对象
	 * @return 最大深度
	 */
	public static Integer getMaxDeep(List<HeadCell> headCells) {
		if (headCells != null && headCells.size() > 0) {
			List<Integer> deep = new ArrayList<Integer>();
			for (HeadCell headCell : headCells) {
				deep.add(maxDeep(headCell));
			}
			return Collections.max(deep);
		} else {
			return 1;
		}
	}

	private static int maxDeep(HeadCell headCell) {
		int i = 0;
		if (headCell.getChildren() != null && headCell.getChildren().size() > 0) {
			for (HeadCell childCell : headCell.getChildren()) {
				int j = maxDeep(childCell);
				i = j > i ? j : i;
			}
		} else {
			i = headCell.getDeep();
		}
		return i;
	}
}