package com.radware.appwall.xml;

import java.util.ArrayList;
import java.util.List;

public class ElementMarshaller {

	public static final String HEADER = "<?xml version=\"1.0\" encoding=\"ASCII\" standalone=\"yes\"?>";
	
	public static String marshal(Element e){
		List<String> lines = new ArrayList<String>();
		lines.add(HEADER);
		lines.addAll(ElementMarshaller.getLines(e));		
		StringBuilder builder = new StringBuilder();
		for(String line : lines){
			builder.append(line).append("\n");
		}
		return builder.toString();
	}
	
	private static List<String> getLines(Element e){
		String attributeString = getAttributesString(e);
		List<String> answer = new ArrayList<String>();
		if(e.getValue()==null && e.getChildren().isEmpty()){
			answer.add("<"+e.getName()+attributeString+"/>");
			return answer;
		}
		if(e.getValue()!=null){
			String value = StringUtil.convertToAscii(StringUtil.escape(e.getValue()));
			answer.add("<"+e.getName()+attributeString+">"+value+"</"+e.getName()+">");
			return answer;
		}
		answer.add("<"+e.getName()+attributeString+">");
		for(Element iter : e.getChildren()){
			List<String> childLines = getLines(iter);
			for(String line : childLines){
				answer.add("    "+line);
			}			
		}
		answer.add("</"+e.getName()+">");
		return answer;
	}
	
	private static String getAttributesString(Element e){
		StringBuilder builder = new StringBuilder();
		for(Attribute iter : e.getAttributes()){
			builder.append(" "+iter.getKey()+"=\""+StringUtil.escape(iter.getValue())+"\"");		
		}
		return builder.toString();
	}
	
}
