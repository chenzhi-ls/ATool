package com.chenzhi.atool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by chenzhi on 2018年3月21日
 */
public class ZipUtils {

	public void copyFileFromZip(String zipfile, String iconPath, String tempFilePath) {
		ZipFile zipFile = null;
		ZipInputStream zipInput = null;
		ZipEntry zipEntry = null;
		OutputStream os = null;
		InputStream is = null;
		File tempFile = new File(tempFilePath);

		try {
			if (!tempFile.exists()) {
				tempFile.createNewFile();
			}
			zipFile = new ZipFile(zipfile);
			zipInput = new ZipInputStream(new FileInputStream(zipfile), Charset.forName("utf-8"));
			os = new FileOutputStream(tempFile);
			zipEntry = zipFile.getEntry(iconPath);
			is = zipFile.getInputStream(zipEntry);
			int len;
			while ((len = is.read()) != -1) {
				os.write(len);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
				if (zipInput != null) {
					zipInput.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
