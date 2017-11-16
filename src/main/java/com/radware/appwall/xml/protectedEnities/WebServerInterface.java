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
        return "WebServerInterface{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", description='" + description +
                '\'' + ", ip='" + ip + '\'' + ", port='" + port + '\'' + ", protocol='" + protocol + '\'' +
                ", sslClientMethod='" + sslClientMethod + '\'' + '}';
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSslClientMethod() {
        return sslClientMethod;
    }

    public void setSslClientMethod(String sslClientMethod) {
        this.sslClientMethod = sslClientMethod;
    }
}
