package com.radware.appwall.old;

public class UserPass {

	public String user = "";
	public String pass = "";
	
	public UserPass(){
	}
	
	public UserPass(String user, String pass){
		this.user = user;
		this.pass = pass;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(!(obj instanceof UserPass)){
			return false;
		}
		UserPass other = (UserPass)obj;
		return user.equals(other.user) && pass.equals(other.pass);
	}
	
	@Override
	public int hashCode() {
		return user.hashCode()+pass.hashCode();
	}
	
	public boolean isEmpty(){
		return user.isEmpty() && pass.isEmpty();
	}
	
	@Override
	public String toString() {
		return "<"+user+","+pass+">";
	}
	
}
