package com.radware.appwall.xml.hosts;

import javax.xml.bind.annotation.XmlElement;

public class URI {
	@XmlElement
	public String UriExp = "";
	@XmlElement
	public String Recursive = "";
	@XmlElement
	public String RegexExp = "";

}