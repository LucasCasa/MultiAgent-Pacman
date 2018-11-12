package ar.edu.itba.multiagent.pacman;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import com.typesafe.config.Config;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Usuario on 28/5/2017.
 */
public class LineChart extends ApplicationFrame {
	private XYSeriesCollection dataset;
	private JFreeChart lineChart;

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
			if(i < seriesCount - 1)
				lineChart.getXYPlot().getRenderer().setSeriesStroke(i, new BasicStroke(4));
			else
				lineChart.getXYPlot().getRenderer().setSeriesStroke(i, new BasicStroke(2));

		}
		for (int i = 0; i < seriesCount; i++) {
			if(i < seriesCount - 1)
				lineChart.getXYPlot().getRenderer().setSeriesPaint(i, getColor(i));
			else
				lineChart.getXYPlot().getRenderer().setSeriesPaint(i, Color.WHITE);
		}
		lineChart.getXYPlot().getRangeAxis().setRange(0, 35);
		chartPanel.setBackground( Color.BLACK );
		lineChart.getXYPlot().setBackgroundPaint( Color.BLACK);
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

	public void addResult(List<List<Float>> values, List<Boolean> chasing){
		int ghostId = 0;
		for(List<Float> ghost : values){
			for (int i = 0; i < ghost.size(); i++) {
				XYSeries serie = dataset.getSeries(String.valueOf(ghostId));
				serie.add(i, ghost.get(i));
			}
			ghostId++;
		}
		int index = values.size();
		Font font = new Font("Dialog", Font.PLAIN, 25);
		int start = -1;
		for (int i = 0; i < chasing.size() - 1; i++) {
			//XYSeries series = dataset.getSeries(String.valueOf(index));
			if(chasing.get(i) != chasing.get(i + 1)){
				if(start == -1) {
					start = i;
				} else {
					Marker in = new IntervalMarker(start, i, new Color(0,0,0.5f,1));
					in.setLabel("Chasing");
					in.setLabelFont(font);
					in.setLabelPaint(Color.WHITE);
					in.setLabelTextAnchor(TextAnchor.TOP_CENTER);
					in.setLabelAnchor(RectangleAnchor.TOP);
					lineChart.getXYPlot().addDomainMarker(in, Layer.BACKGROUND);
					start = -1;
				}
//				Marker r = new ValueMarker(i);
//				r.setLabel(chasing.get(i + 1)?"Chase":"Search");
//				r.setPaint(Color.WHITE);
//				r.setLabelFont(font);
//				r.setLabelPaint(Color.WHITE);
//				if(chasing.get(i)){
//					r.setLabelTextAnchor(TextAnchor.TOP_LEFT);
//					r.setLabelAnchor(RectangleAnchor.TOP_LEFT);
//				} else {
//					r.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
//					r.setLabelAnchor(RectangleAnchor.BOTTOM_LEFT);
//				}
//
//				lineChart.getXYPlot().addDomainMarker(r);

			}
			//series.add(i, chasing.get(i)?35:0);
		}
		if(start != -1) {
			Marker in = new IntervalMarker(start, chasing.size(), new Color(0,0,0.5f,1));
			in.setLabel("Chasing");
			in.setLabelFont(font);
			in.setLabelPaint(Color.WHITE);
			in.setLabelTextAnchor(TextAnchor.TOP_LEFT);
			in.setLabelAnchor(RectangleAnchor.TOP);
			lineChart.getXYPlot().addDomainMarker(in, Layer.BACKGROUND);
		}
	}

	public void addSingleFrame(float distance, int id,  int frame){
		XYSeries serie = dataset.getSeries(String.valueOf(id));
		serie.add(frame, distance);
	}

	private XYSeriesCollection createDataset(Config c) {
		dataset = new XYSeriesCollection();
		int ghostCount = c.getStringList("ghost-names").size();
		for (int i = 0; i < ghostCount + 1; i++) {
			XYSeries data1 = new XYSeries(String.valueOf(i));
			if(i == ghostCount){
				data1.setDescription("");
			} else {
				data1.setDescription(c.getStringList("ghost-names").get(i));
			}
			dataset.addSeries(data1);
		}
		return dataset;
	}
}

