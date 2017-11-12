package com.radware.appwall.domain.scrawler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Job {

    @XmlElement(name = "Name")
    public String name = "";
    @XmlElement(name = "Desc")
    public String desc = "";
    @XmlElement(name = "Limits")
    public Limits limits = new Limits();
    @XmlElement(name = "MinsBetweenCrops")
    public String minsBetweenCrops = "";
    @XmlElement(name = "RetriesForFetches")
    public String retriesForFetches = "";
    @XmlElement(name = "StartPage")
    public StartPage startPage = new StartPage();


    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Job)) {
            return false;
        }
        Job other = (Job) obj;
        return other.name.equals(name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}