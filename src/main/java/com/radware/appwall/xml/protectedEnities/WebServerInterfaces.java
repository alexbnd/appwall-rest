package com.radware.appwall.xml.protectedEnities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class WebServerInterfaces {

    @XmlElement(name = "WebServerInterface")
    public List<WebServerInterface> webServerInterface = new ArrayList<WebServerInterface>();

    public WebServerInterface getWebServerInterfaceById(String id) {
        for(WebServerInterface serverInterface : webServerInterface) {
            if(serverInterface.id.equals(id)) {
                return serverInterface;
            }
        }
        return null;
    }

    public WebServerInterface getWebServerInterfaceByName(String name) {
        for(WebServerInterface serverInterface : webServerInterface) {
            if(serverInterface.name.equals(name)) {
                return serverInterface;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "WebServerInterfaces{" + "webServerInterface=" + webServerInterface + '}';
    }
}
