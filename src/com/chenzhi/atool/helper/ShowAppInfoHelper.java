package com.chenzhi.atool.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.chenzhi.atool.constant.AToolConstant;
import com.chenzhi.atool.util.AXMLPrinter;
import com.chenzhi.atool.util.CmdUtil;
import com.chenzhi.atool.util.FileUtils;
import com.chenzhi.atool.util.ZipUtils;

/**
 * Created by chenzhi on 2018年3月22日
 */
public class ShowAppInfoHelper {

	public static void showAppInfo(String apkPath) {
		Map<String, String> infoMap = getAppInfo4Map(apkPath);

		show("APP名称：" + infoMap.get(AToolConstant.MAP_KEY_APP_NAME));
		show("APP包名：" + infoMap.get(AToolConstant.MAP_KEY_PACKAGE_NAME));
		show("APP版本号：" + infoMap.get(AToolConstant.MAP_KEY_VERSION_CODE));
		show("APP版本名称：" + infoMap.get(AToolConstant.MAP_KEY_VERSION_NAME));
		show("APP支持的最低版本：" + infoMap.get(AToolConstant.MAP_KEY_MIN_SDK_VERSION));
		show("APP支持的目标版本：" + infoMap.get(AToolConstant.MAP_KEY_TARGET_SDK_VERSION));
		show("APPicon名称：" + infoMap.get(AToolConstant.MAP_KEY_ICON_NAME));

		if (!FileUtils.getFileFormat(apkPath).equals("apk")) {
			apkPath = apkPath + ".apk";
		}
		ZipUtils zipFileUtils = new ZipUtils();
		zipFileUtils.copyFileFromZip(apkPath, infoMap.get("clear_icon_path"), AToolConstant.TEMP_ICON_NAME);
	}

	public static void show(String content) {
		System.out.println(content);
	}

	public static Map<String, String> getAppInfo4Map(String apkPath) {
		if (!FileUtils.getFileFormat(apkPath).equals("apk")) {
			apkPath = apkPath + ".apk";
		}

		BufferedReader bReader = CmdUtil.getCmdResult4Reader("aapt.exe d badging " + apkPath);
		Map<String, String> packageinfoMap = new HashMap<String, String>();

		try {
			String line = "";
			String packageName = "";
			String versionCode = "";
			String versionName = "";
			String appName = "";
			String iconName = "";
			String clearIconPath = "";
			String sdkVersion = "";
			String targetSdkVersion = "";

			while ((line = bReader.readLine()) != null) {
				if (line.contains("package:")) {
					String tempPackageStr = line.split("package: name='")[1];
					packageName = tempPackageStr.substring(0, tempPackageStr.indexOf("'"));

					String tempVersionCode = line.split("versionCode='")[1];
					versionCode = tempVersionCode.substring(0, tempVersionCode.indexOf("'"));

					String tempVersionName = line.split("versionName='")[1];
					versionName = tempVersionName.substring(0, tempVersionName.indexOf("'"));
				}
				if (line.contains("application-label")) {
					appName = line.substring(line.indexOf("'") + 1, line.lastIndexOf("'"));
				}
				if (line.contains("application-icon")) {
					iconName = line.substring(line.lastIndexOf("/") + 1, line.lastIndexOf("'"));
					clearIconPath = line.substring(line.indexOf("'") + 1, line.lastIndexOf("'"));
				}
				if (line.contains("sdkVersion:")) {
					sdkVersion = line.substring(line.indexOf("'") + 1, line.lastIndexOf("'"));
				}
				if (line.contains("targetSdkVersion:")) {
					targetSdkVersion = line.substring(line.indexOf("'") + 1, line.lastIndexOf("'"));
				}
			}
			// 关闭数据流
			bReader.close();
			// 保存我们想要的信息
			packageinfoMap.put("package_name", packageName);
			packageinfoMap.put("version_code", versionCode);
			packageinfoMap.put("version_name", versionName);
			packageinfoMap.put("app_name", appName);
			packageinfoMap.put("icon_name", iconName);
			packageinfoMap.put("clear_icon_path", clearIconPath);
			packageinfoMap.put("min_sdk_version", sdkVersion);
			packageinfoMap.put("target_sdk_version", targetSdkVersion);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return packageinfoMap;
	}

	public static void keepTempAppIcon(String apkPath) {
		Map<String, String> infoMap = getAppInfo4Map(apkPath);
		
		if (!FileUtils.getFileFormat(apkPath).equals("apk")) {
			apkPath = apkPath + ".apk";
		}
		ZipUtils zipFileUtils = new ZipUtils();
		zipFileUtils.copyFileFromZip(apkPath, infoMap.get("clear_icon_path"), AToolConstant.TEMP_ICON_NAME);
	}
	
	public static String getLaunchElementName(String apkPath) {
		if (!new File(AToolConstant.TEMP_MANIFEST_XFILE_NAME).exists()) {
			keepTempManifestXmlFile(apkPath);
		}
		
		XmlHelper.getInstance().reSetCurDocument(AToolConstant.TEMP_MANIFEST_XFILE_NAME);
		String launchName = XmlHelper.getInstance().getLaunchElementName();
		char[] launchNames = launchName.toCharArray();
		if ((launchNames[0] + "").equals(".")) {
			launchName = getAppPackageName4Tf(apkPath) + launchName;
		}
		
		return launchName;
	}

	public static void keepTempManifestTxtFile(String apkPath) {
		try {
			String xmlContent = AXMLPrinter.getManifestXMLFromAPK(apkPath);
			FileUtils.writeTxtFile(xmlContent, new File(AToolConstant.MANIFEST_TFILE_NAME), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keepTempManifestXmlFile(String apkPath) {
		try {
			String xmlContent = AXMLPrinter.getManifestXMLFromAPK(apkPath);
			FileUtils.writeTxtFile(xmlContent, new File(AToolConstant.TEMP_MANIFEST_XFILE_NAME), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getAppPackageName4Tf(String apkPath) {
		if (!new File(AToolConstant.TEMP_MANIFEST_XFILE_NAME).exists()) {
			keepTempManifestXmlFile(apkPath);
		}

		XmlHelper.getInstance().reSetCurDocument(AToolConstant.TEMP_MANIFEST_XFILE_NAME);
		String packageName = XmlHelper.getInstance().getAloneAttributeName("manifest", "package");

		return packageName;
	}

	public static String getAppVersionCode4Tf(String apkPath) {
		if (!new File(AToolConstant.TEMP_MANIFEST_XFILE_NAME).exists()) {
			keepTempManifestXmlFile(apkPath);
		}

		XmlHelper.getInstance().reSetCurDocument(AToolConstant.TEMP_MANIFEST_XFILE_NAME);
		String versionCode = XmlHelper.getInstance().getAloneAttributeName("manifest", "android:versionCode");

		return versionCode;
	}

	public static String getAppVersionName4Tf(String apkPath) {
		if (!new File(AToolConstant.TEMP_MANIFEST_XFILE_NAME).exists()) {
			keepTempManifestXmlFile(apkPath);
		}

		XmlHelper.getInstance().reSetCurDocument(AToolConstant.TEMP_MANIFEST_XFILE_NAME);
		String versionName = XmlHelper.getInstance().getAloneAttributeName("manifest", "android:versionName");

		return versionName;
	}

	public static String getAppMinSdkVersion4Tf(String apkPath) {
		if (!new File(AToolConstant.TEMP_MANIFEST_XFILE_NAME).exists()) {
			keepTempManifestXmlFile(apkPath);
		}

		XmlHelper.getInstance().reSetCurDocument(AToolConstant.TEMP_MANIFEST_XFILE_NAME);
		String sdkVersion = XmlHelper.getInstance().getAloneAttributeName("uses-sdk", "android:minSdkVersion");

		return sdkVersion;
	}

	public static String getAppTargetSdkVersion4Tf(String apkPath) {
		if (!new File(AToolConstant.TEMP_MANIFEST_XFILE_NAME).exists()) {
			keepTempManifestXmlFile(apkPath);
		}

		XmlHelper.getInstance().reSetCurDocument(AToolConstant.TEMP_MANIFEST_XFILE_NAME);
		String targetVersion = XmlHelper.getInstance().getAloneAttributeName("uses-sdk", "android:targetSdkVersion");

		return targetVersion;
	}
	
	public static String getAppName4Tf(String apkPath) {
		return "因为该方法是直接通过解析APK中的二进制清单文件来获取信息，所以无法获得对应的APP名称。\n请通过showAppInfo()方法获取。";
	}
}
