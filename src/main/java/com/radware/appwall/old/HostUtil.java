package com.radware.appwall.old;

public class HostUtil {

	public static final String ANY_HOST = "<Any Host>";
	
	public static String presentation(String hostName){
		if(hostName==null || hostName.isEmpty() || hostName.equals("*") || hostName.equalsIgnoreCase(ANY_HOST)){
			return ANY_HOST;
		}
		else{
			return hostName;
		}
	}
	
	public static boolean same(String host1, String host2){
		return presentation(host1).equalsIgnoreCase(presentation(host2));
	}
	
}
