package com.radware.appwall.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SyntacticParser {
	
	private static final String TAG= "<[^<>]+>";
	private static final String TEXT= "[^<>]+";
	private static final String CDATA= "<!\\[CDATA\\[[\\s\\S]*?\\]\\]>";
	
	private static final String REMARK= "<!--[\\s\\S]*?-->";
	
	public static List<SyntaxPart> fromXml(String xml) throws ParseException{
		String noRemarks= removeRemarks(xml);
		List<SyntaxPart> list= new ArrayList<SyntaxPart>();
		Pattern cdataPattern = Pattern.compile(CDATA);
		Pattern tagPattern = Pattern.compile(TAG);
		Pattern textPattern = Pattern.compile(TEXT);
		Pattern pattern = Pattern.compile(CDATA+"|"+TAG+"|"+TEXT);
		Matcher matcher = pattern.matcher(noRemarks);
		while (matcher.find()) {			
			String group= matcher.group();
			Matcher cdataMatcher = cdataPattern.matcher(group);
			Matcher tagMatcher = tagPattern.matcher(group);
			Matcher textMatcher = textPattern.matcher(group);
			if(group.trim().isEmpty()){
				//do nothing
			}
			else if(cdataMatcher.matches()){
				String data= group.substring(9, group.length()-3);
				data= StringUtil.removeEscapes(data);
				list.add(new TextPart(data));
			}	
			else if(tagMatcher.matches()){
				String trimmed= matcher.group().trim();
				String body= trimmed.substring(1, trimmed.length()-1);
				if(body.startsWith("?") && body.endsWith("?")){
					list.add(new HeaderTag(body.substring(1, body.length()-1)));
				}	
				else if(body.startsWith("/")){
					list.add(new EndTag(body.substring(1, body.length())));
				}		
				else if(body.endsWith("/")){
					list.add(new EmptyTag(body.substring(0, body.length()-1)));
				}
				else{
					list.add(new StartTag(body));
				}
			}
			else if(textMatcher.matches()){
				group= StringUtil.removeEscapes(group);
				group= StringUtil.convertUnicode(group);
				list.add(new TextPart(group));
			}					
		}
		return joinTextParts(list);
	}
	
	private static List<SyntaxPart> joinTextParts(List<SyntaxPart> list){
		List<SyntaxPart> answer= new ArrayList<SyntaxPart>();
		for(SyntaxPart iter : list){			
			if(!(iter instanceof TextPart)){
				answer.add(iter);
			}
			else if(answer.isEmpty()){
				answer.add(iter);
			}	
			else{
				SyntaxPart last = answer.get(answer.size()-1);
				if(!(last instanceof TextPart)){
					answer.add(iter);
				}
				else{
					TextPart lastTextPart = (TextPart)last;
					String previousValue = lastTextPart.getText();
					lastTextPart.setText(previousValue+((TextPart)iter).getText());
				}
			}
		}
		return answer;
	}
	
	public static String removeRemarks(String s){
		Pattern pattern = Pattern.compile(REMARK);
		String answer = pattern.matcher(s).replaceAll("");
		return answer;
	}
	
}
