package com.radware.appwall.xml.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class HotLink {

    @XmlElement
    public List<String> TrustedHost= new ArrayList<String>();
    @XmlElement
    public List<String> TrustedLink= new ArrayList<String>();
    @XmlElement
    public List<String> ExcludedURI= new ArrayList<String>();
    @XmlElement
    public String Action= "";
    @XmlElement
    public String ProtectQueries= "0";
    @XmlElement
    public String ProtectMediaFiles="1";
    @XmlElement
    public String ProtectAnyOtherPage="0";
   
        
}