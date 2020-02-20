package models;

public class TestConfig {
	
	private String browser;
	private String url;
	private String os;
	
	public TestConfig() {
		
	}
	
	public TestConfig(String browser, String url, String os) {
		this.browser = browser;
		this.url = url;
		this.os = os;
	}
	
	public String getBrowser()
	{
		return browser;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public String getOS()
	{
		return os;
	}
}
