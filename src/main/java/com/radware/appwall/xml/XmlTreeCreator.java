package com.radware.appwall.xml;

import java.util.ArrayList;
import java.util.List;

class XmlTreeCreator {
	
	static Element fromXml(String xml) throws ParseException{
		List<SyntaxPart> list= SyntacticParser.fromXml(xml);
		if(list.isEmpty()){
			return null;
		}		
		List<SyntaxPart> subList= new ArrayList<SyntaxPart>(list);
		if(subList.get(0) instanceof HeaderTag){
			subList.remove(0);
		}
		if(!(subList.get(0) instanceof StartInterface)){
			String xmlSample= xml.length()<100 ? xml : xml.substring(0, 100)+"...";
			throw new ParseException("Illegal XML: "+xmlSample);
		}
		Element root= new Element(subList);
		return root;
	}
	
}
