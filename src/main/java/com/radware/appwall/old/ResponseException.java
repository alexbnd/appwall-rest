package com.radware.appwall.old;

public class ResponseException extends Exception {

	private AppwallResponse response;
	
	public ResponseException(AppwallResponse response){
		this.response = response;
	}
	
	public AppwallResponse getElement(){
		return response;
	}
	
}
