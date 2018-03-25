package com.chenzhi.atool.util;

public class AToolLog {

	public static boolean IS_SHOW_LOG = false;
	
	public static void setLogTag4Channel(String channelName) {
	}

	public static void setLogSeparated4Channel(String channleName) {
		if (IS_SHOW_LOG) {
			String msgInfo = "------" + channleName + "------";
			System.out.println(msgInfo);
		}
	}

	public static void info(String msg) {
		if (IS_SHOW_LOG) {
			System.out.println("------" + msg + "------");
		}
	}

	public static void info(String msg, Throwable throwable) {
		if (IS_SHOW_LOG) {
			System.out.println("------" + msg + "------");
		}
	}

	public static void error(String msg) {
		if (IS_SHOW_LOG) {
			System.err.println("------" + msg + "------");
		}
	}

	public static void error(String msg, Throwable throwable) {
		if (IS_SHOW_LOG) {
			System.err.println("------" + msg + "------");
		}
	}

	public static void debug(String msg) {
		if (IS_SHOW_LOG) {
			System.out.println("------" + msg + "------");
		}
	}

	public static void debug(String msg, Throwable throwable) {
		if (IS_SHOW_LOG) {
			System.out.println("------" + msg + "------");
		}
	}

	public static void warn(String msg) {
		if (IS_SHOW_LOG) {
			System.out.println("------" + msg + "------");
		}
	}

	public static void warn(String msg, Throwable throwable) {
		if (IS_SHOW_LOG) {
			System.out.println("------" + msg + "------");
		}
	}
}
