package application;

public class ParsingThread implements Runnable{

	private String path;
	private String xml;
	public ParsingThread(String path, String xml) {
		this.path = path;
		this.xml = xml;
	}
	@Override
	public void run() {
		System.out.println("Thread running.");
		ParseAndDownload pd = new ParseAndDownload();
		pd.parseAndDownload(path, xml);
	}

}
