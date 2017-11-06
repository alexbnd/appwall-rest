package com.radware.appwall.old;


public class DefaultAppWall {

	private static AppWall instance;
	
	public static void setAddress(String s){
		UrlArgs urlArgs = new UrlArgs(s);
		instance = new AppWall(urlArgs.ip, urlArgs.port);
	}
	  
	public static AppWall getInstance(){
		return instance;
	}	
	
}
