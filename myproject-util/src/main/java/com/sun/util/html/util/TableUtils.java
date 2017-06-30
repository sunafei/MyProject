package com.sun.util.html.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.util.html.TH;
import com.sun.util.html.TR;
import com.sun.util.html.Table;

public class TableUtils {
	public static void main(String[] args) {
		String table_Head_CH = "部门,总计{男,女},职称{教师{教授,副教授},非教师{正高,副高},其他},合计";

		System.out.println(makeTableHead(table_Head_CH));
	}

	public static Table makeTableHead(String tableHeadExpression) {
		Table t = new Table();
		Pattern p = Pattern.compile("\\{[^\\{]*?\\}");
		Matcher m = p.matcher(tableHeadExpression);
		int start = 0;
		int rowSpan = 0;
		while (m.find()) {
			m.reset();
			start = 0;
			TR tr = new TR();
			t.addChildtag(0, tr);
			while (m.find(start)) {
				String[] hrTitles = m.group(0).substring(1, m.group(0).length() - 1).split(",");
				tableHeadExpression = tableHeadExpression.substring(0, start)
						+ tableHeadExpression.substring(start).replaceFirst("\\{[^\\{]*?\\}",
								new StringBuilder("(c:").append(makeTH(tr, hrTitles, rowSpan)).append(" r:").append(rowSpan + 1).append(")").toString());

				start = m.start() + 1;
				m = p.matcher(tableHeadExpression);
			}
			++rowSpan;
		}
		TR tr = new TR();
		t.addChildtag(0, tr);

		makeTH(tr, tableHeadExpression.split(","), rowSpan);
		return t;
	}

	private static int makeTH(TR tr, String[] hrTitles, int rowSpan) {
		Pattern colsP = Pattern.compile("\\(c:(\\d+) r:(\\d+)\\)$");
		int cols = 0;
		for (int i = 0; i < hrTitles.length; ++i) {
			TH th = new TH();
			Matcher colsM = colsP.matcher(hrTitles[i]);
			if (colsM.find()) {
				th.setColspan(Integer.parseInt(colsM.group(1)));
				th.setRowspan(th.getRowspan() - Integer.parseInt(colsM.group(2)));
				th.setText(colsM.replaceFirst(""));
				cols += Integer.parseInt(colsM.group(1));
			} else {
				th.setText(hrTitles[i]);
				++cols;
			}
			th.setRowspan(th.getRowspan() + rowSpan);
			tr.addChildtag(th);
		}
		return cols;
	}
}