package com.radware.appwall.old;

import java.util.HashMap;
import java.util.Map;

public class CredentialsCache {

	public static Map<UserPass, ConnectionDetails> map = new HashMap<UserPass, ConnectionDetails>();

	public static String getCredentials(UserPass userPass) {
		ConnectionDetails details = map.get(userPass);
		if (details == null || details.credentials == null || details.credentials.isEmpty() || details.loggedUserId.isEmpty() || details.loggedUserId == null) {
			return null;
		} else {
			return details.credentials;
		}
	}

}
