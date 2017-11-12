package com.radware.appwall.xml.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class URIs {
	 
	@XmlElement
	public List<URI> URI = new ArrayList<URI>();
	
	   public URI getURIyName(String name){
	    	for(URI uriIterator : URI){
	    		if(uriIterator.UriExp.equals(name)){
	    			return uriIterator;
	    		}
	    	}
	    	return null;
	    }
	
}