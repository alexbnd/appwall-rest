package com.radware.appwall.domain.scrawler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Binding {

	@XmlElement
	public String Name="";
	@XmlElement
	public String Link="";
	@XmlElement
	public List<String> Host= new ArrayList<String>();
	@XmlElement
	public List<String> WebServerInterfaceName= new ArrayList<String>();
	@XmlElement
	public String GetUserIPFromHTTPHeader;
	
	
	@Override
	public String toString() {
		return Name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(!(obj instanceof Binding)){
			return false;
		}
		Binding other= (Binding)obj;
		
		return other.Name.equals(Name);
	}
	
	@Override
	public int hashCode() {
		return Name.hashCode();
	}
	
}