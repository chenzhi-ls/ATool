package com.chenzhi.atool;

import com.chenzhi.atool.helper.OptionsHelper;
import com.chenzhi.atool.util.AToolLog;

/**
 * Created by chenzhi on 2018年3月20日
 */
public class ATool {

	public static void main(String[] args) {
		isDebug(true);
		OptionsHelper.options(args);
	}

	// ////////////////////////////////////////////////////////////////
	// //
	// //以下为工具的配置方法
	// //
	// ///////////////////////////////////////////////////////////////
	public static void isDebug(boolean debug) {
		AToolLog.IS_SHOW_LOG = debug;
	}
}
