package com.radware.appwall.xml;

class HeaderTag extends SyntaxPart{

	private String tag;
	
	public HeaderTag(String tag){
		this.tag= tag;
	}
	
	public String getTag(){
		return tag;
	}
	
	@Override
	public String toString() {
		return "<?"+tag+"?>";
	}
	
}
