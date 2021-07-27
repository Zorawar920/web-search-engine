package web_search_engine;

public class WebPageRanking implements Comparable<WebPageRanking> {
    
	private String key;
    private int value;
	
    public String getKey() {
    	return this.key;
    }
    
    public WebPageRanking(String key, int value) {
        this.key = key;
        this.value = value;
    }
   
	@Override
	public int compareTo(WebPageRanking other) {
		return other.value - this.value;
	}
}
