package jsonYelp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;


import com.google.gson.Gson;

public class ImportUsers {

	public static int errorsU = 0;
	public static int errorsV = 0;
	
	public static void main(String[] args) 
	{
		try {
			for(int i = 0; i < 130873;i += 2000){
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
		ArrayList<YelpUser> yelpUser = new ArrayList<YelpUser>();  
		String json="";
		File file = new File("yelpUser.json");
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
			YelpUser[] yuArray;
			yuArray = gson.fromJson(json, YelpUser[].class);
			yelpUser = new ArrayList<YelpUser>(Arrays.asList(yuArray));
			
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
		
		DMLs(yelpUser, st);

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
			
			st.execute("DROP TABLE Votes"); 
			st.execute("DROP TABLE YelpUser"); 
			
			String yut = "CREATE TABLE User(" +
					"user_id varchar(25)," +
					"name varchar(50)," +
					"url varchar(70)," +
					"average_stars FLOAT(20)," +
					"review_count INT(5)," +
					"type varchar(5)," +
					"PRIMARY KEY (user_id))";
			st.execute(yut);		
			
			//weak entity
			String vt = "CREATE TABLE UserVotes (" +
					"user_id varchar(25)," +
					"funny int," +
					"useful int," +
					"cool int," +
					"primary key (user_id)," +
					"foreign key (user_id) references YelpUser(user_id))";	
			st.execute(vt);
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void DMLs(ArrayList<YelpUser> people, Statement st) throws IOException {
		
		File f = new File("userImportLog.txt");
		
		if(!f.exists()){
			f.createNewFile();
		}
		
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for(int i = 0; i < people.size()-1; i++) {
			
			YelpUser p = people.get(i);
			
			if(p.name.contains("'")){
				p.name = p.name.replace("'", "''");
			}
			
			String iip = "INSERT INTO User VALUES ('"+p.user_id+"','"+p.name+"','"+p.url+"',"+p.average_stars+","+p.review_count+",'"+p.type+"')";
			
			try {
				st.execute(iip);
			} 
			catch(SQLException e) {
				bw.write(e.getMessage()+"\r\n");
				//System.out.println(iip);
				errorsU++;
			}
			
			String iia = "INSERT INTO UserVotes VALUES ('"+p.user_id+"',"+p.votes.funny+","+p.votes.useful+","+p.votes.cool+")";
			
			try {
				st.execute(iia);
			} 
			catch(Exception e) { 
				bw.write(e.getMessage()+"\r\n");
				//System.out.println(iia);
				errorsV++;
			}
			
		}
		
		bw.close();
		
	}
}
