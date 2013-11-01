package servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/**
 * Server info: Apache Tomcat/7.0.34 Servlet version: 3.0 JSP version: 2.1 Java
 * version: 1.6.0_34
 * 
 */

@WebServlet("/toPlanning")
public class PlanningServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		YelpDB ydb = new YelpDB();

		String userAddress = "";
		double userLong = 0;
		double userLat = 0;
		boolean byFoot = false;
		boolean byCar = false;

		ArrayList<Business> bus1 = new ArrayList<Business>();
		ArrayList<Business> bus2 = new ArrayList<Business>();
		ArrayList<Business> bus3 = new ArrayList<Business>();
		ArrayList<Business> bus4 = new ArrayList<Business>();
		ArrayList<Business> bus5 = new ArrayList<Business>();

		String cat0 = "";
		String cat1 = "";
		String cat2 = "";
		String cat3 = "";
		String cat4 = "";

		boolean[] stop1 = new boolean[2]; // stop[0] pop stop[1] rating
		boolean[] stop2 = new boolean[2];
		boolean[] stop3 = new boolean[2];
		boolean[] stop4 = new boolean[2];
		boolean[] stop5 = new boolean[2];

		double rad1 = 0;
		double rad2 = 0;
		double rad3 = 0;
		double rad4 = 0;
		double rad5 = 0;

		Map<String, String[]> parameters = request.getParameterMap();
		for (String parameter : parameters.keySet()) {

			if (parameter.equals("address")) {
				String[] value = parameters.get(parameter);
				if (!value[0].equals("")) {
					userAddress = value[0];
				}
			}

			if (parameter.equals("travel")) {
				String[] value = parameters.get(parameter);
				for (String s : value) {
					if (s.equals("Foot")) {
						byFoot = true;
					}
					if (s.equals("Car")) {
						byCar = true;
					}
				}
			}

			if (parameter.equals("cat0")) {
				String[] value = parameters.get(parameter);
				if (!value[0].equals("")) {
					cat0 = value[0];
				}
			}

			if (parameter.equals("cat1")) {
				String[] value = parameters.get(parameter);
				if (!value[0].equals("")) {
					cat1 = value[0];
				}
			}

			if (parameter.equals("cat2")) {
				String[] value = parameters.get(parameter);
				if (!value[0].equals("")) {
					cat2 = value[0];
				}
			}

			if (parameter.equals("cat3")) {
				String[] value = parameters.get(parameter);
				if (!value[0].equals("")) {
					cat3 = value[0];
				}
			}

			if (parameter.equals("cat4")) {
				String[] value = parameters.get(parameter);
				if (!value[0].equals("")) {
					cat4 = value[0];
				}
			}

			if (parameter.equals("check0")) {
				String[] value = parameters.get(parameter);
				for (String s : value) {
					if (s.equals("Popularity")) {
						stop1[0] = true;
					}
					if (s.equals("Rating")) {
						stop1[1] = true;
					}
				}

			}

			if (parameter.equals("check1")) {
				String[] value = parameters.get(parameter);
				for (String s : value) {
					if (s.equals("Popularity")) {
						stop2[0] = true;
					}
					if (s.equals("Rating")) {
						stop2[1] = true;
					}
				}

			}
			if (parameter.equals("check2")) {
				String[] value = parameters.get(parameter);
				for (String s : value) {
					if (s.equals("Popularity")) {
						stop3[0] = true;
					}
					if (s.equals("Rating")) {
						stop3[1] = true;
					}
				}

			}
			if (parameter.equals("check3")) {
				String[] value = parameters.get(parameter);
				for (String s : value) {
					if (s.equals("Popularity")) {
						stop4[0] = true;
					}
					if (s.equals("Rating")) {
						stop4[1] = true;
					}
				}
			}

			if (parameter.equals("check4")) {
				String[] value = parameters.get(parameter);
				for (String s : value) {
					if (s.equals("Popularity")) {
						stop5[0] = true;
					}
					if (s.equals("Rating")) {
						stop5[1] = true;
					}
				}
			}

			if (parameter.equals("radius0")) {
				String[] value = parameters.get(parameter);
				if (!value[0].equals("")) {
					rad1 = Double.parseDouble(value[0]);
				}
			}
			if (parameter.equals("radius1")) {
				String[] value = parameters.get(parameter);
				if (!value[0].equals("")) {
					rad2 = Double.parseDouble(value[0]);
				}
			}
			if (parameter.equals("radius2")) {
				String[] value = parameters.get(parameter);
				if (!value[0].equals("")) {
					rad3 = Double.parseDouble(value[0]);
				}
			}
			if (parameter.equals("radius3")) {
				String[] value = parameters.get(parameter);
				if (!value[0].equals("")) {
					rad4 = Double.parseDouble(value[0]);
				}
			}
			if (parameter.equals("radius4")) {
				String[] value = parameters.get(parameter);
				if (!value[0].equals("")) {
					rad5 = Double.parseDouble(value[0]);
				}
			}
		}

		try {
			AddressParser ap = new AddressParser(userAddress);
			userLong = ap.getLong();
			userLat = ap.getLat();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		RangeCalculator rc1 = new RangeCalculator(userLat, userLong, rad1);
		RangeCalculator rc2 = new RangeCalculator(userLat, userLong, rad2);
		RangeCalculator rc3 = new RangeCalculator(userLat, userLong, rad3);
		RangeCalculator rc4 = new RangeCalculator(userLat, userLong, rad4);
		RangeCalculator rc5 = new RangeCalculator(userLat, userLong, rad5);

		try {
			if (!cat0.equals("")) {
				ydb.getValues(cat0, stop1[0], stop1[1], rc1.getLngMax(),rc1.getLngMin(), rc1.getLatMax(), rc1.getLatMin(), bus1);
			}
			if (!cat1.equals("")) {
				ydb.getValues(cat1, stop2[0], stop2[1], rc2.getLngMax(),
						rc2.getLngMin(), rc2.getLatMax(), rc2.getLatMin(), bus2);
			}
			if (!cat2.equals("")) {
				ydb.getValues(cat2, stop3[0], stop3[1], rc3.getLngMax(),
						rc3.getLngMin(), rc3.getLatMax(), rc3.getLatMin(), bus3);
			}
			if (!cat3.equals("")) {
				ydb.getValues(cat3, stop4[0], stop4[1], rc4.getLngMax(),
						rc4.getLngMin(), rc4.getLatMax(), rc4.getLatMin(), bus4);
			}
			if (!cat4.equals("")) {
				ydb.getValues(cat4, stop5[0], stop5[1], rc5.getLngMax(),
						rc5.getLngMin(), rc5.getLatMax(), rc5.getLatMin(), bus5);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String address = "/WEB-INF/pages/results.jsp";

		ydb.close();

		String chart1 = "";
		String chart2 = "";
		String chart3 = "";
		String chart4 = "";
		String chart5 = "";
				
		String dir1 = "https://maps.google.com/";
		String dir2 = "https://maps.google.com/";
		String dir3 = "https://maps.google.com/";
		String dir4 = "https://maps.google.com/";
		String dir5 = "https://maps.google.com/";
		
		Business unplanned = new Business("No User Input");
		Business emptyResult = new Business("~No Suitable Location~");
		
		Business ptr = new Business(userLong, userLat);
		
		ArrayList<Business> allbiz = new ArrayList<Business>();
		
		if (bus1.size()>0) {
			Business b1 = bus1.get((int) ((bus1.size() - 1) * Math.random()));
			request.setAttribute("bus1", b1);
			
			GoogleMapDirectionsLink gmdl1 = new GoogleMapDirectionsLink(ptr.getLatitude(), b1.getLatitude(), ptr.getLongitude(), b1.getLongitude());
			dir1 = gmdl1.getDirectionLink();
			request.setAttribute("link1", dir1);
			
			DistanceCalculator dc1 = new DistanceCalculator(ptr.getLatitude(), b1.getLatitude(), ptr.getLongitude(), b1.getLongitude());
			
			double[] star1 = {b1.getStars()};
			RatingChart rtc1 = new RatingChart(star1);
			chart1 = rtc1.getChartUrl();
			request.setAttribute("ct1", chart1);
			
			if(byFoot){
				request.setAttribute("walk1", dc1.getWalkingDistance());
			}
			if(byCar){
				request.setAttribute("drive1", dc1.getDrivingDistance());
			}
			
			ptr = b1;
			
			allbiz.add(b1);
		}
		else{
			if(!cat0.equals("")){
			request.setAttribute("bus1", emptyResult);
			} 
			else{
				request.setAttribute("bus1", unplanned);
			}
		}
	
		
		if (bus2.size()>0) {
			Business b2 = bus2.get((int) ((bus2.size() - 1) * Math.random()));
			request.setAttribute("bus2", b2);
			GoogleMapDirectionsLink gmdl2 = new GoogleMapDirectionsLink(ptr.getLatitude(), b2.getLatitude(), ptr.getLongitude(), b2.getLongitude());
			dir2 = gmdl2.getDirectionLink();
			request.setAttribute("link2", dir2);
			
			DistanceCalculator dc2 = new DistanceCalculator(ptr.getLatitude(), b2.getLatitude(), ptr.getLongitude(), b2.getLongitude());
			
			double[] star2 = {b2.getStars()};
			RatingChart rtc2 = new RatingChart(star2);
			chart2 = rtc2.getChartUrl();
			request.setAttribute("ct2", chart2);
			
			if(byFoot){
				request.setAttribute("walk2", dc2.getWalkingDistance());
			}
			if(byCar){
				request.setAttribute("drive2", dc2.getDrivingDistance());
			}
			
			ptr = b2;
			allbiz.add(b2);	
		}
		else{
			if(!cat1.equals("")){
				request.setAttribute("bus2", emptyResult);
				} 
				else{
					request.setAttribute("bus2", unplanned);
				}
		}
		
		if (bus3.size()>0) {
			Business b3 = bus3.get((int) ((bus3.size() - 1) * Math.random()));
			request.setAttribute("bus3", b3);
			GoogleMapDirectionsLink gmdl3 = new GoogleMapDirectionsLink(ptr.getLatitude(), b3.getLatitude(), ptr.getLongitude(), b3.getLongitude());
			dir3 = gmdl3.getDirectionLink();
			request.setAttribute("link3", dir3);

			DistanceCalculator dc3 = new DistanceCalculator(ptr.getLatitude(), b3.getLatitude(), ptr.getLongitude(), b3.getLongitude());
			
			double[] star3 = {b3.getStars()};
			RatingChart rtc3 = new RatingChart(star3);
			chart3 = rtc3.getChartUrl();
			request.setAttribute("ct3", chart3);
			
			if(byFoot){
				request.setAttribute("walk3", dc3.getWalkingDistance());
			}
			if(byCar){
				request.setAttribute("drive3", dc3.getDrivingDistance());
			}
			ptr = b3;
			
			allbiz.add(b3);
		}
		else{
			if(!cat2.equals("")){
				request.setAttribute("bus3", emptyResult);
				} 
				else{
					request.setAttribute("bus3", unplanned);
				}
		}
		
		if (bus4.size()>0) {
			Business b4 = bus4.get((int) ((bus4.size() - 1) * Math.random()));
			request.setAttribute("bus4", b4);
			GoogleMapDirectionsLink gmdl4 = new GoogleMapDirectionsLink(ptr.getLatitude(), b4.getLatitude(), ptr.getLongitude(), b4.getLongitude());
			dir4 = gmdl4.getDirectionLink();
			request.setAttribute("link4", dir4);

			DistanceCalculator dc4 = new DistanceCalculator(ptr.getLatitude(), b4.getLatitude(), ptr.getLongitude(), b4.getLongitude());
			
			double[] star4 = {b4.getStars()};
			RatingChart rtc4 = new RatingChart(star4);
			chart4 = rtc4.getChartUrl();
			request.setAttribute("ct4", chart4);
			
			if(byFoot){
				request.setAttribute("walk4", dc4.getWalkingDistance());
			}
			if(byCar){
				request.setAttribute("drive4", dc4.getDrivingDistance());
			}
			
			ptr = b4;
			allbiz.add(b4);
		}
		else{
			if(!cat3.equals("")){
				request.setAttribute("bus4", emptyResult);
				} 
				else{
					request.setAttribute("bus4", unplanned);
				}
		}
		
		if (bus5.size()>0) {
			Business b5 = bus5.get((int) ((bus5.size() - 1) * Math.random()));
			request.setAttribute("bus5", b5);
			GoogleMapDirectionsLink gmdl5 = new GoogleMapDirectionsLink(ptr.getLatitude(), b5.getLatitude(), ptr.getLongitude(), b5.getLongitude());
			dir5 = gmdl5.getDirectionLink();
			request.setAttribute("link5", dir5);
			
			DistanceCalculator dc5 = new DistanceCalculator(ptr.getLatitude(), b5.getLatitude(), ptr.getLongitude(), b5.getLongitude());
			
			double[] star5 = {b5.getStars()};
			RatingChart rtc5 = new RatingChart(star5);
			chart5 = rtc5.getChartUrl();
			request.setAttribute("ct5", chart5);
			
			if(byFoot){
				request.setAttribute("walk5", dc5.getWalkingDistance());
			}
			if(byCar){
				request.setAttribute("drive5", dc5.getDrivingDistance());
			}
			
			allbiz.add(b5);
		}
		else{
			if(!cat4.equals("")){
				request.setAttribute("bus5", emptyResult);
			} 
			else{
				request.setAttribute("bus5", unplanned);
			}
		}
		
		double[] latar = new double[allbiz.size()+1];
		double[] lonar = new double[allbiz.size()+1];
		latar[0] = userLat;
		lonar[0] = userLong;
		for(int i = 0; i < allbiz.size(); i++){
			latar[i+1] = allbiz.get(i).getLatitude();
			lonar[i+1] = allbiz.get(i).getLongitude();
		}
		
		StaticMapGenerator smg = new StaticMapGenerator(latar, lonar);
		String mapurl = smg.getMapUrl();
		

		
		
		request.setAttribute("mapurl", mapurl);
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

}
