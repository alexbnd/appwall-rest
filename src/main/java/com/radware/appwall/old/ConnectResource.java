package com.radware.appwall.old;

public class ConnectResource extends ServerResource {

	public ConnectResource(String clearString){
		super("ADMIN", "key="+encriptString(clearString));		
	}
	
	private static String encriptString(String clear_text) {
		String s = "kavaDo@yehuda_halevy_101";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			String character = s.substring(i, i + 1);
			builder.append(clear_text.contains(character) ? "z" : "0");
		}
		return builder.toString();
	}
	
	@Override
	protected String requestMethodForSave() {
		return "CONNECT";
	}
	
}
