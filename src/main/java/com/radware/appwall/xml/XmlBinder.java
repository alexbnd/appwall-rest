package com.radware.appwall.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class XmlBinder {

	public static final String DEFAULT = "##default";
	public static final String PARSER_DOES_NOT_SUPPORT_PRIMITIVES = "Parser does not support primitives";

	public static <T> T fromXml(Class<T> clazz, String xml) throws Exception {
		Element root= XmlTreeCreator.fromXml(xml);	
		String className= clazz.getSimpleName();
		XmlRootElement xmlRootElement=  clazz.getAnnotation(javax.xml.bind.annotation.XmlRootElement.class);
		if(xmlRootElement!=null && !DEFAULT.equals(xmlRootElement.name())){
			className= xmlRootElement.name();
		}
		if(!root.getName().equals(className)){
			throw new ParseException("Root element should be of type "+clazz.getSimpleName());
		}
		T t= fromElement(clazz, root);
		return t;
	}

	public static String toXml(Object object) throws Exception {
		String className= object.getClass().getSimpleName();
		XmlRootElement xmlRootElement=  object.getClass().getAnnotation(javax.xml.bind.annotation.XmlRootElement.class);
		if(xmlRootElement!=null && !DEFAULT.equals(xmlRootElement.name())){
			className= xmlRootElement.name();
		}
 		Element element = fromObject(object);
 		element.setName(className);
		String answer = ElementMarshaller.marshal(element);
		return answer;
	}

	private static String getElementName(Field field){
		XmlElement xmlElement= field.getAnnotation(javax.xml.bind.annotation.XmlElement.class);
		assert xmlElement!=null;
		String otherName= xmlElement.name();
		String name= otherName.equals(DEFAULT) ? field.getName() : otherName;
		return name;
	}

	private static <T> T fromElement(Class<T> clazz, Element element) throws Exception {
		if(clazz==String.class){
			@SuppressWarnings("unchecked")
			T string= (T)element.getValue();
			return string;
		}
		T answer= clazz.newInstance();
		Field[] fields= clazz.getDeclaredFields();
		List<Field> fieldsToFill= new ArrayList<Field>();
		for(Field field : fields){
			field.setAccessible(true);
			XmlValue xmlValue=  field.getAnnotation(javax.xml.bind.annotation.XmlValue.class);
			XmlElement xmlElement= field.getAnnotation(javax.xml.bind.annotation.XmlElement.class);
			XmlAttribute xmlAttribute= field.getAnnotation(javax.xml.bind.annotation.XmlAttribute.class);
			if(xmlValue!=null){
				String value= element.getValue();
				field.set(answer, value);
			}
			else if(xmlElement!=null){
				String name= getElementName(field);
				Class<?> fieldType= field.getType();
				if(fieldType.isPrimitive()){
					throw new ParseException(PARSER_DOES_NOT_SUPPORT_PRIMITIVES);
				}
				if(fieldType==String.class){
					String leafValue= element.getLeafValue(name);
					if(leafValue!=null){
						field.set(answer, leafValue);
					}
				}
				else{
					fieldsToFill.add(field);
				}
			}
			else if(xmlAttribute!=null){
				String otherName= xmlAttribute.name();
				String name= otherName.equals(DEFAULT) ? field.getName() : otherName;
				String value= element.getAttribute(name);
				if(value!=null){
					field.set(answer, value);
				}
			}
		}
		for(Field field : fieldsToFill){
			String name= getElementName(field);
			Class<?> fieldType= field.getType();
			if(fieldType==List.class){
				ParameterizedType type= (ParameterizedType)field.getGenericType();
				Type[] actualTypes= type.getActualTypeArguments();
				Class<?> genericClass= (Class<?>)actualTypes[0];
				List<Element> children= element.getChildren(name);
				if(children.isEmpty()){
					continue;
				}
				List<Object> list= new ArrayList<Object>();
				for(Element iter : children){
					Object o= fromElement(genericClass, iter);
					list.add(o);
				}
				field.set(answer, list);
			}
			else{
				Element child= element.getChild(name);
				if(child==null){
					continue;
				}
				Object o= fromElement(fieldType, child);
				field.set(answer, o);
			}
		}
		return answer;
	}

	private static <T> Element fromObject(T object) throws Exception {
		@SuppressWarnings("unchecked") Class<T> clazz = (Class<T>)object.getClass();
		Field[] fields= clazz.getDeclaredFields();
		String value = null;
		for(Field field : fields){
			XmlValue xmlValue=  field.getAnnotation(javax.xml.bind.annotation.XmlValue.class);
			if(xmlValue==null){
				continue;
			}
			Object o = field.get(object);
			value= o.toString();
		}
		Element element = new Element(clazz.getSimpleName(), value);
		for(Field field : fields){
			XmlAttribute xmlAttribute= field.getAnnotation(javax.xml.bind.annotation.XmlAttribute.class);
			if(xmlAttribute==null){
				continue;
			}
			String otherName= xmlAttribute.name();
			String attributeKey= otherName.equals(DEFAULT) ? field.getName() : otherName;
			Object attributeValue = field.get(object);
			if(attributeValue==null){
				continue;
			}
			element.addAttribute(new Attribute(attributeKey, attributeValue.toString()));
		}
		List<Field> fieldsToFill= new ArrayList<Field>();
		for(Field field : fields){
			XmlElement xmlElement= field.getAnnotation(javax.xml.bind.annotation.XmlElement.class);
			if(xmlElement==null){
				continue;
			}
			Object o = field.get(object);
			if(o==null){
				continue;
			}
			Class<?> fieldType= field.getType();
			if(fieldType.isPrimitive()){
				throw new ParseException(PARSER_DOES_NOT_SUPPORT_PRIMITIVES);
			}		
			else{
				fieldsToFill.add(field);
			}
		}
		for(Field field : fieldsToFill){
			Object o = field.get(object);
			String name= getElementName(field);
			if(o instanceof String){
				element.getChildren().add(new Element(name, (String)o));
			}
			else if(o instanceof List){
				ParameterizedType type= (ParameterizedType)field.getGenericType();
				Type[] actualTypes= type.getActualTypeArguments();
				Class<?> genericClass= (Class<?>)actualTypes[0];
				for(Object iter : (List<?>)o){
					if(genericClass==String.class){
						Element child = new Element(name, (String)iter);
						element.getChildren().add(child);
					}
					else{
						Element child= fromObject(iter);
						child.setName(name);
						element.getChildren().add(child);
					}
				}
			}
			else{			
				Element child= fromObject(o);
				child.setName(name);
				element.getChildren().add(child);
			}
		}
		return element;
	}
	
}
