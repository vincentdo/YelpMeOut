package servlets;

public class StaticMapGenerator {
	
	private String url;
	private static final String PREFIX = "http://maps.googleapis.com/maps/api/staticmap?size=400x400&maptype=roadmap";
	
	public StaticMapGenerator(double[] latitudes, double[] longitudes){
		String sensorStatus = "&sensor=false";
		char label = 'A';
		String markers = "\\&markers=color:red"+"%7Clabel:"+label+"%7C"+latitudes[0]+","+longitudes[0];
		label++;
		for (int i = 1; i<longitudes.length; i++){
			markers = markers +"&markers=color:red"+"%7Clabel:"+label+"%7C"+latitudes[i]+","+longitudes[i];
			label++;
		}
		url = PREFIX + markers + sensorStatus;
	}
	
	public String getMapUrl(){
		return url;
	}
	
	public static void main(String[] args){
		double[] latitudes = {39.957334,39.953057, 39.957902};
		double[] longtitudes = {-75.252674,-75.281896,-75.3076883};
		
		StaticMapGenerator map = new StaticMapGenerator(latitudes,longtitudes);
		
		System.out.println(map.getMapUrl());		
	}
}
