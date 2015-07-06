package com.twitter.analyzer;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Arun Velagapalli
 *This class provides a HashMap to count the occurences of a word in tweets, 
 *using Treemap makes the map structure ordered by ASCII values (string).
 *
 */
public class WordCounter {
	private Map<String,Integer> wordCountMap; //map datastructure to hold the words and their counts;

	public WordCounter(){
		this.wordCountMap=new TreeMap<String, Integer>();
	}
	
	public Map<String, Integer> getWordCountMap() {
		return wordCountMap;
	}
	public void setWordCountMap(Map<String, Integer> wordCountMap) {
		this.wordCountMap = wordCountMap;
	}
	/**
	 * 
	 * @param wordsinTweet
	 * put words into map and increment if already exists in map
	 */
	public void countWords(String[] wordsinTweet){
		for(String word:wordsinTweet){
			Integer currentCount= this.wordCountMap.get(word);
			if(currentCount==null){
				currentCount=0;
			}
			this.wordCountMap.put(word, currentCount+1); 
		}
	}
	
	public Map<String,Integer> getWordCount() throws Exception{
		return this.wordCountMap;
	}
		
}
