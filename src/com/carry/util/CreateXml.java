package com.carry.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * 增删改xml参数生成类
 * @author zhanglei
 * */
public class CreateXml {
	/**
	 * 生成xml信息
	 * @param String[] node 节点名称
	 * @param Map<String, Object> nodevalue 节点属性值
	 * @author zhanglei
	 * **/
	public static String xml(String[] node ,Map<String, Object> nodevalue ){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		stringBuilder.append("<body>");
		stringBuilder.append("<"+node[0]+">");
		for (int i = 1; i < node.length; i++) {
			stringBuilder.append("<"+node[i]+">");
			if(nodevalue.get(node[i])!=null ){
			 stringBuilder.append(nodevalue.get(node[i]));
			}else{
			  stringBuilder.append("");
			}
			stringBuilder.append("</"+node[i]+">");
		}
		stringBuilder.append("</"+node[0]+">");
		stringBuilder.append("</body>");
		
		return stringBuilder.toString();
	}
	/**
	 * 操作返回xml信息解析
	 * **/
	public static Map<String, String> analysisXml(String resultXml){
		Map<String, String> result = new HashMap<String, String>();
		try {
			Document doc = (Document) DocumentHelper.parseText(resultXml);
			Element root = doc.getRootElement();
			Element code = root.element("fhz");
			Element message = root.element("tsxx");
		    //Element gh = root.element("gh");
			result.put("code", code.getText());
			result.put("message", message.getText());
			//result.put("gh", gh.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		return result;
	}
	
	public static Map<String, Object> analysisXml2(String resultXml){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Document doc = (Document) DocumentHelper.parseText(resultXml);
			Element root = doc.getRootElement();
			Element code = root.element("fhz");
			Element message = root.element("tsxx");
		    //Element gh = root.element("gh");
			result.put("code", code.getText());
			result.put("message", message.getText());
			//result.put("gh", gh.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} 
	public static Map<String, Object> analysisShXml(String resultXml){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Document doc = (Document) DocumentHelper.parseText(resultXml);
			Element root = doc.getRootElement();
			Element yh = root.element("yh");
			String jgdm = yh.elementTextTrim("jgdm");
			String qymc = yh.elementTextTrim("qymc");
			result.put("jgdm", jgdm);
			result.put("qymc", qymc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
