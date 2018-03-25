package com.chenzhi.atool.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class XmlUtils {

	/**
	 * 修改当前只有一个元素的元素属性
	 * 
	 * @param elementName
	 * @param attribute
	 * @param targetStr
	 * @param parentElement
	 * @return
	 */
	public static boolean modifyElementAttribute(Document curDocument, String elementName, String attribute,
			String targetStr, String newXmlName) {
		boolean result = false;
		NodeList curNodeList = curDocument.getElementsByTagName(elementName);
		for (int i = 0; i < curNodeList.getLength(); i++) {
			if (curNodeList.item(i) instanceof Element) {
				Element manElement = (Element) curNodeList.item(i);
				if (manElement.getAttribute(attribute) != null) {
					manElement.setAttribute(attribute, targetStr);
					break;
				}
			}
		}

		result = commitCurElement(curDocument, newXmlName);
		return result;
	}

	/**
	 * 修改多个相同元素中的一个对应的元素内容
	 * 
	 * @param curDocument
	 * @param elementName
	 * @param knowAttribute
	 * @param knowAttValue
	 * @param targetStr
	 * @param newXmlName
	 * @return
	 */
	public static boolean modifyElementContent(Document curDocument, String elementName, String knowAttribute,
			String knowAttValue, String targetStr, String newXmlName) {
		boolean result = false;
		try {
			NodeList curNodeList = curDocument.getElementsByTagName(elementName);
			for (int j = 0; j < curNodeList.getLength(); j++) {
				if (curNodeList.item(j) instanceof Element) {
					Element manElement = (Element) curNodeList.item(j);
					if (manElement.getAttribute(knowAttribute).equals(knowAttValue)) {
						manElement.setTextContent(targetStr);
						result = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result) {
			result = commitCurElement(curDocument, newXmlName);
		}
		return result;
	}

	/**
	 * 提交改动的xmlDocument
	 * 
	 * @param curDocument
	 * @param xmlName
	 * @return
	 */
	public static boolean commitCurElement(Document curDocument, String xmlName) {
		boolean result = false;
		try {
			result = formatAndWrite2Xml(curDocument, xmlName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static boolean formatAndWrite2Xml(Document doc_main, String xmlName) throws IOException {
		OutputFormat format = new OutputFormat(doc_main);
		format.setLineWidth(65);
		format.setIndenting(true);
		format.setIndent(4);
		Writer out = new StringWriter();
		XMLSerializer serializer = new XMLSerializer(out, format);
		serializer.serialize(doc_main);

		boolean result = false;
		result = write2Xml(xmlName, out.toString());
		return result;
	}

	/**
	 * 写入xml文件
	 * 
	 * @param fileName
	 * @param unformattedXml
	 * @return
	 */
	public static boolean write2Xml(String fileName, String unformattedXml) {
		boolean result = false;
		try {
			if (!FileUtils.getFileFormat(fileName).equals("xml")) {
				fileName = fileName + ".xml";
			}

			File newFile = new File(fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(newFile);
			fileOutputStream.write(unformattedXml.getBytes("UTF-8"));
			fileOutputStream.close();

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 获取当前只有一个元素的元素属性
	 * 
	 * @param curDocument
	 * @param elementName
	 * @param attributeName
	 * @return
	 */
	public static String getAloneElementAttributeValue(Document curDocument, String elementName, String attributeName) {
		return getAttributeValue(curDocument, elementName, "", "", attributeName);
	}

	/**
	 * 获取属性值
	 * 
	 * @param curDocument
	 * @param elementName
	 * @param fixedAttName
	 * @param fixedAttValue
	 * @param targetAttributeName
	 * @return
	 */
	public static String getAttributeValue(Document curDocument, String elementName, String fixedAttName,
			String fixedAttValue, String targetAttributeName) {
		String attributeValue = "";
		try {
			NodeList targetNodeList = curDocument.getElementsByTagName(elementName);
			for (int i = 0; i < targetNodeList.getLength(); i++) {
				if ((Element) targetNodeList.item(i) instanceof Element) {
					Element targetElement = (Element) targetNodeList.item(i);
					if (StringUtils.isEmpty(fixedAttName) && StringUtils.isEmpty(fixedAttValue)) {
						attributeValue = targetElement.getAttribute(targetAttributeName);
						break;
					}
					if (targetElement.getAttribute(fixedAttName).equals(fixedAttValue)) {
						attributeValue = targetElement.getAttribute(targetAttributeName);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return attributeValue;
	}

	/**
	 * 获取启动页面名称
	 * 
	 * @param curDocument
	 * @return
	 */
	public static String getLaunchElementName(Document curDocument) {
		return getParentElementName(curDocument, "category", "android:name", "android.intent.category.LAUNCHER");
	}

	/**
	 * 获取父元素名称
	 * 
	 * @param curDocument
	 * @param elementName
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	public static String getParentElementName(Document curDocument, String elementName, String attributeName,
			String attributeValue) {
		String result = "";
		try {
			NodeList targetNodeList = curDocument.getElementsByTagName(elementName);
			for (int i = 0; i < targetNodeList.getLength(); i++) {
				if (targetNodeList.item(i) instanceof Element) {
					Element targetElement = (Element) targetNodeList.item(i);
					if (targetElement.getAttribute(attributeName).equals(attributeValue)) {

						Element intentElement = (Element) targetElement.getParentNode();
						Element activityElement = (Element) intentElement.getParentNode();
						result = activityElement.getAttribute("android:name");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}