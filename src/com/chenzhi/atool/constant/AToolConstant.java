package com.chenzhi.atool.constant;

import java.io.File;

/**   
 * Created by chenzhi on 2018年3月22日
 */
public class AToolConstant {
	
	public static String DEFAULET_FORMAT = "UTF-8";
	
	public static String FILE_MANIFEST_NAME = "AndroidManifest.xml";
	
	public static String TEMPSTR = "temp_";
	public static String TEMP_ICON_NAME = TEMPSTR + "icon.png";
	public static String TEMP_MANIFEST_XFILE_NAME = TEMPSTR + FILE_MANIFEST_NAME;
	public static String MANIFEST_TFILE_NAME = "AndroidManifest.txt";
	public static String NEW_APKNAME_PERFIX = "atool_";
	
	public static String MAP_KEY_APP_NAME = "app_name";
	public static String MAP_KEY_PACKAGE_NAME = "package_name";
	public static String MAP_KEY_VERSION_CODE = "version_code";
	public static String MAP_KEY_VERSION_NAME = "version_name";
	public static String MAP_KEY_MIN_SDK_VERSION = "min_sdk_version";
	public static String MAP_KEY_TARGET_SDK_VERSION = "target_sdk_version";
	public static String MAP_KEY_ICON_NAME = "icon_name";
	public static String MAP_KEY_CLEAR_ICON_PATH = "clear_icon_path";
	
	public static String PATH_TEMP_ROOT = "temp" + File.separator;
}
