package com.radware.appwall.old;

public class AppWallUtil {

	public static AppwallResponse getReply(AppWall appwall, ServerResource serverResource, byte[] body, String timeStamp, UserPass userPass, boolean isRead) throws ResponseException {
		return getReply(appwall, serverResource, body, timeStamp, userPass, isRead, 0);
	}

	public static AppwallResponse getReply(AppWall appwall, ServerResource serverResource, byte[] body, String timeStamp, UserPass userPass, boolean isRead, int timeout) throws ResponseException {
		if (!CredentialsCache.map.containsKey(userPass)) {
			createCredentialsAndLogin(appwall, userPass);
		}
		String credentials = CredentialsCache.getCredentials(userPass);
		ConnectArgs args = new ConnectArgs(appwall, serverResource, isRead, userPass);
		args.setClientContent(serverResource.getClientContent());
		args.body = body;
		args.credentials = credentials;
		args.timeStamp = timeStamp;
		args.timeout = timeout;
		if (args.credentials == null) {
			createCredentialsAndLogin(appwall, userPass);
			args.credentials = CredentialsCache.getCredentials(userPass);

		}
		AppwallResponse reply = ConnectProtocol.analyzeReply(args);
		if (reply.getStatusCode() == 401 || reply.getStatusCode() == 405) {
			createCredentialsAndLogin(appwall, userPass);
			args.credentials = CredentialsCache.getCredentials(userPass);
			reply = ConnectProtocol.analyzeReply(args);
		}
		if (reply.is200()) {
			return reply;
		} else {
			//AppWallLogger.info(AppWallUtil.class, "APPWALL_UTIL_ERROR_LOG2x2", reply.getStatusCode(), reply.body);
			//AppWallLogger.info(AppWallUtil.class, "APPWALL_UTIL_CREDENTIALS_FOR_405x1", args);
			//AppWallLogger.info(AppWallUtil.class, "APPWALL_UTIL_ERROR_LOGx2", args, reply);
			throw new ResponseException(reply);
		}
	}

	private static void createCredentialsAndLogin(AppWall appwall, UserPass userPass) throws ResponseException {
		String credentials = Authenticate.createCredentials(appwall, userPass);
		ConnectionDetails newConnectionDetails = new ConnectionDetails();
		newConnectionDetails.credentials = credentials;
		CredentialsCache.map.put(userPass, newConnectionDetails);
		if (!userPass.isEmpty()) {
			String userId = login(appwall, userPass, credentials);
			CredentialsCache.map.get(userPass).loggedUserId = userId;
		}
	}

	private static String login(AppWall appwall, UserPass userPass, String credentials) throws ResponseException {
		String body = userPass.user + "\t" + EncryptDecript.Encrypt(userPass.pass);
		ServerResource serverResource = new LoginResource();
		ConnectArgs args = new ConnectArgs(appwall, serverResource, false, userPass);
		args.body = body.getBytes();
		args.credentials = credentials;
		AppwallResponse reply = ConnectProtocol.analyzeReply(args);
		if (!reply.is200()) {
			//AppWallLogger.info(AppWallUtil.class, "APPWALL_UTIL_ERROR_LOGINx4", args, userPass, credentials, reply
			//		.body);
			throw new ResponseException(reply);
		}
		return reply.body;
	}

}
