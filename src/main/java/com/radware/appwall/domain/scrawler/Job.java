package com.radware.appwall.domain.scrawler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Job {

	@XmlElement
	public String Name="";
	@XmlElement
	public String Desc="";
	@XmlElement
	public Limits Limits= new Limits();	
	@XmlElement
	public String MinsBetweenCrops="";
	@XmlElement
	public String RetriesForFetches="";
	@XmlElement
	public StartPage StartPage= new StartPage();	
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(!(obj instanceof Job)){
			return false;
		}
		Job other= (Job)obj;
		return other.Name.equals(Name);
	}
	
	@Override
	public int hashCode() {
		return Name.hashCode();
	}

}