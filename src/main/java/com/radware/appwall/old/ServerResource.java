package com.radware.appwall.old;



public abstract class ServerResource {

	String subSystem;
	String fileString;
	private ClientContent clientContent;
	


	public ClientContent getClientContent() {
		return clientContent;
	}

	public void setClientContent(ClientContent clientContent) {
		this.clientContent = clientContent;
	}

	public ServerResource(String subSystem, String fileString) {
		this.subSystem = subSystem;
		this.fileString = fileString;
	}

	protected String requestMethodForRefresh(){
		return "GETINFO";
	}
	
	protected String requestMethodForSave(){
		return "SETINFO";
	}
	
}
