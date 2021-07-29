package web_search_engine;
import java.io.*;
import java.util.*;  


public class WordSuggestor {
	
	//Levenshtein distance algorithm given 2 strings finds their difference
	public static int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
	 
		return dp[len1][len2];
	}

	// This method is to assign a longer string as word1 so that always a positive percentage for difference can be calculated
	public static double getDistanceDiff(String s, String t) {
		 String longer = s, shorter = t;
	        if (s.length() < t.length()) { // longer should always have greater length
	            longer = t;
	            shorter = s;
	        }
	        int longerLength = longer.length();
	        if (longerLength == 0) {
	            return 1.0; /* both strings are zero length */ }
	        
	        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Type in a single word to suggest 3 similar words:");
		String str = sc.nextLine();
		str=str.toLowerCase();
		ArrayList<String> dictionary =new ArrayList<String>();
		//BufferedReader br = new BufferedReader(new FileReader("keywords\\dictionary.dat"));
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
            double p_match=getDistanceDiff(str, dictionary.get(i));
            int pp_match=(int) (p_match*100);
            percentage_match.add((int) pp_match);
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
        sc.close();
		
	}
}
