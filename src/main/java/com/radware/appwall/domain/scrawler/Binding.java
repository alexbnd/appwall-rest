package com.radware.appwall.domain.scrawler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Binding {

    @XmlElement(name = "Name")
    public String name = "";
    @XmlElement(name = "Link")
    public String link = "";
    @XmlElement(name = "Host")
    public List<String> hosts = new ArrayList<String>();
    @XmlElement(name = "WebServerInterfaceName")
    public List<String> webServerInterfaceName = new ArrayList<String>();
    @XmlElement(name = "GetUserIPFromHTTPHeader")
    public String getUserIPFromHTTPHeader;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public List<String> getWebServerInterfaceName() {
        return webServerInterfaceName;
    }

    public void setWebServerInterfaceName(List<String> webServerInterfaceName) {
        this.webServerInterfaceName = webServerInterfaceName;
    }

    public String getGetUserIPFromHTTPHeader() {
        return getUserIPFromHTTPHeader;
    }

    public void setGetUserIPFromHTTPHeader(String getUserIPFromHTTPHeader) {
        this.getUserIPFromHTTPHeader = getUserIPFromHTTPHeader;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Binding)) {
            return false;
        }
        Binding other = (Binding) obj;

        return other.name.equals(name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}