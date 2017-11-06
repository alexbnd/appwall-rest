package com.radware.appwall.old;

import java.util.Arrays;

public class ConnectArgs {

	public String host;
	public int port;
	public String method;
	public String subSystem;
	public String version;
	public String credentials = "";
	public String timeStamp;
	public String fileString;
	public byte[] body = new byte[0];
	private ClientContent clientContent;
	public int timeout = 0;
	

	public ClientContent getClientContent() {
		return clientContent;
	}

	public void setClientContent(ClientContent clientContent) {
		this.clientContent = clientContent;
	}

	public ConnectArgs(AppWall appWall, ServerResource serverResource, boolean isRead, UserPass userPass){
		this.host = appWall.host;
		this.port = appWall.port;
		this.version = DDFinals.SUPPORTED_SERVER_VERSION;
		this.method = getMethod(appWall, serverResource, subSystem, isRead, userPass);
		this.subSystem = getSubSys(appWall, serverResource);
		this.fileString = serverResource.fileString;
	}
	
	private static String getMethod(AppWall appWall, ServerResource serverResource, String subSystem, boolean isRead, UserPass userPass) {
        String simpleMethod = isRead ? serverResource.requestMethodForRefresh() : serverResource.requestMethodForSave();
        if(!appWall.isMemberOfCluster()){
        	return simpleMethod;
        }
        else{
        	if(appWall.isBeingAddedToCluster()){
        		return "GETPROX2 " + subSystem + " " + simpleMethod + " " + getCommunicationIDString(appWall, userPass);
        	}
        	else{
        		return "GETPROX3 "+ subSystem + " " + simpleMethod + " " + appWall.host;
        	}
        }
    }
	
	private static String getCommunicationIDString(AppWall node, UserPass userPass) {
    	String clearloginName= userPass.user;
    	String clearPassword= userPass.pass;
		String isSSL= "0";
		String retVal= node.host + " " + node.port + " " + isSSL + " " + clearloginName + " " + EncryptDecript.Encrypt(clearPassword);
		return retVal;
	}
	
	private static String getSubSys(AppWall appWall, ServerResource serverResource){
		if (appWall.isMemberOfCluster()) {
            return "CLUSTER";
        }
        return serverResource.subSystem;
	}

	@Override
	public String toString() {
		return "ConnectArgs{" + "host='" + host + '\'' + ", port=" + port + ", method='" + method + '\'' +
				", subSystem='" + subSystem + '\'' + ", version='" + version + '\'' + ", credentials='" + credentials +
				'\'' + ", timeStamp='" + timeStamp + '\'' + ", fileString='" + fileString + '\'' + ", body=" +
				Arrays.toString(body) + ", clientContent=" + clientContent + ", timeout=" + timeout + '}';
	}
}
