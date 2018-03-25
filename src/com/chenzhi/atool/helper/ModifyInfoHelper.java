package com.chenzhi.atool.helper;

import java.io.File;

import com.chenzhi.atool.constant.AToolConstant;
import com.chenzhi.atool.inf.IModifyInfo;
import com.chenzhi.atool.util.AToolLog;
import com.chenzhi.atool.util.CmdUtil;
import com.chenzhi.atool.util.FileUtils;
import com.chenzhi.atool.util.StringUtils;

/**
 * Created by chenzhi on 2018年3月22日
 */
public class ModifyInfoHelper implements IModifyInfo {
	
	private static ModifyInfoHelper instance;
	
	private ModifyInfoHelper() {
		//
	}

	public static ModifyInfoHelper getInstance() {
		if (instance == null) {
			instance = new ModifyInfoHelper();
		}
		return instance;
	}
	
	public void Init() {
		//
	}

	public void modifyAppInfo(String apkPath, String appName, String pName, String vcode, String vName) {
		if (StringUtils.isEmpty(apkPath) || !new File(apkPath).exists()) {
			AToolLog.info("请检查路径是否正确 " + apkPath);
			return;
		}
		if (StringUtils.isEmpty(appName) && StringUtils.isEmpty(pName) && StringUtils.isEmpty(vcode)
				&& StringUtils.isEmpty(vName)) {
			return;
		}
		
		if (!StringUtils.isEmpty(appName)) {
			modifyAppName(apkPath, appName);
		}
		if (!StringUtils.isEmpty(pName)) {
			modifyPackage(apkPath, pName);
		}
		if (!StringUtils.isEmpty(vcode)) {
			modifyVersionCode(apkPath, vcode);
		}
		if (!StringUtils.isEmpty(vName)) {
			modifyVersionName(apkPath, vName);
		}
		
		modifyEnd(apkPath);
	}

	private void modifyEnd(String apkPath){
		exportNewAPK(apkPath);
		signNewApk(apkPath);
		deleteTempUnPackage(apkPath);
	}
	
	public void exportNewAPK(String apkPath) {
		String apkName = FileUtils.getFileNameNoFormat(apkPath);
		String tempApkPath = AToolConstant.PATH_TEMP_ROOT + apkName;
		String newTempApkPath = AToolConstant.PATH_TEMP_ROOT + apkName;
		String command = String.format(CmdUtil.CMD_EXPORT_PACKAGE, "", tempApkPath, newTempApkPath);
		
		AToolLog.info("开始回编APK工程 " + apkName + " 请稍候");
		CmdUtil.runCmd(command, AToolConstant.DEFAULET_FORMAT);
		AToolLog.info("回编APK工程 " + apkName + " 完成");
	}
	
	public void signNewApk(String apkPath) {
		String apkName = FileUtils.getFileNameNoFormat(apkPath);
		String newTempApkPath = AToolConstant.PATH_TEMP_ROOT + apkName;
		String newApkPath = AToolConstant.NEW_APKNAME_PERFIX + apkName + "_" + StringUtils.StringFilter(StringUtils.getCurrentDetailsData());
		String command = String.format(CmdUtil.CMD_SIGN_PACKAGE, newApkPath, newTempApkPath);
		
		AToolLog.info("开始签名APK工程 " + apkName + " 请稍候");
		CmdUtil.runCmd(command, "GBK");
		AToolLog.info("签名APK工程 " + apkName + " 完成");
	}
	
	public void deleteTempUnPackage(String apkPath) {
		String apkName = FileUtils.getFileNameNoFormat(apkPath);
		String newTempApkPath = AToolConstant.PATH_TEMP_ROOT + apkName;
		String newApkPath = AToolConstant.PATH_TEMP_ROOT + apkName + ".apk";
		
		FileUtils.deleteDir(new File(newTempApkPath));
		FileUtils.deleteFile(new File(newApkPath));
	}

	public void unPackageApp(String apkPath) {
		if (!apkPath.contains(".apk")) {
			AToolLog.error("抱歉！" + "路径无效：" + apkPath + "\n请输入包含.apk格式的路径");
			return;
		}
		String apkName = FileUtils.getFileNameNoFormat(apkPath);
		String tempApkPath = AToolConstant.PATH_TEMP_ROOT + apkName;
		String command = String.format(CmdUtil.CMD_OPEN_PACKAGE, "", apkName, tempApkPath);

		if (!new File(apkName + ".apk").exists()) {
			AToolLog.error(apkName + " 不存在 请检查路径是否正确");
			return;
		}
		// 暂时这样处理！！！
		if (new File(tempApkPath).exists()) {
			AToolLog.info(tempApkPath + " 已存在");
			return;
		}

		AToolLog.info("开始拆开APK工程 " + apkName + " 请稍候");
		CmdUtil.runCmd(command, AToolConstant.DEFAULET_FORMAT);
		AToolLog.info("拆开APK工程 " + apkName + " 完成");
	}

	@Override
	public boolean modifyPackage(String apkPath, String targetStr) {
		unPackageApp(apkPath);
		String apkName = FileUtils.getFileNameNoFormat(apkPath);
		String tempApkPath = AToolConstant.PATH_TEMP_ROOT + apkName + File.separator;
		
		XmlHelper.getInstance().reSetCurDocument(tempApkPath + AToolConstant.FILE_MANIFEST_NAME);
		
		return XmlHelper.getInstance().modifyPackage(targetStr);
	}

	@Override
	public boolean modifyVersionName(String apkPath, String targetStr) {
		unPackageApp(apkPath);
		String apkName = FileUtils.getFileNameNoFormat(apkPath);
		String tempApkPath = AToolConstant.PATH_TEMP_ROOT + apkName + File.separator;

		XmlHelper.getInstance().reSetCurDocument(tempApkPath + AToolConstant.FILE_MANIFEST_NAME);

		return XmlHelper.getInstance().modifyVersionName(targetStr);
	}

	@Override
	public boolean modifyVersionCode(String apkPath, String targetStr) {
		unPackageApp(apkPath);
		String apkName = FileUtils.getFileNameNoFormat(apkPath);
		String tempApkPath = AToolConstant.PATH_TEMP_ROOT + apkName + File.separator;

		XmlHelper.getInstance().reSetCurDocument(tempApkPath + AToolConstant.FILE_MANIFEST_NAME);

		return XmlHelper.getInstance().modifyVersionCode(targetStr);
	}

	@Override
	public boolean modifyAppName(String apkPath, String targetStr) {
		unPackageApp(apkPath);
		String apkName = FileUtils.getFileNameNoFormat(apkPath);
		String tempApkPath = AToolConstant.PATH_TEMP_ROOT + apkName + File.separator;
		boolean result = false;
		
		String labelNameString = XmlHelper.getInstance().getAloneAttributeName("application", "android:label");
		String nameAttValue = labelNameString.contains("/") ? labelNameString.split("/")[1] : "";

		if (!StringUtils.isEmpty(targetStr)) {
			if (!labelNameString.contains("/")) {
				// 直接在manifest文件中写死游戏名称
				result = XmlHelper.getInstance().modifyElementAttribute("application", "android:label", targetStr);
			} else {
				// 引用string文件中的字符串内容
				String gameResPath = tempApkPath + File.separator + "res" + File.separator;
				String StringsFilePath = gameResPath + "values" + File.separator + "strings.xml";

				XmlHelper.getInstance().reSetCurDocument(StringsFilePath);
				result = XmlHelper.getInstance().modifyAppName(nameAttValue, targetStr);
			}
		}

		return result;
	}
}
