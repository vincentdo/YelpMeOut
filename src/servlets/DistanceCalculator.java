package servlets;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class DistanceCalculator {
	
	private static final String PREFIX = "http://maps.googleapis.com/maps/api/directions/xml";
	private double lat1, lat2;
	private double lng1, lng2;
	private String drivingDistance;
	private String walkingDistance;

	public DistanceCalculator(double ilat1, double ilat2, double ilng1, double ilng2){	
		lat1 = ilat1;
		lat2 = ilat2;
		lng1 = ilng1;
		lng2 = ilng2;
		
		try {
			setDrivingDistance();
			setWalkingDistance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void setDrivingDistance() throws Exception{
		
		URL url = new URL(PREFIX + "?origin=" + lat1+","+lng1+ "&destination=" + lat2+","+lng2+ "&sensor=false&mode=driving");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		Document directionsResult = null;
		try {
			conn.connect();
			InputSource directionResultInputSource = new InputSource(conn.getInputStream());
			directionsResult = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(directionResultInputSource);
		} finally {
			conn.disconnect();
		}

		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList resultNodeList = null;

		resultNodeList = (NodeList) xpath.evaluate("//leg/distance/*", directionsResult, XPathConstants.NODESET);		
		for(int i=0; i<resultNodeList.getLength(); ++i) {
			Node node = resultNodeList.item(i);
			if("text".equals(node.getNodeName())) drivingDistance = node.getTextContent();			
		}		
	}
	
	public void setWalkingDistance() throws Exception{
		
		
		URL url = new URL(PREFIX + "?origin=" + lat1+","+lng1+ "&destination=" + lat2+","+lng2+ "&sensor=false&mode=walking");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		Document directionsResult = null;
		try {
			conn.connect();
			InputSource directionResultInputSource = new InputSource(conn.getInputStream());
			directionsResult = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(directionResultInputSource);
		} finally {
			conn.disconnect();
		}

		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList resultNodeList = null;

		resultNodeList = (NodeList) xpath.evaluate("//leg/distance/*", directionsResult, XPathConstants.NODESET);		
		for(int i=0; i<resultNodeList.getLength(); ++i) {
			Node node = resultNodeList.item(i);
			if("text".equals(node.getNodeName())) walkingDistance = node.getTextContent();
		}
		
		
	}
	
	public String getWalkingDistance(){
		return walkingDistance;		
	}
	
	public String getDrivingDistance(){
		return drivingDistance;
	}
//	
//	public static void main(String[] args){
//		double[] latitudes = {39.957334,39.953057};
//		double[] longtitudes = {-75.252674,-75.281896};
//		
//		DistanceCalculator dc = new DistanceCalculator(latitudes,longtitudes);
//		
//		System.out.println("Walking Distance: " + dc.walkingDistance);
//		System.out.println("Driving Distance: " + dc.drivingDistance);
//	}
}
