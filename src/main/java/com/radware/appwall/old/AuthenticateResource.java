package com.radware.appwall.old;

public class AuthenticateResource extends ServerResource {

	public AuthenticateResource(){
		super("ADMIN", "12345");
	}
	
	@Override
	protected String requestMethodForSave() {
		return "AUTHENTICATE";
	}
	
}
