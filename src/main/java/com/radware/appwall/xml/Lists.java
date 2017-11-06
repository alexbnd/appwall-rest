package com.radware.appwall.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Lists {

	public static <T> List<T> subList(List<T> input, int fromIndex, int toIndex){
		List<T> answer = new ArrayList<T>();
		for(int i=fromIndex ; i<toIndex; i++){
			answer.add(input.get(i));
		}
		return answer;
	}
	
	public static <T> boolean retainAll(Collection<T> list1, Collection<T> list2){
		List<T> toRemove = new ArrayList<T>();
		for(T iter : list1){
			if(!list2.contains(iter)){
				toRemove.add(iter);
			}
		}
		return list1.removeAll(toRemove);
	}
	
}
