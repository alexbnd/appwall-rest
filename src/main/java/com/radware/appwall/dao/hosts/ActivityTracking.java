package com.radware.appwall.dao.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ActivityTracking {

	@XmlElement
	public String Action = "disable";
	@XmlElement
	public String Mode = "anti-DDoS";
	@XmlElement
	public TrackingMethod TrackingMethod= new TrackingMethod();
	@XmlElement
	public String SourceBlocking = "Disable";
	@XmlElement
    public Thresholds Thresholds= new Thresholds();
	@XmlElement
    public URIs ProtectedURIs= new URIs();
	@XmlElement
    public URIs UnProtectedURIs= new URIs();
	@XmlElement
	public SourceWhitelist SourceWhitelistIp = new SourceWhitelist();
	@XmlElement
	public SourceWhitelist SourceWhitelistHeader = new SourceWhitelist();
	@XmlElement
	public SourceWhitelist SourceWhitelistFingerPrint = new SourceWhitelist();

}