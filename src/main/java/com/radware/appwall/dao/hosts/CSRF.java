package com.radware.appwall.dao.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class CSRF {
	
    @XmlElement
    public List<String> TrustedHost= new ArrayList<String>();
    @XmlElement
    public List<String> TrustedLink= new ArrayList<String>();
    @XmlElement
    public List<String> ExcludedURI= new ArrayList<String>();
    @XmlElement
    public String Action= "";
    @XmlElement
    public String ProtectPOST= "1";
    @XmlElement
    public String ProtectQueries ="0";
   
 
}