package com.radware.appwall.xml;

class TextPart extends SyntaxPart{

	private String text;
	
	public TextPart(String value){
		this.text= value;
	}
	
	public String getText(){
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
}
