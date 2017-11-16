package com.radware.appwall.xml.protectedEnities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class WebFarm {

    @XmlElement(name = "ID")
    public String id = "";
    @XmlElement(name = "Name")
    public String name = "";
    @XmlElement(name = "Description")
    public String description = "";
    @XmlElement(name = "WebServerInterfaces")
    public ServersList serversList = new ServersList();

    @Override
    public String toString() {
        return "WebFarm{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", description='" + description + '\'' +
                ", serversList=" + serversList + '}';
    }
}
