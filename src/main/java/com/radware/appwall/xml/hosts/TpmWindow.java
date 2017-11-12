package com.radware.appwall.xml.hosts;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class TpmWindow {

	@XmlValue
	public String value="15";
	@XmlAttribute(name="low")
	public String low="3";
	@XmlAttribute(name="high")
	public String high="1";

}