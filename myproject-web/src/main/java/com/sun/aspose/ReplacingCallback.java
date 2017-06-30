package com.sun.aspose;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.aspose.words.IReplacingCallback;
import com.aspose.words.ReplaceAction;
import com.aspose.words.ReplacingArgs;

public class ReplacingCallback implements IReplacingCallback {
	private Object val;

	public ReplacingCallback(Object val) {
		this.val = val;
	}

	@Override
	public int replacing(ReplacingArgs ra) throws Exception {
		String p = StringUtils.trim(ra.getMatch().group(1));
		if (StringUtils.isNotBlank(p)) {
			Object vo = PropertyUtils.getProperty(val, p);
			String v = String.valueOf(vo);
			ra.setReplacement(v);
		}
		return ReplaceAction.REPLACE;
	}

}
