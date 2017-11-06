package com.radware.appwall.xml;

class EndTag extends SyntaxPart implements EndInterface{

	private String tag;
	
	public EndTag(String tag){
		this.tag= tag;
	}
	
	public String getTag(){
		return tag;
	}
	
	@Override
	public String toString() {
		return "</"+tag+">";
	}
	
}
