package com.radware.appwall.xml.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class SourceWhitelist {

	@XmlElement
	public List<Source> Source = new ArrayList<Source>();
	
	public Source getSourceName(String name){
    	for(Source sourceIterator : Source){
    		if(sourceIterator.value.equals(name)){
    			return sourceIterator;
    		}
    	}
    	return null;
    }
}
