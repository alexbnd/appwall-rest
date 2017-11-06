package com.radware.appwall.old;


public class Authenticate {

	public static String createCredentials(AppWall remoteServer, UserPass userPass) throws ResponseException {
		AppwallResponse authenticationReply = doAuthenticate(remoteServer, userPass);
		if (!authenticationReply.is200()) {
			throw new ResponseException(authenticationReply);
		}
		int startIndex = authenticationReply.firstLine.indexOf(",");
		String token = authenticationReply.firstLine.substring(startIndex + 1);
		AppwallResponse connectReply = doConnect(token, remoteServer, userPass);
		if (!connectReply.is200()) {
			throw new ResponseException(connectReply);
		}
		String credentials = connectReply.firstLine.split("=")[1].trim();
		return credentials;
	}

	public static AppwallResponse doAuthenticate(AppWall remoteServer, UserPass userPass) throws ResponseException {
		ConnectArgs args = new ConnectArgs(remoteServer, new AuthenticateResource(), false, userPass);
		return ConnectProtocol.analyzeReply(args);
	}

	private static AppwallResponse doConnect(String clearString, AppWall remoteServer, UserPass userPass) throws ResponseException {
	    ConnectArgs args = new ConnectArgs(remoteServer, new ConnectResource(clearString), false, userPass);
	    return ConnectProtocol.analyzeReply(args);
	}
	
}
