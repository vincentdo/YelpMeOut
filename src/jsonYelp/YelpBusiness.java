package jsonYelp;

import java.util.ArrayList;

public class YelpBusiness {
	
	public String business_id, full_address, open, url, photo_url, city, name, state;
	public int review_count;
	public double stars, longitude, latitude;
	
	public ArrayList<String> schools = new ArrayList<String>();
	public ArrayList<String> categories = new ArrayList<String>();
	public ArrayList<String> neighborhoods = new ArrayList<String>();
	
	public YelpBusiness(String b, String a, String o, String u, String pu, String c, String n, String st, int r, double s, double lo, double la){
		business_id = b;
		full_address = a;
		open = o;
		url = u;
		photo_url = pu;
		city = c;
		name = n;
		state = st;
		review_count = r;
		stars = s;
		longitude = lo;
		latitude = la;
	}
	
	public void addSchool(String s){
		if(!schools.contains(s))
			schools.add(s);
		
	}
	
	public void addCategory(String c){		
		if(!categories.contains(c))
			categories.add(c);
	}
	
	public void addNeighborhood(String n){
		if(!neighborhoods.contains(n))
			neighborhoods.add(n);
	}
}
