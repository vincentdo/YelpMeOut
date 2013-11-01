package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class YelpDB {

	public static final String URL = "jdbc:mysql://localhost:3306/YelpMeOut";
	public static final String usr = "yelp";
	public static final String pwd = "cis330";

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	public YelpDB() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, usr, pwd);
			stmt = conn.createStatement();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet getResult(String sql) {
		try {
			if (stmt.execute(sql))
				rs = stmt.getResultSet();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return rs;
	}

	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void getValues(String cate, boolean pop, boolean rat, double lngMax, double lngMin, double latMax, double latMin, ArrayList<Business> biz) throws SQLException{
		String category = cate;
		/*
		double lngMax = -75.197005;
		double lngMin = -75.200994;
		double latMax = 39.956863;
		double latMin = 39.954336;
		*/
		boolean rating = rat;
		boolean popularity = pop;
		String createView = "create VIEW current_view as "
				+ "select business.business_id, name, full_address, photo_url, stars, review_count, longitude, latitude "
				+ "from business, businesscategory "
				+ "where business.business_id = businesscategory.business_id "
				+ "AND businesscategory.category = \"" + category + "\" "
				+ "AND longitude <= " + lngMax + " "
				+ "AND longitude >= " + lngMin + " "
				+ "AND latitude <= " + latMax + " "
				+ "AND latitude >= " + latMin + " ";
		String prefixQuery = "SELECT * from current_view WHERE ";
		String starQuery = "stars >= (SELECT Max(stars) as maxrating "
												  + "FROM current_view) - 0.5 ";
		String and = "AND ";
		String reviewCountQuery = "review_count >= (SELECT Max(review_count) as maxPopularity "
														+ " FROM current_view) - 0.2*(SELECT Max(review_count) as maxPopularity FROM current_view)";
		
		String dropView = "DROP VIEW current_view";
		
		stmt.execute(dropView);
		
		Business b;
		String n, f;
		int r;
		double s, lo, la;
		
		try {
			ResultSet rsx = getResult(createView);
			if (rating && popularity){
				prefixQuery += starQuery + and + reviewCountQuery;
				rsx = getResult(prefixQuery);
			}
			else if (rating && !popularity){
				prefixQuery += starQuery;
				rsx = getResult(prefixQuery);
			}
			else if (popularity && !rating){
				prefixQuery += reviewCountQuery;
				rsx = getResult(prefixQuery);
			}
			else{
				rsx = getResult("SELECT * from current_view");
			}
			
			while (rsx.next()) {
				n = rsx.getString("name");
				f = rsx.getString("full_address");
				r = rsx.getInt("review_count");
				s = rsx.getDouble("stars");
				lo = rsx.getDouble("longitude");
				la = rsx.getDouble("latitude");
				b = new Business(n, f, r, s, lo, la);

				biz.add(b);
			}
	
		}

		catch (Exception e) {
			System.out.println("Caught an error");
		}
		
		
	}

}
