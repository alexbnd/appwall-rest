package com.radware.appwall.xml.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReWrite {

	@XmlElement
    public String From="";
	@XmlElement
    public String To="";
	@XmlElement
    public String Block="";
	
		
}