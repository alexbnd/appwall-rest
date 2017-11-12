package com.radware.appwall.xml.hosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class GlobalActivityTracking {

	@XmlElement
	public String TraceEnabled = "0";
	@XmlElement
	public String MaximumNumberOfStates = "10000";
	@XmlElement
	public TpsWindow TpsWindow= new TpsWindow();
	@XmlElement
	public TpmWindow TpmWindow= new TpmWindow();
	@XmlElement
	public String URIsPerSourceCount = "5";
	@XmlElement
	public String EventIntervalSeconds = "120";
	@XmlElement
	public String CookieName = "";
	@XmlElement
	public String FingerprintDetectionPrefix = "/fp/";
	@XmlElement
	public String FingerprintDetectionPage = "fingerprint.html";
	@XmlElement
	public String AjaxPostRequestURL = "awasah/";
	@XmlElement
	public String TimeSkewEnabled = "0";
	@XmlElement
	public String HTML5GeoEnabled = "1";
	@XmlElement
	public String TimeSkewRequests = "0";
	@XmlElement
	public String ClientMaxLoadingSeconds = "2";
	@XmlElement
	public String IPBlockingPenaltyDDoSEnable = "1";
	@XmlElement
	public String IPBlockingPenaltyDDoS = "5";
	@XmlElement
	public String IPBlockingPenaltyScrapingEnable = "1";
	@XmlElement
	public String IPBlockingPenaltyScraping = "5";
	@XmlElement
	public String IPBlockingBlacklistedBotPenaltyEnable = "1";
	@XmlElement
	public String IPBlockingBlacklistedBotPenalty = "51";
	@XmlElement
	public String LocalIPEnabled = "1";
	@XmlElement
	public String ShowFingerPrintPage = "0";
	@XmlElement
	public String IPValidationPeriodSeconds = "86400";
	@XmlElement
	public String IncludeStaticResourcesInRateCalculation = "0";
	@XmlElement
	public String ForceManagementIPForNslookup = "0";
	@XmlElement
	public String IncludeSoapRequestsInRateCalculation = "0";
	@XmlElement
	public String ActivityTrackingDBMaxSizeMB = "512";
	@XmlElement
	public String DigDNSPrimaryAddress = "";
	@XmlElement
	public String DigDNSPrimaryPort = "";
	@XmlElement
	public String DigDNSPrimaryDisplayAddress = "";
	@XmlElement
	public String DigDNSPrimaryDisplayPort = "";
	@XmlElement
	public String DigDNSSecondaryAddress = "";
	@XmlElement
	public String DigDNSSecondaryPort = "";
	@XmlElement
	public String DigDNSSecondaryDisplayAddress= "";
	@XmlElement
	public String DigDNSSecondaryDisplayPort = "";
	@XmlElement
	public String InternalResourceThreshold = "10";
	@XmlElement
	public String InternalResourceBuckets = "5";
	@XmlElement
	public String InternalResourceTimeWindowSec = "10";
	@XmlElement
	public String InternalResourceEventIntervalSec = "60";
	@XmlElement
	public String InternalResourceMaxStatesNumber = "10000";
	@XmlElement
	public String InternalResourceIPBlockingScoreEnable = "1";
	@XmlElement
	public String InternalResourceIPBlockingScore = "25";
}