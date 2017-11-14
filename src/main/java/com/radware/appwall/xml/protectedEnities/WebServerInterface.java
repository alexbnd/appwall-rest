package com.radware.appwall.xml.protectedEnities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class WebServerInterface {

    @XmlElement(name = "ID")
    public String id = "";
    @XmlElement(name = "Name")
    public String name = "";
    @XmlElement(name = "Description")
    public String description = "";
    @XmlElement(name = "IP")
    public String ip = "";
    @XmlElement(name = "Port")
    public String port = "";
    @XmlElement(name = "Protocol")
    public String protocol = "";
    @XmlElement(name = "SSLClientMethod")
    public String sslClientMethod = "1";

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof WebServerInterface)) {
            return false;
        }
        WebServerInterface other = (WebServerInterface) obj;
        return this.name.equals(other.name);
    }

}
