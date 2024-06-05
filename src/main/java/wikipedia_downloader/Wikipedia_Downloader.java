package wikipedia_downloader;

import java.awt.Image;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Wikipedia_Downloader implements Runnable {

    private String keyword;
    

    // Constructor to initialize the keyword
    public Wikipedia_Downloader(String keyword) {
        this.keyword = keyword;
    }

    // The main method to run the Wikipedia Downloader
    public void run() {
        // Step 2: Clean user input
        if (this.keyword == null || this.keyword.length() == 0) {
            return;
        }

        // Replace spaces with underscores to format the keyword for a Wikipedia URL
        this.keyword = this.keyword.trim().replaceAll("[ ]+", "_");
        
        // Step 3: Generate the Wikipedia URL for the given keyword
        String wikiurl = getWikipediaUrlForQuery(this.keyword);
        String response = "";
        String img_url = null;

        try {
            // Step 3: Make a request to Wikipedia
            String wikipediaResponse = Http_Url_Connection.sendGet(wikiurl);
           
            
            // Step 4: Parse the Wikipedia response
            Document document = Jsoup.parse(wikipediaResponse, "https://en.wikipedia.org");

            // Select the elements with relevant content
            Elements childElements = document.body().select(".mw-parser-output > *");
            int state = 0;
            for (Element childElement : childElements) {
                if (state == 0) {
                    if (childElement.tagName().equals("table")) {
                        state = 1;
                    }
                } else if (state == 1) {
                    if (childElement.tagName().equals("p")) {
                        response = childElement.text();
                        break;
                    }
                }
            }

            // Attempt to retrieve the image URL from the infobox
            try {
                img_url = document.body().select(".infobox img").get(0).attr("src");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Ensure the image URL starts with "https:"
        if (img_url != null && img_url.startsWith("//")) {
            img_url = "https:" + img_url;
        }

        // Step 6: Show relevant results
        WikiResult wk = new WikiResult(this.keyword, response, img_url);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(wk);
        System.out.println(json);

        // Attempt to download the image
        Image image = null;
        try {
            URL url = new URL(img_url);
            image = ImageIO.read(url);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    // Method to generate the Wikipedia URL for a given keyword
    private String getWikipediaUrlForQuery(String cleanKeyword) {
        return "https://en.wikipedia.org/wiki/" + cleanKeyword;
    }

    // The main method to start the Wikipedia Downloader
    public static void main(String[] args) {
        // Step 1: Get input from the user
        ExecutorService executor = Executors.newFixedThreadPool(20);
        Scanner src = new Scanner(System.in);
        System.out.println("Enter the keyword to search on Wikipedia:");
        String user_input = src.nextLine();
        src.close();
        
        // Create and execute a Wikipedia_Downloader instance with the user input
        Wikipedia_Downloader wd = new Wikipedia_Downloader(user_input);
        executor.execute(wd);
        executor.shutdown();
    }
}
