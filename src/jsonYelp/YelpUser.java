package jsonYelp;

public class YelpUser {
	
	public YelpVotes votes;
	public String user_id, name, url, type;
	public double average_stars;
	public int review_count;

	public YelpUser(YelpVotes v, String id, String n, String u, Double a, int r, String t){
		votes = v;
		user_id = id;
		name = n;
		url = u;
		average_stars = a;
		review_count = r;
		type = t;
	}
}