package com.radware.appwall.xml;

import java.util.List;

class EmptyTag extends SyntaxPart implements StartInterface, EndInterface{

	private String tag;
	private List<Attribute> attributes;
	
	public EmptyTag(String text) throws ParseException{
		String[] parts= text.trim().split(" ");
		if(parts.length==0){
			throw new ParseException();
		}
		this.tag= parts[0];
		attributes= AttributesUtil.getAttributes(text);
	}
	
	public List<Attribute> getAttributes(){
		return attributes;
	}
	
	public String getTag(){
		return tag;
	}
	
	@Override
	public String toString() {
		return "<"+tag+"/>";
	}
	
}
