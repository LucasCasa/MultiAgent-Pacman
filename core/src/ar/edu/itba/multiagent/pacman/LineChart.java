package ar.edu.itba.multiagent.pacman;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import com.typesafe.config.Config;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Usuario on 28/5/2017.
 */
public class LineChart extends ApplicationFrame {
	private XYSeriesCollection dataset;
	private JFreeChart lineChart;
	private boolean show_best;
	private boolean show_max;
	private boolean show_avg;
	private boolean show_min;

	public LineChart(Config c) {
		super("Distancia de los fantasmas al pacman");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		lineChart = ChartFactory.createXYLineChart(
				"Distancia de los fantasmas al pacman",
				"Tiempo","Distancia",
				createDataset(c),
				PlotOrientation.VERTICAL,
				true,true,false);

 		ChartPanel chartPanel = new ChartPanel( lineChart );
		int seriesCount = lineChart.getXYPlot().getSeriesCount();
		for (int i = 0; i < seriesCount; i++) {
			lineChart.getXYPlot().getRenderer().setSeriesStroke(i, new BasicStroke(4));
		}
		for (int i = 0; i < seriesCount; i++) {
			lineChart.getXYPlot().getRenderer().setSeriesPaint(i, getColor(i));
		}

		chartPanel.setPreferredSize( new java.awt.Dimension( 800 , 600 ) );
		setContentPane( chartPanel );
	}

	private Paint getColor(int i) {
		switch (i){
			case 0:
				return Color.CYAN;
			case 1:
				return Color.PINK;
			case 2:
				return Color.ORANGE;
			case 3:
				return Color.RED;
			case 4:
				return Color.GREEN;
			case 5:
				return Color.GRAY;
			case 6:
				return new Color(176, 25, 176);
			case 7:
				return new Color(178,178,1);
			case 8:
				return new Color(139,69,19);
			case 9:
				return new Color(0, 221, 170);
		}
		return Color.BLACK;
	}

	public void addResult(List<List<Float>> values){
		int time = 0;
		for(List<Float> ghost : values){
			for (int i = 0; i < ghost.size(); i++) {
				System.out.println(i);
				XYSeries serie = dataset.getSeries(String.valueOf(time));
				serie.add(i, ghost.get(i));
			}
			time++;
		}
	}

	public void addSingleFrame(float distance, int id,  int frame){
		XYSeries serie = dataset.getSeries(String.valueOf(id));
		serie.add(frame, distance);
	}

	private XYSeriesCollection createDataset(Config c) {
		dataset = new XYSeriesCollection();
		for (int i = 0; i < c.getStringList("ghost-names").size(); i++) {
			XYSeries data1 = new XYSeries(String.valueOf(i));
			data1.setDescription(c.getStringList("ghost-names").get(i));
			dataset.addSeries(data1);
		}
		return dataset;
	}
}

