# Wikipedia Downloader

Wikipedia Downloader is a simple Java application that allows you to download the content of Wikipedia pages using multithreading. It fetches the content of a given Wikipedia page by making an HTTP GET request and displays the raw HTML response.

## Features

- Multithreaded downloading of Wikipedia pages.
- Cleans and formats the keyword to create a valid Wikipedia URL.
- Simple HTTP GET request implementation.

## Requirements

- Java 8 or higher

## Usage

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/wikipedia_downloader.git
   cd wikipedia_downloader
# Code Structure
Wikipedia_Downloader.java
This is the main class that implements the Runnable interface. It handles:

Cleaning and formatting the keyword.
Generating the Wikipedia URL.
Making the GET request to fetch the Wikipedia page content.
Http_Url_Connection.java
This class contains a static method sendGet to make an HTTP GET request and return the response as a String.

# Example
To download the Wikipedia page for "Albert Einstein":

ExecutorService ex = Executors.newFixedThreadPool(20);
Wikipedia_Downloader wd = new Wikipedia_Downloader("Albert_Einstein");
ex.execute(wd);
ex.shutdown();


# Output
![image](https://github.com/Vsandeep1663/Wikipedia_Fetcher/assets/95342206/19986fdd-bc63-48ee-ad31-cf891488d773)

