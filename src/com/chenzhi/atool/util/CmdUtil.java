package com.chenzhi.atool.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by chenzhi on 2018年3月20日
 */
public class CmdUtil {

	// 测试用的签名文件
	private static String KEY_STORE = "debug.keystore";
	// 测试用的签名文件密码
	private static String KEY_STORE_PASSWORD = "123456";

	// 拆开APP包的CMD命令
	public static String CMD_OPEN_PACKAGE = "cmd.exe /C java -jar %sapktool.jar d [-s] -f %s.apk -o %s";
	// 回编APP包的CMD命令
	public static String CMD_EXPORT_PACKAGE = "cmd.exe /C java -jar %sapktool.jar b %s -o %s.apk";
	// 签名校验网址
	private static String TSA = "http://timestamp.digicert.com/";
	// 对APP包进行签名的CMD命令
	public static String CMD_SIGN_PACKAGE = "cmd.exe /C jarsigner -digestalg SHA1 -sigalg MD5withRSA -tsa " + TSA
			+ " -verbose -keystore " + KEY_STORE + " -signedjar %s.apk %s.apk " + KEY_STORE + " -storepass "
			+ KEY_STORE_PASSWORD;

	public static boolean runCmd(String cmd) {
		return runCmd(cmd, "UTF-8");
	}

	public static boolean runCmd(String cmd, String format) {
		// System.out.println(cmd);
		boolean result = false;
		Runtime rt = Runtime.getRuntime();
		BufferedReader bufferedReader = null;
		InputStreamReader isr = null;
		try {
			Process p = rt.exec(cmd);
			// p.waitFor();
			isr = new InputStreamReader(p.getInputStream(), format);
			bufferedReader = new BufferedReader(isr);
			String msg = null;
			while ((msg = bufferedReader.readLine()) != null) {
				AToolLog.info(msg);
			}
			result = true;
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			AToolLog.error("执行cmd命令出错" + e.getMessage());
		} finally {
			try {
				if (isr != null) {
					isr.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				AToolLog.error(e.getMessage());
			}
		}

		return result;
	}

	public static BufferedReader getCmdResult4Reader(String cmd) {
		return getCmdResult4Reader(cmd, "UTF-8");
	}

	public static BufferedReader getCmdResult4Reader(String cmd, String format) {
		// System.out.println(cmd);
		Runtime rt = Runtime.getRuntime();
		BufferedReader bufferedReader = null;
		InputStreamReader isr = null;
		try {
			Process p = rt.exec(cmd);
			// p.waitFor();
			isr = new InputStreamReader(p.getInputStream(), format);
			bufferedReader = new BufferedReader(isr);
		} catch (Exception e) {
			AToolLog.error("执行cmd命令出错" + e.getMessage());
			e.printStackTrace();
		}

		return bufferedReader;
	}
}
