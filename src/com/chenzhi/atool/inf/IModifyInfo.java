package com.chenzhi.atool.inf;

/**
 * Created by chenzhi on 2018年3月22日
 */
public interface IModifyInfo {

	public boolean modifyPackage(String apkPath, String targetStr);

	public boolean modifyVersionName(String apkPath, String targetStr);

	public boolean modifyVersionCode(String apkPath, String targetStr);

	public boolean modifyAppName(String apkPath, String targetStr);
}
