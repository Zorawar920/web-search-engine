package web_search_engine;
import java.io.*;
import org.jsoup.Jsoup;
import textprocessing.*;

public class genTextFilefrmUrl {
	public static void main(String[] args) 
		{
		genTextFilefrmUrl fr= new genTextFilefrmUrl();  
		In in = new In("urlLinks\\urllist.txt");
			int i=1;
	        while (!in.isEmpty()) {
	        	
	        	String line = in.readLine();
	        	    //System.out.println("fetched :" +line );
	                try {
	                fr.HtmlConversion(line,i);}
	                catch (IOException e) {
	    	            System.out.println("Exception Occurred" + e);
	    	            continue;
	                }
	                i++;
			     }
	           
	         	
		}
	
					public void HtmlConversion(String link,int i) throws IOException
		{
		     		org.jsoup.nodes.Document doc1 = Jsoup.connect(link).get();
		     		//org.jsoup.nodes.Document doc = Jsoup.parse(doc1,"UTF-8");
		     		String Txt=doc1.text();
		     		PrintWriter out = new PrintWriter("data\\" +i+".txt");
		   			out.println(Txt);	     			
		   			out.close();
	     		
		    
		}



}
