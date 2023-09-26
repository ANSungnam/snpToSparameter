package funcSuites.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;


class PlotType2 extends JFrame {
	List<XYSeries> series = new ArrayList<>();
	String chartTitle;
	String xLabel;
	String yLabel;
	XYLineAndShapeRenderer render = new XYLineAndShapeRenderer();
	
    public PlotType2(String chartTitle, String xLabel, String yLabel) {
    	this.chartTitle = chartTitle;
    	this.xLabel = xLabel;
    	this.yLabel = yLabel;
    	
        initUI();
    }


	private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JFreeChart createChart(XYDataset dataset) {    	
    	JFreeChart chart = ChartFactory.createXYLineChart(
                chartTitle,
                xLabel,
                yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        render.setSeriesPaint(0, Color.RED);
        render.setSeriesStroke(0, new BasicStroke(2.0f));
        
        render.setSeriesPaint(1, Color.BLUE);
        render.setSeriesStroke(1, new BasicStroke(2.0f));
        
        render.setSeriesPaint(2, Color.GREEN);
        render.setSeriesStroke(2, new BasicStroke(2.0f));
        
        render.setSeriesPaint(3, Color.BLACK);
        render.setSeriesStroke(3, new BasicStroke(2.0f));

        plot.setRenderer(render);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(chartTitle,
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }
    
    
    
    
    private XYDataset createDataset() {
    	var dataset = new XYSeriesCollection();
    	
    	for(int i=0; i<4; i++) {
    		series.add(new XYSeries(""+i));
    	}    	
        
        for(int i=0; i<4; i++) {
        	dataset.addSeries(series.get(i));
    	}
        return dataset;
    }

    
    
    
    
    
    

}    