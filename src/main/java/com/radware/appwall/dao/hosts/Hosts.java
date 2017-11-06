
package com.radware.appwall.dao.hosts;

import com.radware.appwall.old.HostUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Hosts")
public class Hosts {

    @XmlElement
    public List<Host> Host= new ArrayList<Host>();
    @XmlElement
    public GlobalActivityTracking GlobalActivityTracking= new GlobalActivityTracking();

    public Host getHostByName(String name){
    	String fixName;
    	for(Host hostIterator : Host){
    		fixName = hostIterator.Name;
    		if(HostUtil.same(fixName, name)){
    			return hostIterator;
    		}
    	}
    	return null;
    }

	public Host getHostByLink(String link) {
		String fixName;
		if(link.isEmpty()){
			return null;
		}
    	for(Host hostIterator : Host){
    		fixName = hostIterator.Link;
    		if(HostUtil.same(fixName, link)){
    			return hostIterator;
    		}
    	}
		return null;
	}
   
}