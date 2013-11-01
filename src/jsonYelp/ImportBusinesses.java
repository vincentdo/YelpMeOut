package jsonYelp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

public class ImportBusinesses {

	public static int errorsU = 0;
	
	public static void main(String[] args) 
	{
		try {
			for(int i = 0; i < 13490;i += 2000){
				importData(i, i + 2000, args);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			System.out.println("Number of errorsU " + errorsU);
		}
	
	public static void importData(int start, int end, String[] args) throws Exception{
		Gson gson = new Gson();
		ArrayList<YelpBusiness> yelpBusiness = new ArrayList<YelpBusiness>();  
		
		String json="";
		File file = new File("yelpBusiness.json");
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
				System.out.println("Number of errorsU " + errorsU);				
			}
			json+=line;
			
		}
		json += "]";
		fis.close();
		bis.close();
		d.close(); 
		
		try{
			YelpBusiness[] ybArray;
			ybArray = gson.fromJson(json, YelpBusiness[].class);
			yelpBusiness = new ArrayList<YelpBusiness>(Arrays.asList(ybArray));
			
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
		
		DMLs(yelpBusiness, st);

	}
		
	public static Statement makeConnectionWithDatabase(String[] args) throws Exception
	{
		try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		
		conn = DriverManager.getConnection ("jdbc:mysql://localhost:3306/YelpMeOut","yelp", "cis330");

		Statement st = conn.createStatement();
		
		System.out.println("Accessed!");
		
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
			
			st.execute("DROP TABLE NearbyNeighborhoods");
			st.execute("DROP TABLE BusinessCategory");
			st.execute("DROP TABLE NearbySchools"); 
			st.execute("DROP TABLE Business"); 
			
			String yut = "CREATE TABLE Business(" +
					"business_id varchar(25)," +
					"name varchar(100)," +
					"full_address varchar(200)," +
					"city varchar(50)," +
					"state varchar(25)," +
					"open varchar(10)," +
					"photo_url varchar(100)," +
					"review_count int(5)," +
					"stars float(20)," +
					"longitude float(20)," +
					"latitude float(20)," +
					"PRIMARY KEY (business_id))";
			st.execute(yut);		
			
			System.out.println(yut);
			
			String ybs = "CREATE TABLE NearbySchools(" +
					"business_id varchar(25)," +
					"school varchar(50)," +
					"primary key (business_id, school)," +
					"foreign key (business_id) references Business (business_id) )";
			st.execute(ybs);

			System.out.println(ybs);
			
			String ybc = "CREATE TABLE BusinessCategory(" +
					"business_id varchar(25)," +
					"category varchar(25)," +
					"primary key (business_id, category)," +
					"foreign key (business_id) references Business (business_id) )";
			st.execute(ybc);
		
			System.out.println(ybc);
			
			String ybn = "CREATE TABLE NearbyNeighborhoods(" +
					"business_id varchar(25)," +
					"neighborhood varchar(25)," +
					"primary key (business_id, neighborhood)," +
					"foreign key (business_id) references Business (business_id) )";
			st.execute(ybn);
 
			System.out.println(ybn);
			
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void DMLs(ArrayList<YelpBusiness> businesses, Statement st) throws IOException {
		
		for(int i = 0; i < businesses.size()-1; i++) {
			
			YelpBusiness b = businesses.get(i);
			
			if(b.name.contains("'")){
				b.name = b.name.replace("'", "''");
			}
			
			if(b.full_address.contains("'")){
				b.full_address = b.full_address.replace("'", "''");
			}
			
			String iip = "INSERT INTO Business VALUES ('"+b.business_id+"','"+b.name+"','"+b.full_address+"','"+b.city+"','"+b.state+"','"+b.open+"','"+b.photo_url+"',"+b.review_count+","+b.stars+","+b.longitude+", "+b.latitude+")";
			
			try {
				st.execute(iip);
			} 
			catch(SQLException e) {
				System.out.println(e.getMessage());
				//System.out.println(iip);
				errorsU++;
			}
			
			
			for(String s : b.schools){
				
				String iibs = "INSERT INTO NearbySchools VALUES('"+b.business_id+"','"+s+"') ";
				
				try{
					st.execute(iibs);
				}
				catch(SQLException e){
				}
		
			}
			
			for(String c : b.categories){
				
				String iibc = "INSERT INTO BusinessCategory VALUES('"+b.business_id+"','"+c+"') ";
				
				try{
					st.execute(iibc);
				}
				catch(SQLException e){
					
				}
				 
			}
			
			for(String n : b.neighborhoods){
				
				String iibn = "INSERT INTO NearbyNeighborhoods VALUES('"+b.business_id+"','"+n+"') ";
				
				try{
					st.execute(iibn);
				}
				catch(SQLException e){
					
				}
				 

			}
			
		}
		
	}
}
