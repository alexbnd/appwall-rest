package com.radware.appwall.xml;


import java.util.ArrayList;
import java.util.List;

class Element {

	private String name;
	private String value="";
	private List<Attribute> attributes;
	private List<Element> children;

	public String getAttribute(String key){
		for(Attribute iter : attributes){
			if(iter.getKey().equals(key)){
				return iter.getValue();
			}
		}
		return null;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public List<Attribute> getAttributes(){
		return attributes;
	}
	
	public Element getChild(String text){
		Element answer= null;
		for(Element iter : children){
			if(iter.getName().equals(text)){
				answer = iter;
			}
		}
		return answer;
	}
	
	public String getLeafValue(String name){
		Element leaf= getChild(name);
		if(leaf==null){
			return null;
		}
		return leaf.getValue();
	}
	
	public List<String> getLeafValues(String name){
		List<Element> list= getChildren(name);
		List<String> answer= new ArrayList<String>();
		for(Element iter : list){
			answer.add(iter.getValue());
		}
		return answer;
	}
	
	public String getLeafValue(String name, String def){
		Element leaf= getChild(name);
		if(leaf==null){
			return def;
		}
		return leaf.getValue();
	}
	
	public String getValue(){
		return value;
	}
	
	public List<Element> getChildren(String text){
		List<Element> answer= new ArrayList<Element>();
		for(Element iter : children){
			if(iter.getName().equals(text)){
				answer.add(iter);
			}
		}
		return answer;
	}
	
	public Element(String name, String value){
		this.name= name;
		this.value= value;
		children= new ArrayList<Element>();
		attributes= new ArrayList<Attribute>();
	}
	
	public Element(List<SyntaxPart> syntacticList) throws ParseException{
		children= new ArrayList<Element>();
		attributes= new ArrayList<Attribute>();
		if(syntacticList.isEmpty()){
			throw new ParseException();
		}
		if(!(syntacticList.get(0) instanceof StartInterface)){
			throw new ParseException();
		}
		if(!(syntacticList.get(syntacticList.size()-1) instanceof EndInterface)){
			throw new ParseException();
		}
		StartInterface start= (StartInterface)syntacticList.get(0);
		EndInterface end= (EndInterface)syntacticList.get(syntacticList.size()-1);
		if(!start.getTag().equals(end.getTag())){
			throw new ParseException();
		}
		name= start.getTag();	
		attributes.addAll(start.getAttributes());
		if(syntacticList.size()==1){			
			SyntaxPart part= syntacticList.get(0);
			if(!(part instanceof EmptyTag)){
				throw new ParseException();
			}
		}
		else if(syntacticList.size()==2){	
			return;
		}
		else if(syntacticList.size()==3){			
			SyntaxPart middlePart= syntacticList.get(1);
			if(middlePart instanceof TextPart){
				TextPart textPart= (TextPart)middlePart;
				value= textPart.getText();
			}
			else if(middlePart instanceof EmptyTag){
				EmptyTag emptyTag= (EmptyTag)middlePart;
				Element newEmptyElement= new Element(emptyTag.getTag(), "");
				for(Attribute iter : emptyTag.getAttributes()){
					newEmptyElement.addAttribute(iter);
				}
				getChildren().add(newEmptyElement);
			}
			else{
				throw new ParseException();
			}
		}
		else{												
			int fromIndex = 1;
			List<List<SyntaxPart>> subListsList= new ArrayList<List<SyntaxPart>>();
			while(fromIndex < syntacticList.size()-1){
				int toIndex = getEndOfNode(syntacticList, fromIndex);
				List<SyntaxPart> subList= new ArrayList<SyntaxPart>(Lists.subList(syntacticList, fromIndex, toIndex+1));
				subListsList.add(subList);
				fromIndex= toIndex+1;				
			}
			for(List<SyntaxPart> iter : subListsList){
				children.add(new Element(iter));
			}
		}
	}

	private int getEndOfNode(List<SyntaxPart> syntacticList, int fromIndex) throws ParseException{
		SyntaxPart part= syntacticList.get(fromIndex);
		if(part instanceof TextPart || part instanceof EmptyTag){
			return fromIndex;
		}
		if(!(part instanceof StartTag)){
			throw new ParseException();
		}
		StartTag startTag= (StartTag)part;
		for(int toIndex=fromIndex ; toIndex<syntacticList.size() ; toIndex++){
			SyntaxPart iter = syntacticList.get(toIndex);
			if(iter instanceof EndTag){
				EndTag endTag= (EndTag)iter;
				if(endTag.getTag().equals(startTag.getTag())){
					return toIndex;
				}
			}
		}
		throw new ParseException(startTag.getTag()+" does not have an enclosing tag.");
	}
	
	public void addAttribute(Attribute attribute){
		attributes.add(attribute);
	}
	
	public String getName() {
		return name;
	}

	List<Element> getChildren(){
		return children;
	}
	
	@Override
	public String toString() {
		return "<"+name+">";
	}
	
}
