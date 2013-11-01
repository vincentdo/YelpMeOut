package servlets;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class AddressParser {
	
	private static final String PREFIX = "http://maps.googleapis.com/maps/api/geocode/xml";
	private double lng;
	private double lat;

	public AddressParser(String address) throws Exception{		
		lat = 0;
		lng = 0;

		URL url = new URL(PREFIX + "?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=false");
		
		System.out.println(url);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		Document geocoderResultDocument = null;
		try {
			conn.connect();
			InputSource geocoderResultInputSource = new InputSource(conn.getInputStream());
			geocoderResultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(geocoderResultInputSource);
		} finally {
			conn.disconnect();
		}

		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList resultNodeList = null;

		resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result[1]/geometry/location/*", geocoderResultDocument, XPathConstants.NODESET);		
		for(int i=0; i<resultNodeList.getLength(); ++i) {
			Node node = resultNodeList.item(i);
			if("lat".equals(node.getNodeName())) lat = Double.parseDouble(node.getTextContent());
			if("lng".equals(node.getNodeName())) lng = Double.parseDouble(node.getTextContent());
		}
	}
	
	public double getLong(){
		return lng;
	}
	
	public double getLat(){
		return lat;
	}

	/*
	public static void main(String[] args){
		String address = "69th Street Terminal, Upper Darby";
		
		AddressParser ap;
		try {
			ap = new AddressParser(address);
			System.out.println(ap.lng + ", " + ap.lat);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	*/

}
