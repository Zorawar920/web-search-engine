package web_search_engine;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import textprocessing.In;
import textprocessing.TrieST;


public class ExtractKeywords {
	private static HashMap<String, TrieST<Integer>> WordsMap = new HashMap<String, TrieST<Integer>>();


public static String HtmlConversion(String link,int i)
{  
	    String content = null;
	    File directoryPath = new File("data\\");
	    File filesList[] = directoryPath.listFiles(); 
	    
        String pattern = "^[a-zA-Z0-9]+";
        
        Pattern pt = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		
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
        	   				  String lowerCase = text.toLowerCase();
        	   				  st.append(lowerCase+",");
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
if(!WordsMap.containsKey(link)) {
	TrieST<Integer> st = new TrieST<Integer>();
    int i =1;
	for(String text : list) {
		st.put(text, i);
		i++;
	}
	WordsMap.put(link, st);
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
	    StoreDictionary();
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
				out.write(entry.getKey() +"::" + "{");
				for(var key : entry.getValue().keys()) {
					out.write(key + ",");
					// System.out.println(key);
				}
			out.write("}" + "\n");
			}
			out.flush();
			out.close();
			}
			catch (IOException e) {
			System.out.println("exception occoured" + e);
			}  
		  
	}
		


private static void StoreDictionary() {
    String LocFile = "keywords\\dictionary.dat";
    File file = new File(LocFile);
    try {
        BufferedWriter out = new BufferedWriter(
                      new FileWriter(file));
        out.close();
    }
    catch (IOException e) {
        System.out.println("Exception Occurred" + e);
    }
  
    HashSet<String> h = new HashSet<String>();
	for(var entry : WordsMap.entrySet()) {
		for(var key : entry.getValue().keys()) {
			h.add(key.toLowerCase());
		}
	}
	try {
		BufferedWriter out = new BufferedWriter(
		new FileWriter(file, true));
		for (String i : h) {
			out.write(i+"\n");
		}
		out.flush();
		out.close();
		}
		catch (IOException e) {
		System.out.println("exception occoured" + e);
		}

}
	
}


	

