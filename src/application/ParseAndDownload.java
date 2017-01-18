package application;

import java.io.FileOutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ParseAndDownload {

	public synchronized void parseAndDownload(String path, String xml){
		System.out.println("parsing and downloading...");
		System.out.println(path);
		System.out.println(xml);
		Document d = parseXml(xml);
		if(d != null){
			System.out.println("Not null");
			try{
				Thread.sleep(200);
			}catch(Exception e){
				e.printStackTrace();
			}
			Element root=d.getDocumentElement();
			NodeList nodes = root.getChildNodes();
			NodeList nodesGroupId = root.getElementsByTagName("groupId");
			System.out.println("nodesGroupId len: " + nodesGroupId.getLength());
			String groupId = nodesGroupId.item(0).getTextContent();
			System.out.println("Groupid = " + groupId);

			NodeList nodesArtifactId = root.getElementsByTagName("artifactId");
			String artifactId = nodesArtifactId.item(0).getTextContent();
			System.out.println("artifactId: " + artifactId);

			NodeList nodesVersion = root.getElementsByTagName("version");
			String version = nodesVersion.item(0).getTextContent();
			System.out.println("version: " + version);

			download(groupId, artifactId, version, path);
		}else{
			System.out.println("Null");
		}

	}

	private synchronized Document parseXml(String xml){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		InputSource is = null;
		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(xml));
			return builder.parse(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;


	}

	private void download(String gid, String aid, String v, String path){
		String url = "http://repo1.maven.org/maven2/";
		gid = gid.replaceAll("\\.", "/");
		url += gid;
		url += "/" + aid;
		url += "/" + v;
		url += "/" + aid + "-" + v + ".jar";
		System.out.println(url);
		try {
			URL dlURL = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(dlURL.openStream());
			FileOutputStream fos = new FileOutputStream(path + "\\" + aid + "-" + v + ".jar");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
