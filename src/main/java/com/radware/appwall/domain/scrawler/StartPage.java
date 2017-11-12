package com.radware.appwall.domain.scrawler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StartPage {

    @XmlElement(name = "URL")
    public String url = "";
    @XmlElement(name = "Method")
    public String method = "";

    public StartPage() {
    }

    public StartPage(String URL, String method) {
        this.url = URL;
        this.method = method;
    }

}