package com.radware.appwall.domain.scrawler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Scrawler")
public class Scrawler {

	@XmlElement(name = "HostMap")
	public HostMap hostMap= new HostMap();
	@XmlElement(name = "Job")
	public List<Job> job= new ArrayList<Job>();

	
}