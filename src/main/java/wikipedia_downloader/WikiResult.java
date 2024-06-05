package wikipedia_downloader;

public class WikiResult {
	private String query;
	private String text_result;
	private String img_url;
	
	
	
	public  WikiResult(String query, String text_result,String img_url)
	{
		this.query = query;
		this.text_result = text_result;
		this.img_url = img_url;
	}
	
	
	public String getText_result() 
	{
		return this.text_result;
	}
	
	
	
	public String getquery() 
	{
		return this.query;
	}
	
	
	public String getimg_url() 
	{
		return this.img_url;
	}
}

