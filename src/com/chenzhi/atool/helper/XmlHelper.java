package com.chenzhi.atool.helper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import com.chenzhi.atool.util.StringUtils;
import com.chenzhi.atool.util.XmlUtils;

public class XmlHelper {

	private static XmlHelper instance;
	private static Document curDocument = null;
	private static String curXmlName = "";

	public static XmlHelper getInstance() {
		if (instance == null) {
			instance = new XmlHelper();
		}
		return instance;
	}

	/**
	 * 重新设置当前的XML Document
	 * 
	 * @param newXmlFileName
	 */
	public void reSetCurDocument(String newXmlFileName) {
		initCurDocument(newXmlFileName);
	}

	/**
	 * 获取节点属性值
	 * 
	 * @param elementName
	 * @param attributeName
	 * @return
	 */
	public String getAloneAttributeName(String elementName, String attributeName) {
		String value = "";
		if (curDocument == null) {
			noInitCurDoceumntTip();
			return value;
		}

		value = XmlUtils.getAloneElementAttributeValue(curDocument, elementName, attributeName);
		return value;
	}
	
	public String getLaunchElementName() {
		return XmlUtils.getLaunchElementName(curDocument);
	}

	public void initCurDocument(String xmlFileName) {
		if (StringUtils.isEmpty(xmlFileName)) {
			strParamEmpty("xmlFileName");
			return;
		}

		curXmlName = xmlFileName;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;

		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace(); // 出现异常时，输出异常信息
		}
		try {
			curDocument = db.parse(xmlFileName);
		} catch (Exception e) {
			e.printStackTrace(); // 出现异常时，输出异常信息
		}
	}
	
	public boolean modifyPackage(String targetStr) {
		boolean result = false;
		try {
			if (curDocument == null) {
				noInitCurDoceumntTip();
				return result;
			}
			if (StringUtils.isEmpty(targetStr)) {
				strParamEmpty("新的包名");
				return result;
			}

			result = XmlUtils.modifyElementAttribute(curDocument, "manifest", "package", targetStr, curXmlName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		onResultTip(result, "修改包名 " + targetStr);
		return result;
	}
	
	public boolean modifyVersionName(String targetStr) {
		boolean result = false;
		try {
			if (curDocument == null) {
				noInitCurDoceumntTip();
				return result;
			}
			if (StringUtils.isEmpty(targetStr)) {
				strParamEmpty("新的版本名称");
				return result;
			}

			result = XmlUtils.modifyElementAttribute(curDocument, "manifest", "android:versionName", targetStr,
					curXmlName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		onResultTip(result, "修改版本名称 " + targetStr);
		return result;
	}
	
	public boolean modifyVersionCode(String targetStr) {
		boolean result = false;
		try {
			if (curDocument == null) {
				noInitCurDoceumntTip();
				return result;
			}
			if (StringUtils.isEmpty(targetStr)) {
				strParamEmpty("新的版本代号");
				return result;
			}

			result = XmlUtils.modifyElementAttribute(curDocument, "manifest", "android:versionCode", targetStr,
					curXmlName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		onResultTip(result, "修改版本代号 " + targetStr);
		return result;
	}

	public boolean modifyAppName(String knowAttValue, String targetStr) {
		boolean result = false;
		try {
			if (curDocument == null) {
				noInitCurDoceumntTip();
				return result;
			}
			if (StringUtils.isEmpty(targetStr)) {
				strParamEmpty("新的游戏名称 ");
				return result;
			}

			result = XmlUtils.modifyElementContent(curDocument, "string", "name", knowAttValue, targetStr, curXmlName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		onResultTip(result, "修改游戏名称 " + targetStr);
		return result;
	}
	
	public boolean modifyElementAttribute(String elementName, String attributeName, String targetStr) {
		boolean result = false;
		try {
			if (curDocument == null) {
				noInitCurDoceumntTip();
				return result;
			}
			if (StringUtils.isEmpty(elementName)) {
				strParamEmpty("elementName");
				return result;
			}
			if (StringUtils.isEmpty(attributeName)) {
				strParamEmpty("attributeName");
				return result;
			}
			if (StringUtils.isEmpty(targetStr)) {
				strParamEmpty("新的属性值");
				return result;
			}

			result = XmlUtils.modifyElementAttribute(curDocument, elementName, attributeName, targetStr, curXmlName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		onResultTip(result, "修改元素属性 elementName = " + elementName + " attributeName = " + targetStr);
		return result;
	}

	private void strParamEmpty(String paramName) {
		System.err.println(paramName + "不能为空");
	}

	private void noInitCurDoceumntTip() {
		System.err.println("未初始化当前的Doceumnt对象");
	}
	
	private void onResultTip(boolean state, String msg) {
		if (state) {
			System.out.println(msg + " 操作成功");
		} else {
			System.err.println(msg + " 操作失败");
		}
	}
}
