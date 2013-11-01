package jsonYelp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

public class ImportReviews {

	public static int errorsU = 0;
	public static int errorsV = 0;
	
	public static void main(String[] args) 
	{
		try {
			for(int i = 0; i < 330071;i += 2000){
			importData(i, i + 2000, args);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			System.out.println("Number of errorsU " + errorsU);
			System.out.println("Number of errorsV " + errorsV);
		}

	
	public static void importData(int start, int end, String[] args) throws Exception{
		Gson gson = new Gson();
		ArrayList<YelpReview> yelpReview = new ArrayList<YelpReview>();  
		String json="";
		File file = new File("yelpReview.json");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		BufferedReader d= new BufferedReader(new InputStreamReader(bis));		
		String line = "";
		json+= "[";
		for(int i = 0; i < start; i ++){
			d.readLine();
		}
		
		for(int i = start; i < end; i++){
			line = d.readLine();
		
			if(line == null){
				break;
			}
			
			line += ",";
			
			if(i % 5000 == 0){
				System.out.println("Count is " + i);
				System.out.println("Number of errorsU " + errorsU + " ~ Number of errorsV " + errorsV);				
			}
			json+=line;
			
		}
		json += "]";
		fis.close();
		bis.close();
		d.close(); 
		
		try{
			YelpReview[] yrArray;
			yrArray = gson.fromJson(json, YelpReview[].class);
			yelpReview = new ArrayList<YelpReview>(Arrays.asList(yrArray));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Invalid data format");	// FAILS HERE
			System.exit(0);
		}
		
		//Insert all of the data into your database
		//Initialize the database and write DDLs
		
		Statement st = null;
		st = makeConnectionWithDatabase(args);
		//DDLs(st);
		DMLs(yelpReview, st);

	}
		
	public static Statement makeConnectionWithDatabase(String[] args) throws Exception
	{
		try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		
		conn = DriverManager.getConnection ("jdbc:mysql://localhost:3306/YelpMeOut","yelp", "cis330");

		Statement st = conn.createStatement();
		
		return st;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static void DDLs(Statement st) throws Exception
	{
		try{
			
			//st.execute("DROP TABLE ReviewVotes"); 
			//st.execute("DROP TABLE Review"); 
			
			String yut = "CREATE TABLE Review(" +
					"user_id varchar(25)," +
					"review_id varchar(25)," +
					"business_id varchar(25)," +
					"stars int," +
					"date varchar(15)," +
					"PRIMARY KEY (review_id))";
			st.execute(yut);		
			
			//weak entity
			String vt = "CREATE TABLE ReviewVotes (" +
					"review_id varchar(25)," +
					"business_id varchar(25)," +
					"funny int," +
					"useful int," +
					"cool int," +
					"primary key (review_id, business_id)," +
					"foreign key (review_id) references Review(review_id) ) " ;	
			st.execute(vt);
		}
		
		catch (SQLException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	public static void DMLs(ArrayList<YelpReview> reviews, Statement st) {
		
		for(int i = 0; i < reviews.size()-1; i++) {
			
			YelpReview r = reviews.get(i);
			
			String iip = "INSERT INTO Review VALUES ('"+r.user_id+"','"+r.review_id+"','"+r.business_id+"',"+r.stars+",'"+r.date+"')";
			
			try {
				st.execute(iip);
			} 
			catch(SQLException e) { 
				//System.out.println(iip);
				errorsU++;
			}
			
			String iia = "INSERT INTO ReviewVotes VALUES ('"+r.review_id+"','"+r.business_id+"',"+r.votes.funny+","+r.votes.useful+","+r.votes.cool+")";
			
			try {
				st.execute(iia);
			} 
			catch(Exception e) { 
				//System.out.println(iia);
				errorsV++;
			}
			
		}		
	}
}
