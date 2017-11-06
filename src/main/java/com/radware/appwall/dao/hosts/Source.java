package com.radware.appwall.dao.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Source {
	@XmlAttribute(name="type")
	public String type= "";
	@XmlValue
	public String value= "";
}
