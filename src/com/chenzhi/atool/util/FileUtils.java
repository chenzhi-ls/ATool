package com.chenzhi.atool.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	/**
	 * 删除目录(包括：目录里的所有文件和根目录)
	 * 
	 * @param dir
	 * @return
	 */
	public static int deleteDir(File dir) {
		int deletedFiles = 0;
		try {
			if (dir != null && dir.isDirectory()) {
				try {
					for (File child : dir.listFiles()) {
						if (child.isDirectory()) {
							deletedFiles += deleteDir(child);
						}
						if (child.delete()) {
							deletedFiles++;
						}
					}
					if (dir.isDirectory()) {
						dir.delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deletedFiles;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(File newPath) {
		boolean status;

		if (newPath.isFile() && newPath.exists()) {
			try {
				newPath.delete();
				status = true;
			} catch (SecurityException se) {
				se.printStackTrace();
				status = false;
			}
		} else
			status = false;
		return status;
	}

	public static void createFileDir(String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写入文件
	 * 
	 * @param content
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static boolean writeTxtFile(String content, File fileName, String format) throws Exception {
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes(format));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 读取文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readerFile(String fileName) {
		StringBuilder contentBuilder = new StringBuilder();
		File f = new File(fileName);
		if (f.exists() && f.isFile()) {
			BufferedReader br = null;
			FileReader fr = null;
			try {
				fr = new FileReader(f);
				br = new BufferedReader(fr);
				String line = null;
				while ((line = br.readLine()) != null) {
					contentBuilder.append(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fr != null) {
						fr.close();
					}
					if (br != null) {
						br.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return contentBuilder.toString();
	}

	public static String getFileDir(String filePath) {
		if (StringUtils.isEmpty(filePath))
			return "";
		return filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 根据文件的绝对路径获取文件名但不包含扩展名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameNoFormat(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return "";
		}
		int point = filePath.lastIndexOf('.');
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1, point);
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

	/**
	 * 创建指定目录
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean createDir(String filePath) {
		boolean result = false;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 创建指定目录（包含父目录如：temp//temp）
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean createDirs(String filePath) {
		boolean result = false;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
