package com.radware.appwall.dao.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Thresholds {
    @XmlElement
    public String EnableTPS= "1";
	@XmlElement
    public String TPS= "5";
	@XmlElement
    public String TPSBlockingInterval= "300";
    @XmlElement
    public String EnableSpecificURIRequestsOvertime ="1";
    @XmlElement
    public String SpecificURIRequestsOvertime ="5";
    @XmlElement
    public String SpecificURIBlockingInterval ="300";
    @XmlElement
    public String TPSensitivity ="HIGH";

}