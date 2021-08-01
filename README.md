# web-search-engine
Web Search Engine along with crawler is developed in Java.
A simple search engine which is based on the occurence of the key words in the text files.

**Project Components:**

--> Imported Packages : Text Processing

--> Web crawler: Crawler.java

--> Data Folders: data, keywords,urlLinks

--> Text Files: All the files generated in the Folder

--> Java File: GenTextFilefrmUrl.java - Code to parse URLs to text files.

--> Jave File: ExtractKeywords.java - Code to get the keywords and store in a trie from all the downloaded text files & 
to create a dictionary for word suggestions 

--> Jave File: WebPageRanking.java -

--> Jave File: StoreHashMap.java -

--> Jave File: WordSuggestor.java - Code to implement the word edit distances aglorithm and find the percent difference for each set of words.

--> Jave File: SearchEngine.java -- Driver Code that uses all the above files to build the search engine.

**Concepts Used:**

* Ternary Search Trie
* Hash Maps and Hash Sets
* Priority Queues
* Text Processing (JSoup, String Functions)
* Regex Processing
* Memory Management (Caching and Files)
* Levenshtein edit distance algorithm


**Flow of Execution of the Search Engine:**

* Use of  web crawler to crawl the web and recursively retreive around 1000 URLs.
* Each URL is parsed to a text file using JSoup.
* All URLs are indexed into a Hash Map.
* TST is generated for each text file and occurence of keywords are extracted.The generated data is stored in memory to implement cache and 
drastically improve search time.
* Stop words are removed from the Search String given by the user.
* String is converted to token using Java String Tokenizer.
* To implement page ranking, occurence of these words along with the URL index are stored in the Priority Queues.
* If pages are found ,results are printed else Similar word suggestions are generated.

Search Results
![image](https://user-images.githubusercontent.com/83388538/127771582-13389387-cd20-4fda-9472-dd86f98ff940.png)

![image](https://user-images.githubusercontent.com/83388538/127771598-250ecc88-ac3c-43f5-87dd-eea28e8cb667.png)

