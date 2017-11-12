package com.radware.appwall.domain.scrawler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class HostMap {
	
	@XmlElement(name = "Binding")
	public List<Binding> bindings= new ArrayList<Binding>();

	public Binding getBindingByHostName(String hostName){
		for(Binding bindingIterator : bindings){
			if(bindingIterator.getName().equals(hostName)){
				return bindingIterator;
			}
		}
		return null;
	}

	public Binding getBindingByLink(String link) {
		if(link.trim().isEmpty()){
			return null;
		}
		for(Binding bindingIterator : bindings){
			if(bindingIterator.getLink().equals(link)){
				return bindingIterator;
			}
		}
		return null;
	}

}