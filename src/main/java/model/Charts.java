package model;

import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;


import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

//Class for the real time graph. Graphs deaths, total infections and active infections
public class Charts {

	 public SwingWrapper<XYChart> sw;
	 public XYChart chart;
	 public JFrame frame;
	 public JPanel chartPanel;
	 private HashMap<String,JLabel> labels;
	  
	  public Charts() {
		 
			Map.xData.add(Simulator.simTicks);
			Map.yData_totalInfected.add(Map.totalInfected);
			Map.yData_totalActiveInfected.add(Map.totalActiveInfected);
			Map.yData_totalDied.add(Map.totalDead);
			this.chart = new XYChartBuilder().width(1200).height(600).title("Virus outbreak simulation").xAxisTitle("Time (Days)").yAxisTitle("Number of people").theme(ChartTheme.Matlab).build();
			chart.addSeries("Total Infected", Map.xData, Map.yData_totalInfected);
			chart.addSeries("Active Infected", Map.xData, Map.yData_totalActiveInfected);
			chart.addSeries("Total Deaths", Map.xData, Map.yData_totalDied);
			this.chartPanel = new XChartPanel<XYChart>(chart); 
			chartPanel.setLocation(0, 0);
			chartPanel.setSize(800, 570);
			
			frame = new JFrame();
			frame.getContentPane().setBackground(Color.LIGHT_GRAY);
			frame.setResizable(false);
			frame.setTitle("Real Time Data");
			frame.setSize(new Dimension(1007, 600));
			frame.getContentPane().setLayout(null);
			frame.getContentPane().add(chartPanel);
			
			labels = new HashMap<>();
			
			JLabel lblNewLabel = new JLabel("Statistics");
			lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
			lblNewLabel.setBounds(863, 34, 93, 14);
			frame.getContentPane().add(lblNewLabel);
			
			JLabel lblTick = new JLabel("Tick");
			lblTick.setFont(new Font("Segoe UI", Font.BOLD, 12));
			lblTick.setBounds(810, 96, 93, 14);
			frame.getContentPane().add(lblTick);
			
			JLabel lblTotalInfected = new JLabel("Total Infections");
			lblTotalInfected.setFont(new Font("Segoe UI", Font.BOLD, 12));
			lblTotalInfected.setBounds(810, 121, 93, 14);
			frame.getContentPane().add(lblTotalInfected);
			
			JLabel lblActiveInfected = new JLabel("Active Infections");
			lblActiveInfected.setFont(new Font("Segoe UI", Font.BOLD, 12));
			lblActiveInfected.setBounds(810, 146, 108, 14);
			frame.getContentPane().add(lblActiveInfected);
			
			JLabel lblTick_2_1 = new JLabel("Immune");
			lblTick_2_1.setFont(new Font("Segoe UI", Font.BOLD, 12));
			lblTick_2_1.setBounds(810, 171, 93, 14);
			frame.getContentPane().add(lblTick_2_1);
			
			JLabel lblTick_2_2 = new JLabel("Dead");
			lblTick_2_2.setFont(new Font("Segoe UI", Font.BOLD, 12));
			lblTick_2_2.setBounds(810, 196, 93, 14);
			frame.getContentPane().add(lblTick_2_2);
			
			JLabel lblTick_2_3 = new JLabel("Quarantined");
			lblTick_2_3.setFont(new Font("Segoe UI", Font.BOLD, 12));
			lblTick_2_3.setBounds(810, 221, 93, 14);
			frame.getContentPane().add(lblTick_2_3);
			
			JLabel lblTick_2_4 = new JLabel("Total Tests");
			lblTick_2_4.setFont(new Font("Segoe UI", Font.BOLD, 12));
			lblTick_2_4.setBounds(810, 246, 93, 14);
			frame.getContentPane().add(lblTick_2_4);
			
			JLabel lblTick_2_5 = new JLabel("Positive Tests");
			lblTick_2_5.setFont(new Font("Segoe UI", Font.BOLD, 12));
			lblTick_2_5.setBounds(810, 271, 93, 14);
			frame.getContentPane().add(lblTick_2_5);
			
			JLabel tickLbl = new JLabel("0");
			tickLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
			tickLbl.setBounds(942, 96, 49, 14);
			frame.getContentPane().add(tickLbl);
			labels.put("tick", tickLbl);
			
			JLabel totInfLbl = new JLabel("0");
			totInfLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
			totInfLbl.setBounds(942, 121, 49, 14);
			frame.getContentPane().add(totInfLbl);
			labels.put("totalInfections", totInfLbl);
			
			JLabel actInfLbl = new JLabel("0");
			actInfLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
			actInfLbl.setBounds(942, 146, 49, 14);
			frame.getContentPane().add(actInfLbl);
			labels.put("activeInfections", actInfLbl);
			
			JLabel immuneLbl = new JLabel("0");
			immuneLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
			immuneLbl.setBounds(942, 172, 49, 14);
			frame.getContentPane().add(immuneLbl);
			labels.put("immune", immuneLbl);
			
			JLabel deadLbl = new JLabel("0");
			deadLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
			deadLbl.setBounds(942, 197, 49, 14);
			frame.getContentPane().add(deadLbl);
			labels.put("dead", deadLbl);
			
			JLabel quarantineLbl = new JLabel("0");
			quarantineLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
			quarantineLbl.setBounds(942, 222, 49, 14);
			frame.getContentPane().add(quarantineLbl);
			labels.put("quarantined", quarantineLbl);
			
			JLabel totTestLbl = new JLabel("0");
			totTestLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
			totTestLbl.setBounds(942, 247, 49, 14);
			frame.getContentPane().add(totTestLbl);
			labels.put("totalTests", totTestLbl);
			
			JLabel posTestLbl = new JLabel("0");
			posTestLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
			posTestLbl.setBounds(942, 272, 49, 14);
			frame.getContentPane().add(posTestLbl);
			labels.put("positiveTests", posTestLbl);
			
			frame.setVisible(true);
		}
		  
	  
	public void realTimeupdates() {
	    chart.updateXYSeries("Total Infected", Map.xData, Map.yData_totalInfected, null);
	    chart.updateXYSeries("Active Infected", Map.xData, Map.yData_totalActiveInfected, null);
	    chart.updateXYSeries("Total Deaths", Map.xData, Map.yData_totalDied, null);
	    
	    labels.get("tick").setText(""+Simulator.simTicks);
	    labels.get("totalInfections").setText(""+Map.totalInfected);
	    labels.get("activeInfections").setText(""+Map.totalActiveInfected);
	    labels.get("immune").setText(""+Map.totalImmune);
	    labels.get("dead").setText(""+Map.totalDead);
	    labels.get("quarantined").setText(""+Map.totalQuarantined);
	    labels.get("totalTests").setText(""+Map.totalTests);
	    labels.get("positiveTests").setText(""+Map.totalPositiveTests);
	    
	    frame.repaint();
	}
	
	public void saveBitmapImage() {
	try {
	BitmapEncoder.saveBitmap(chart, OutputWriter.path+"TraceChart.png", BitmapFormat.PNG);}
	catch (IOException e) {
		e.printStackTrace();
	}
}
}

