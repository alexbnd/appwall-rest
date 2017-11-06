package com.radware.appwall.old;

/*
import shared.Print;
import shared.validation.IpValidator;
import shared.validation.PortValidator;
*/

public class UrlArgs {

	public String protocol = "http";
	public String ip = "127.0.0.1";
	public int port = 8200;
	
	public UrlArgs(String protocol, String ip, int port){
		this.protocol = protocol;
		this.ip = ip;
		this.port = port;
	}
	
	public UrlArgs(String s){
		String[] split = s.split(":");
		if(split.length!=3){
			//Print.err("Illegal UrlArgs '"+s+"'");
			return;
		}
		if(split[0].equals("https")){
			protocol = split[0];
		}
	/*	if(IpValidator.ipV4Validate("ip", split[1])==null){
			ip = split[1];
		}
		if(PortValidator.isValid(split[2])){
			port = Integer.parseInt(split[2]);
		}*/
	}
	
}
