package com.radware.appwall.old;

public class AppWall {
		
	public String host = "127.0.0.1";
	public int port = 8200;
	private boolean memberOfCluster = false;
	private boolean isBeingAddedToCluster= false;

	public AppWall(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	public boolean isMemberOfCluster(){
		return memberOfCluster;
	}
	
	public boolean isBeingAddedToCluster(){
		return isBeingAddedToCluster;
	}
	
	public void setBeingAddedToCluster(boolean isAddedToCluster) {
		this.isBeingAddedToCluster = isAddedToCluster;
	}
	
}
