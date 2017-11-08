package com.radware.appwall.domain.scrawler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StartPage {

	@XmlElement
	public String URL="";
	@XmlElement
	public String Method="";

	public StartPage(){}
	
	public StartPage(String URL, String Method){
		this.URL= URL;
		this.Method= Method;
	}
		
}