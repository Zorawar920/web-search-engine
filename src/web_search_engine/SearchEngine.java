package web_search_engine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;
import textprocessing.TrieST;

public class SearchEngine {
	
  private static PriorityQueue<WebPageRanking> pQueue = new PriorityQueue<WebPageRanking>();

	public static void main(String[] args) {
		var map = LoadHashMap();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print("Type in to search:");
		String str = sc.nextLine();
		str=str.toLowerCase();
		
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while(st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
        RankWebPages(list, map);	
        var element = pQueue.peek();
        if(pQueue.size()!=0) {
        	System.out.print("The Top links for your search are : ");
        	for(var links : pQueue) {
        		 System.out.println(links.getKey());
        }
        }   
           else
        {
        	System.out.print("No links were found on your search. Here are a few suggestions for you to search on : \n");
        	SimilarWords(str);
        }
       

	}

	

	private static Map<String, TrieST<Integer>> LoadHashMap() {
	
		Map<String, TrieST<Integer>> map
        = new HashMap<String, TrieST<Integer>>();
       BufferedReader br = null;
		
       try {
    	   File file = new File("keywords\\keywordMap.dat");
    	   br = new BufferedReader(new FileReader(file));
    	   String line = null;
    	   while ((line = br.readLine()) != null) {
               String[] parts = line.split("::"); 
               String link = parts[0].trim();
               String[] words = parts[1].trim().split("[{},]");
               
               TrieST<Integer> st = new TrieST<Integer>();
               
               for(int i =0; i< words.length;i++) {
            	   st.put(words[i],i);
               }
               map.put(link, st);
       }
       }catch(Exception e) {
           e.printStackTrace();
       }
		return map;
	}

	private static void RankWebPages(ArrayList<String> str, Map<String, TrieST<Integer>> map) {
		
		int freq_score = 0;
		for(var entry : map.entrySet()) {
			for(var word : str) {
				
				if(entry.getValue().contains(word))
					freq_score++;
				
			}
			if(freq_score == str.size()) {
				WebPageRanking rank = new WebPageRanking(entry.getKey(), freq_score);
				pQueue.add(rank);
			}
			else if(freq_score>0) {
				WebPageRanking rank = new WebPageRanking(entry.getKey(), freq_score);
				pQueue.add(rank);			
			}
			freq_score = 0;
	}
		
	}
	
	private static void SimilarWords(String str) {
		ArrayList<String> dictionary =new ArrayList<String>();
	try (BufferedReader br = new BufferedReader(new FileReader("keywords\\dictionary.dat"))) {
	    while (br.ready()) {
	    	dictionary.add(br.readLine());
	    }
	br.close();
	}
	  catch (IOException e) {
	        System.out.println("Exception Occurred" + e);
	    }
	List<Integer> percentage_match = new ArrayList<Integer>();
    String output="";
    for (int i = 0; i < dictionary.size(); i++) {
        double p_similar=WordSuggestor.getDistanceDiff(str, dictionary.get(i));
        int pp_similar=(int) (p_similar*100);
        percentage_match.add((int) pp_similar);
    }
    //1st match
    int max = Collections.max(percentage_match);
    int index = percentage_match.indexOf(max);
    output+=dictionary.get(index)+"\n";
   
    percentage_match.set(index,0);
    //2nd match
    max = Collections.max(percentage_match);
    index = percentage_match.indexOf(max);
    output+=dictionary.get(index)+"\n";

    percentage_match.set(index,0);
    //3rd match
    max = Collections.max(percentage_match);
    index = percentage_match.indexOf(max);
    output+=dictionary.get(index)+"\n";
    System.out.println(output);
    	
}
	
	



}
