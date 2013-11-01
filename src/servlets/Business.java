package servlets;

public class Business {
	private String name;
	private String address;
	private double review_count, stars, longitude, latitude, weight;
	
	public Business(String n){
		name = n;
	}
	
	public Business(double lo, double la){
		longitude = lo;
		latitude = la;
	}
	
	public Business(String n, String a, int r, double st, double lo, double la) {
		name = n;
		address = a;
		review_count = r;
		stars = st;
		longitude = lo;
		latitude = la;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public double getReviewCount() {
		return review_count;
	}

	public double getStars() {
		return stars;
	}

	public double getLongitude() {
		return longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getWeight(){
		return weight;
	}
	
}
