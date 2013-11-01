package servlets;

public class GoogleMapDirectionsLink {
	
	private String url;
	
	public GoogleMapDirectionsLink(double lat1, double lat2, double lng1, double lng2){
		url = "http://maps.google.com/maps?saddr=" + lat1 + "," + lng1 +"&daddr=" + lat2 + "," + lng2;
	}
	
	public String getDirectionLink(){
		return url;
	}
	
//	public static void main(String[] args){
//		double[] latitudes = {39.957334,39.953057};
//		double[] longtitudes = {-75.252674,-75.281896};
//		
//		GoogleMapDirectionsLink gmdl = new GoogleMapDirectionsLink(latitudes,longtitudes);
//		
//		System.out.println(gmdl.url);		
//	}
//	
}
