package model;



import java.io.IOException;
import javax.swing.JPanel;


import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;





public class Charts {

	 public SwingWrapper<XYChart> sw;
	 public XYChart chart;
	 public JPanel chartPanel;
	  
	  public Charts() {
		 
			Map.xData.add(Simulator.simTicks);
			Map.yData_totalInfected.add(Map.totalInfected);
			Map.yData_totalActiveInfected.add(Map.totalActiveInfected);
			Map.yData_totalDied.add(Map.totalDead);
		 //   System.out.print("Chart initialised!");
			this.chart = new XYChartBuilder().width(1200).height(600).title("Virus outbreak simulation").xAxisTitle("Time (Days)").yAxisTitle("Number of people(in thousands)").build();
			chart.addSeries("totalInfectedSeries", Map.xData, Map.yData_totalInfected);
			chart.addSeries("totalActiveInfectedSeries", Map.xData, Map.yData_totalActiveInfected);
			chart.addSeries("totalDiedSeries", Map.xData, Map.yData_totalDied);
			this.sw=new SwingWrapper<>(chart);
			sw.displayChart();
			this.chartPanel = new XChartPanel<XYChart>(chart);  
	  }
		  
	  
	public void realTimeupdates() {
	    chart.updateXYSeries("totalInfectedSeries", Map.xData, Map.yData_totalInfected, null);
	    chart.updateXYSeries("totalActiveInfectedSeries", Map.xData, Map.yData_totalActiveInfected, null);
	    chart.updateXYSeries("totalDiedSeries", Map.xData, Map.yData_totalDied, null);
		
	    sw.repaintChart();
	}
	
	public void saveBitmapImage() {
	try {
	BitmapEncoder.saveBitmap(chart, OutputWriter.path+"TraceChart.png", BitmapFormat.PNG);}
	catch (IOException e) {
		e.printStackTrace();
	}
}
}

