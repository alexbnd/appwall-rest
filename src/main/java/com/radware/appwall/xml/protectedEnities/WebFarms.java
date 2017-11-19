package com.radware.appwall.xml.protectedEnities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class WebFarms {

    @XmlElement(name = "WebFarm")
    public List<WebFarm> webFarm = new ArrayList<WebFarm>();

    public WebFarm getWebFarmID(String id) {
        for(WebFarm webFarm1 : webFarm) {
            if(webFarm1.id.equals(id)) {
                return webFarm1;
            }
        }
        return null;
    }

    public WebFarm getWebFarmName(String name) {
        for(WebFarm webFarm1 : webFarm) {
            if(webFarm1.name.equals(name)) {
                return webFarm1;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "WebFarms{" + "webFarm=" + webFarm + '}';
    }
}
