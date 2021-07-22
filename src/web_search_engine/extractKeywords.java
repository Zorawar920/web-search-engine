package web_search_engine;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import textprocessing.In;


public class extractKeywords {
	
	private static HashMap<String, HashMap<String, Integer>> WordsMap = new HashMap<String, HashMap<String, Integer>>();
	
public static String HtmlConversion(String link,int i)
{  
	    String content = null;
	    File directoryPath = new File("data\\");
	    File filesList[] = directoryPath.listFiles(); 
	    
        String pattern = "^[a-zA-Z0-9]+";
        
        Pattern pt = Pattern.compile(pattern);
		
		StringBuilder st = new StringBuilder();
		
        String file = "data/" + i + ".txt";
        String checkfile = "data\\"+i+".txt";
        Scanner inFile;
        try {
        	
        	for(File txtFiles : filesList) {
        		
        		if(txtFiles.getPath().equals(checkfile)) {
        			
        			inFile = new Scanner(new FileReader(file));
        			
        	           while(inFile.hasNext()) {
        	        	   
        	        	   String name = inFile.next();
        	        	   Matcher m = pt.matcher(name);
        	    			
        	        	   if(m.find()) {
        	   				  String text = m.group();
        	   				  st.append(text+",");
        	    			}
        	           }
        		}
        	}
           
           content = st.toString();
     
      } catch (FileNotFoundException ex) {
          ex.printStackTrace();
      }
        return content;

}
public static ArrayList<String> extractTextWords(String content) {
	ArrayList<String> list = new ArrayList<String>();
	String[] split = content.split(",");
	
	for(String text : split) {
		list.add(text);
	}
	
	return list;
}

private static void StoreWordOccurences(String link, ArrayList<String> list) {
	
	for(String text : list) {
		if(WordsMap.containsKey(link)) {
			HashMap<String, Integer> innerMap = WordsMap.get(link);
			
			if(innerMap.containsKey(text)) {
				int freq = innerMap.get(text);
				freq++;	
				innerMap.put(text, freq);
			}else {
				innerMap.put(text, 1);
			}
			WordsMap.put(link, innerMap);
		}
		else {
			HashMap<String, Integer> innerMap = new HashMap<String, Integer>();
			innerMap.put(text, 1);
			WordsMap.put(link, innerMap);
		}
	}
	
}
	public static void main(String[] args) {
		ArrayList<String> links = new ArrayList<String>();
		In in = new In("urlLinks\\urllist.txt");
		while(!in.isEmpty()) {
			links.add(in.readLine());
		}
		in.close();
		
		int i = 1;
		for(String link : links) {
			 String content = HtmlConversion(link, i);
			 ArrayList<String> arr = extractTextWords(content);
			 StoreWordOccurences(link, arr);
			 i++;
		}
	    StoreKeyWords();
	}
	
	private static void StoreKeyWords() {
	    String LocFile = "keywords\\keywordMap.dat";
	    File file = new File(LocFile);
	    
		  try {
	            BufferedWriter out = new BufferedWriter(
	                          new FileWriter(file));
	             out.close();
	        }
	        catch (IOException e) {
	            System.out.println("Exception Occurred" + e);
	        }
		  
		  
		  try {
			BufferedWriter out = new BufferedWriter(
			new FileWriter(file, true));
			for(var entry : WordsMap.entrySet()) {
				out.write(entry.getKey() + " " + entry.getValue());
			}
			out.flush();
			out.close();
			}
			catch (IOException e) {
			System.out.println("exception occoured" + e);
			}  
		  
	}
		
}


