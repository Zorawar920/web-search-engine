package web_search_engine;
import org.jsoup.Jsoup;
import java.io.*;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

public class crawler {
    private static final int MAX_DEPTH = 3;
    private static final int max_urls=20;
    private HashSet<String> links;

    public crawler() {
        links = new HashSet<>();
    }
    
    public void getPageLinks(String URL, int depth) {
    	     if ((!links.contains(URL) && (depth < MAX_DEPTH) && links.size() < max_urls)) {
            //System.out.println(">> Depth: " + depth + " [" + URL + "]");
            appendStrToFile("data\\urllist.txt",URL+"\n");
             try {
                links.add(URL);

                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");

                depth++;
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }
    
    
    public static void appendStrToFile(String fileName,
            String str)
{
try {

// Open given file in append mode.
BufferedWriter out = new BufferedWriter(
new FileWriter(fileName, true));
out.write(str);
out.close();
}
catch (IOException e) {
System.out.println("exception occoured" + e);
}
}

    public static void main(String[] args) {
    	
    	String LocFile="data\\urllist.txt";
		File fil = new File(LocFile);
		  try {
	            BufferedWriter out = new BufferedWriter(
	                          new FileWriter(fil));
	             out.close();
	        }
	        catch (IOException e) {
	            System.out.println("Exception Occurred" + e);
	        }
        new crawler().getPageLinks("http://www.w3.org/", 0);
    }
}
    
