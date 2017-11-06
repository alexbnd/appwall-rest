package com.radware.appwall.old;

public class LoginResource extends ServerResource {

	public LoginResource(){
		super("ADMIN", "LOGIN");		
	}
	
	@Override
	protected String requestMethodForSave() {
		return "OPERATION";
	}

}
