package com.radware.appwall.rest.wrappers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.radware.appwall.domain.entities.HostBindings;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

public class HostBindingsCollectionWrapper implements Serializable {

    public List<HostBindings> getCollection() {
        return collection;
    }

    public void setCollection(List<HostBindings> collection) {
        this.collection = collection;
    }

    @Expose
    @SerializedName("HostBindings")
    @XmlElement(name = "HostBindings")
    private List<HostBindings> collection;
}
