package com.radware.appwall.old;


public class EncryptDecript {

	private static final int SECRET_KEY = 18081981;
	private static final String ERROR = "#error#";
	
	public static String Decrypt (String str) {
		return Decrypt(str, SECRET_KEY);
	}
	
	public static String Decrypt (String str, long constEnc) {
		int[] enc_user = new int[str.length()];
		for ( int i=0 ; i<str.length() ; i++ ) {
			enc_user[i] = str.charAt(i);
		}
		
		if ( enc_user.length %4 != 1 ) {
			return ERROR;
		}

		long num = constEnc-12001;
		
		int stamp = 0x41 + (int)(num%26);
		if ( ( enc_user[0]+1 == stamp ) || 
			 ( (enc_user[0] == 0x41+63) && (stamp == 0x41) ) ) {
			--num;
		} else if ( enc_user[0] != stamp ) {
			return ERROR;
		}
		
		int iterator = 1;
		int offset = 0;
		StringBuffer sb = new StringBuffer();
		while ( iterator<enc_user.length ) {
			int encletter = (enc_user[1+offset]-0x41) + (enc_user[3+offset]-0x41)*26;
			int c = encletter^(int)(num%256);
			sb.append((char)c);
			num = ((num << 8 ) + (num >> 24 ) + 0xA9);
			num^=0x6ED9EBA1;
			num = Math.abs(num);
			offset+=4;
			iterator+=4;
		}
		return sb.toString();		
	}
	
	public static String Encrypt (String user) {
		return Encrypt(user, SECRET_KEY);
	}
	
	public static String Encrypt (String user, long constEnc) {
		long num = 0;
		int userLen = user.length();
		int[] enc_user = new int[userLen*4+1];

		num = constEnc-12001;

		int iterator = 0;
		int offset = 0;
		enc_user[offset] = 0x41+(int)(num%26);
		offset++;
		while ( iterator<userLen ) {
			char c = user.charAt(iterator);
			int cInt = c;
			int encletter = cInt ^ (int)(num%256);
			enc_user[0+offset] = 0x41 + (encletter%26);
			enc_user[1+offset] = 0x41+(int)(Math.random()*100)%26;
			enc_user[2+offset] = 0x41+(encletter/26);
			enc_user[3+offset] = 0x41+(int)(Math.random()*100)%26;
			num = ((num << 8 ) + (num >> 24 ) + 0xA9);
			num^=0x6ED9EBA1;
			num = Math.abs(num);
			offset+=4;
			iterator++;
		}
		
		StringBuffer sb = new StringBuffer();
		for ( int i=0 ; i<enc_user.length ; i++ ) {
			sb.append((char)enc_user[i]);
		}
		return sb.toString();
	}
	
}
