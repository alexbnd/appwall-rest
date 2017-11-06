package com.radware.appwall.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StringUtil {

	public static String removeEscapes(String input){
		String answer= new String(input);
		answer= answer.replaceAll("&quot;", "\"");
		//answer= answer.replaceAll("&apos;", "'");
		answer= answer.replaceAll("&lt;", "<");
		answer= answer.replaceAll("&gt;", ">");	
		answer= answer.replaceAll("&amp;", "&");
        return answer;
	}
	
	public static String escape(String input){
		String answer= new String(input);
		answer= answer.replaceAll("&", "&amp;");
		//answer= answer.replaceAll("'", "&apos;");
		answer= answer.replaceAll("<", "&lt;");
		answer= answer.replaceAll(">", "&gt;");				
        return answer;
	}
	
	public static String convertToAscii(String input){
		String answer= new String(input);
		Map<Character,String> replacementsMap= new HashMap<Character,String>();
		for(int i=0 ; i<input.length() ; i++){
			char c = input.charAt(i);
			int intValue = (int)c;
			if(intValue>255){
				replacementsMap.put(c, "&#"+intValue+";");
			}
		}
		for(Character iter : replacementsMap.keySet()){
			answer= answer.replaceAll(iter.toString(), replacementsMap.get(iter));
		}
        return answer;
	}
	
	public static String convertUnicode(String input){
		String answer= new String(input);
		Map<String,Character> replacementsMap= new HashMap<String,Character>();
		String unicodePattern = "&#\\d{2,4};";
		String hexUnicodePattern = "&#x[0-9a-fA-F]{2,4};";
		Pattern pattern = Pattern.compile(unicodePattern+"|"+hexUnicodePattern);
		Matcher matcher = pattern.matcher(answer);
		boolean foundAmpersent = false;
		while (matcher.find()) {
			String group= matcher.group();
			if(group.matches(unicodePattern)){
				String numberString= group.substring(2,group.length()-1);
				int number= Integer.parseInt(numberString);
				if(number == 38){
					foundAmpersent= true;
				}
				else{
					replacementsMap.put(group, (char)number);
				}
			}
			else if(group.matches(hexUnicodePattern)){
				String numberString= group.substring(3,group.length()-1);
				int number= Integer.parseInt(numberString, 16);
				replacementsMap.put(group, (char)number);
			}
		}
		for(String key : replacementsMap.keySet()){
			answer= answer.replaceAll(key, replacementsMap.get(key).toString());
		}
		if(foundAmpersent){
			answer= answer.replaceAll("&#38;", "&");
		}
        return answer;
	}		
	
}
