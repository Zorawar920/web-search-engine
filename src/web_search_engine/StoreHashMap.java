package web_search_engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import textprocessing.In;

public class StoreHashMap {
	
	private HashMap<Integer, String> urlIndex;
	  
	public static void main(String[] args) {
		StoreHashMap sm= new StoreHashMap();
		sm.storeUrlIndex();
	}
	private void storeUrlIndex() {
		String LocFile="hashMapUrl\\urlMap.dat";
		File file = new File(LocFile);
		  try {
	            BufferedWriter out = new BufferedWriter(
	                          new FileWriter(file));
	             out.close();
	        }
	        catch (IOException e) {
	            System.out.println("Exception Occurred" + e);
	        }
		  
		  In in = new In("urlLinks\\urllist.txt");
		  urlIndex = new HashMap<Integer, String>();
		  int i =1;
		  while(!in.isEmpty()) {
			  String line = in.readLine();
			  urlIndex.put(i, line);
			  i++;
			  
		  }
		  
		  try {
			BufferedWriter out = new BufferedWriter(
			new FileWriter(file, true));
			for(var entry : urlIndex.entrySet()) {
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
