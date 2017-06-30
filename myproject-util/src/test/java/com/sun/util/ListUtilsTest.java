package com.sun.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ListUtilsTest {

	public static void main(String[] args) {
		List<TreeObj> ls = new ArrayList<TreeObj>();
		TreeObj a1 = new TreeObj("a01");
		TreeObj a1_1 = new TreeObj("a01-1");
		TreeObj a1_2 = new TreeObj("a01-2");
		TreeObj a2 = new TreeObj("a02");
		TreeObj a2_1 = new TreeObj("a02-1");
		TreeObj a2_2 = new TreeObj("a02-2");
		ls.add(a1);
		ls.add(a1_1);
		ls.add(a1_2);
		ls.add(a2);
		ls.add(a2_1);
		ls.add(a2_2);
		TreeObj a =ListUtils.findByTree(ls, "children", "name", "a02-1");
		System.out.println(a.getName());
	}

}
