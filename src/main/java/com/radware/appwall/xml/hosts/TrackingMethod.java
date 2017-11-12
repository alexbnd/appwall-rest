package com.radware.appwall.xml.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TrackingMethod {
	
	@XmlElement
	public String Type = "IP";
	@XmlElement
	public String Name = "";

}
