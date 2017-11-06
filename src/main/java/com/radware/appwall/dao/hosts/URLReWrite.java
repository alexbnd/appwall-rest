package com.radware.appwall.dao.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class URLReWrite {

	@XmlElement
    public String Action= "";
	@XmlElement
	public List<ReWrite> ReWrite= new ArrayList<ReWrite>();
	
	public ReWrite getRewrite(String from, String to){
		for(ReWrite iter :  ReWrite){
			if(iter.From.equals(from) && iter.To.equals(to))
				return iter;
		}
		return null;
	}
	
}