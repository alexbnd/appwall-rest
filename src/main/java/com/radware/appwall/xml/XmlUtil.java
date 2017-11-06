package com.radware.appwall.xml;


public class XmlUtil {
	
	public static <T> T fromXml(Class<T> clazz, String xml) throws Exception {
		return XmlBinder.fromXml(clazz, xml);
	}
	
	public static String toXml(Object object) throws Exception {
		return XmlBinder.toXml(object);
	}
	
}
