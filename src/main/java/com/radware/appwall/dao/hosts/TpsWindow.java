package com.radware.appwall.dao.hosts;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class TpsWindow {
	
	@XmlValue
	public String value="5";
	@XmlAttribute(name="low")
	public String low="30";
	@XmlAttribute(name="high")
	public String high="10";

}