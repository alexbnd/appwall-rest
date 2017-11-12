package com.radware.appwall.xml.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Host {
	
    @XmlElement
    public String Name="";
    @XmlElement
    public String Link="";
    @XmlElement
    public List<String> equiv= new ArrayList<String>();
    @XmlElement
    public CSRF CSRF= new CSRF();
    @XmlElement
    public HotLink HotLink= new HotLink();
    @XmlElement
    public DirList DirList= new DirList();
    @XmlElement
    public URLReWrite URLReWrite= new URLReWrite();
    @XmlElement
    public ActivityTracking ActivityTracking= new ActivityTracking();
    
    @Override
    public String toString() {
    	return Name;
    }
    
}
