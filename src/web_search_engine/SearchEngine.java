package web_search_engine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
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
		
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while(st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
        RankWebPages(list, map);	
        var element = pQueue.peek();
        for(var links : pQueue) {
        	 System.out.println(links.getKey());
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



}
