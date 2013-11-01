package servlets; 

public class RangeCalculator {
	
	private static final double R = 3958.756;
	private double latMax;
	private double latMin;
	private double lngMax;
	private double lngMin;
	
	public RangeCalculator(double lat, double lng, double distance){
		
		if (distance > 100){
			distance = 100;
		}		
		double r = 0;
		r = distance/R;
		latMax = lat + r;
		latMin = lat - r;		

		double delta_lng = Math.asin(Math.sin(r)/Math.cos(lat));
		lngMax = Math.max(lng + delta_lng, lng - delta_lng);
		lngMin = Math.min(lng + delta_lng, lng - delta_lng);
	}
	
	public double getLatMax(){
		return latMax;
	}
	
	public double getLatMin(){
		return latMin;
	}
	
	public double getLngMax(){
		return lngMax;
	}
	
	public double getLngMin(){
		return lngMin;
	}
	
	public static void main(String[] args){
		try {
			AddressParser ap = new AddressParser("Philadelphia");

			RangeCalculator rc = new RangeCalculator(ap.getLat(),ap.getLong(), 5);

			System.out.println(rc.latMax);
			System.out.println(rc.latMin);
			System.out.println(rc.lngMax);
			System.out.println(rc.lngMin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
