package jsonYelp;

public class YelpReview {
	
	public YelpVotes votes;
	public String user_id, review_id, business_id, date;
	public int stars;

	public YelpReview(YelpVotes v, String u, String r, String b, int s, String d){
		votes = v;
		user_id = u;
		review_id = r;
		business_id = b;
		stars = s;
		date = d;
	}
	
}
