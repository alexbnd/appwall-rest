package com.radware.appwall.xml.protectedEnities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ProtectedEntities")
public class ProtectedEntities {

    @XmlElement(name = "WebServerInterfaces")
    public WebServerInterfaces webServerInterfaces = new WebServerInterfaces();

    @XmlElement(name = "WebFarms")
    public WebFarms webFarms = new WebFarms();

    @Override
    public String toString() {
        return "ProtectedEntities{" + "webServerInterfaces=" + webServerInterfaces + ", webFarms=" + webFarms + '}';
    }

    /*
    public String getProtectedEntityName(String ID) {
        WebFarm webFarm = webFarms.getWebFarmID(ID);
        if(webFarm != null) {
            return "(Web Farm) " + webFarm.name;
        }
        WebServerInterface webServerInterface = webServerInterfaces.getWebServerInterfaceById(ID);
        if(webServerInterface != null) {
            return "(Web Server) " + webServerInterface.name;
        }
        return "";
    }

    public String getCleanProtectedEntityName(String ID) {
        String name = getProtectedEntityName(ID);
        if(!name.isEmpty()) {
            name = name.substring(name.indexOf(")") + 1).trim();
        }
        return name;
    }

    public String getProtectedEntityID(String name) {
        WebFarm webFarm = webFarms.getWebFarmName(name);
        if(webFarm != null) {
            return webFarm.id;
        }
        WebServerInterface webServerInterface = webServerInterfaces.getWebServerInterfaceByName(name);
        if(webServerInterface != null) {
            return webServerInterface.id;
        }
        return "";
    }
*/


}
