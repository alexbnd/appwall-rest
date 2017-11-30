package com.radware.appwall.rest.wrappers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.radware.appwall.domain.entities.WebServerBinding;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

public class WebServerCollectionWrapper implements Serializable{

    public List<WebServerBinding> getCollection() {
        return collection;
    }

    public void setCollection(List<WebServerBinding> collection) {
        this.collection = collection;
    }

    @Expose
    @SerializedName("WebServers")
    @XmlElement(name="WebServers")
    private List<WebServerBinding> collection;
}
