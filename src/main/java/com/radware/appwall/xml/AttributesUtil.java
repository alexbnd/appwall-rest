package com.radware.appwall.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AttributesUtil {

	static List<Attribute> getAttributes(String text) throws ParseException{
		List<Attribute> attributes= new ArrayList<Attribute>();
		Pattern pattern = Pattern.compile("\\s+\\w+\\s*=\\s*\"[^\"]*\"");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			String group= matcher.group();
			attributes.add(getAttribute(group));
		}		
		return attributes;
	}
	
	private static Attribute getAttribute(String s) throws ParseException{
		String part= s.trim();
		int equalIndex= part.indexOf("=");		
		String key= part.substring(0, equalIndex).trim();
		String val= part.substring(equalIndex+1).trim();
		if(key.isEmpty()){
			throw new ParseException(s);
		}
		if(val.length()<2){
			throw new ParseException(s);
		}
		if(val.charAt(0)!='"' || val.charAt(val.length()-1)!='"'){
			throw new ParseException(s);
		}
		String attrString = val.substring(1, val.length()-1);
		attrString= StringUtil.removeEscapes(attrString);
		attrString= StringUtil.convertUnicode(attrString);
		Attribute attribute= new Attribute(key, attrString);
		return attribute;
	}
	
	
}
