package servlets;

import static com.googlecode.charts4j.Color.*;

import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.LinearGradientFill;
import com.googlecode.charts4j.Plots;

public class RatingChart {
	
	private String url;
	private static final int MAX_RATING = 5;

	public RatingChart(double[] ratings){
		double averageRating = 0;

		for(int i = 0; i<ratings.length; i++){
			averageRating += ratings[i];
		}				
		averageRating /= ratings.length;
		averageRating = averageRating/MAX_RATING * 100;

		BarChartPlot plot = Plots.newBarChartPlot(Data.newData(averageRating),RED,"");

		BarChart chart = GCharts.newBarChart(plot);

        chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0, 5));      

        chart.setSize(200, 300);
        chart.setBarWidth(100);
        chart.setSpaceWithinGroupsOfBars(20);
        chart.setDataStacked(true);
        chart.setTitle("Rating", BLACK, 16);
        chart.setGrid(100, 10, 3, 2);
        chart.setBackgroundFill(Fills.newSolidFill(WHITE));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, LAVENDER, 100);
        fill.addColorAndOffset(WHITE, 0);
        chart.setAreaFill(fill);
        String url = chart.toURLString();
        this.url = url;
	}
	
	public String getChartUrl(){
		System.out.println(url);
		return url;
	}
	
	public static void main(String[] args){
		double[] ratings = {4.6};
		
		RatingChart chart = new RatingChart(ratings);
		
		System.out.println(chart.url);
	}

}
