package com.radware.appwall.old;

public class AppwallResponse {

	private boolean hadReply;
	private int statusCode;
	public String firstLine = "";
	public String body = "";
	public String timeStamp = "";
	
	public static final AppwallResponse NO_REPLY= new AppwallResponse();
	
	private AppwallResponse(){
		hadReply = false;
	}
	
	public AppwallResponse(String text) {
		hadReply = true;
		String[] lines = text.split("\n");
		firstLine = lines[0];
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < lines.length; i++) {
			String s = lines[i];
			builder.append(s+"\n");
		}
		body = builder.toString();
		String[] firstLineSplit = firstLine.split("\\s");
		try{
			String statusString = firstLineSplit[0];
			statusCode = Integer.parseInt(statusString);
			if(firstLineSplit.length>=5){
				timeStamp = firstLineSplit[4];
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	

	public int getStatusCode(){
		return statusCode;
	}

	public boolean hadReply(){
		return hadReply;
	}
	
	public boolean is200(){
		return statusCode==200;
	}
	
	@Override
	public String toString() {
		return "statusCode= " + statusCode + ", firstline = " + firstLine + ", body=" + body;
	}
	
	
}
