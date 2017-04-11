package edu.demo.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import edu.demo.bean.message.reply.NewsReply;
import edu.demo.bean.message.reply.NewsReply.Item;

public class XmlUtils {
	public static Map<String, Object> parseSimpleXml2Map(String xml) throws DocumentException {
		Map<String, Object> map = new HashMap<>();

		Document document = DocumentHelper.parseText(xml);

		Element root = document.getRootElement();

		@SuppressWarnings("unchecked")
		Iterator<Element> iterator = root.elementIterator();

		while (iterator.hasNext()) {
			Element ele = iterator.next();
			map.put(ele.getName(), ele.getText());
		}

		return map;
	}

	public static String generateSimpleXmlFromMap(Map<String, Object> map) {

		Element root = DocumentHelper.createElement("xml");
		Document document = DocumentHelper.createDocument(root);

		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			Element element = root.addElement(entry.getKey());

			String value = entry.getValue().toString();
			String content;
			content = "<![CDATA[" + value.toString() + "]]>";
			// if (value instanceof String) {
			// content = "<![CDATA[" + value.toString() + "]]>";
			// } else {
			// content = value.toString();
			// }
			element.addText(content);

		}
		String xml = document.asXML();

		xml = xml.replaceAll("&lt;", "<").replaceAll("&gt;", ">");

		xml = xml.substring(xml.indexOf("<xml>"));
		return xml;
	}

	public static String generateSimpleXmlFromObject(Object object)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Element root = DocumentHelper.createElement("xml");
		Document document = DocumentHelper.createDocument(root);

		appendElement(root, object, null);

		String xml = document.asXML();

		xml = xml.replaceAll("&lt;", "<").replaceAll("&gt;", ">");

		xml = xml.substring(xml.indexOf("<xml>"));
		return xml;
	}

	public static void appendElement(Element parent, Object object, String tagName) {
		Element preElement = null;
		if (tagName != null) {
			preElement = parent.addElement(tagName);
		}

		Class<? extends Object> clz = object.getClass();

		Method[] methods = clz.getMethods();

		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("get") && !"getClass".equals(methodName)) {
				Object value = null;

				try {
					value = method.invoke(object);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

				if (value == null) {
					continue;
				}

				String fieldName = StringUtils.lowerCaseFirstLetter(methodName.substring(3));

				Element element = null;

				if (preElement != null) {
					element = preElement.addElement(StringUtils.upperCaseFirstLetter(fieldName));
				} else {
					element = parent.addElement(StringUtils.upperCaseFirstLetter(fieldName));
				}

				if (value instanceof List) {
					// List
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) value;
					for (Object ob : list) {
						appendElement(element, ob, StringUtils.lowerCaseFirstLetter(ob.getClass().getSimpleName()));
					}

				} else if (value instanceof String || value instanceof Enum) {
					// String or enum
					element.addText("<![CDATA[" + value.toString() + "]]>");
				} else {
					// Object
					appendElement(element, value, null);
				}
			}

		}
	}

	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// ImageReply ir = new ImageReply();
		// ir.setFromUserName("1212121");
		// ir.setToUserName("212121212");
		// ir.setCreateTime("121212121");
		// Image img = ir.new Image();
		// img.setMediaId("12121");
		//
		// ir.setImage(img);
		//
		// System.out.println(generateSimpleXmlFromObject(ir));

		NewsReply r = new NewsReply();

		List<Item> l = new ArrayList<>();

		Item i1 = r.new Item();
		i1.setDescription("d1");
		i1.setPicUrl("p1");
		i1.setTitle("t1");
		i1.setUrl("u1");
		Item i2 = r.new Item();
		i2.setDescription("d2");
		i2.setPicUrl("p2");
		i2.setTitle("t2");
		i2.setUrl("u2");
		Item i3 = r.new Item();
		i3.setDescription("d3");
		i3.setPicUrl("p3");
		i3.setTitle("t3");
		i3.setUrl("u3");
		l.add(i1);
		l.add(i2);
		l.add(i3);

		r.setArticleCount("3");
		r.setArticles(l);
		r.setCreateTime("1212");
		r.setFromUserName("abc");
		r.setToUserName("456");
		System.out.println(generateSimpleXmlFromObject(r));
	}
}
