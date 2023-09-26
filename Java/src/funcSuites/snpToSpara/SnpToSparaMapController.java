package funcSuites.snpToSpara;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;

public class SnpToSparaMapController implements Initializable {
	final static String splPath ="D:/Data/InBand_s2p";  // snp 파일을 포함하는 최상위 폴더
	@FXML private Pane pane;
	
	@Override
	public void initialize(URL url, ResourceBundle resource) {
		pane.getChildren().clear();
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
// Import S parameter		
		SnpToSparaMap spl1 = new SnpToSparaMap(splPath);  // S parameter 추출을 위한 객체 생성
		
		spl1.setSnpPath(0,0,0);    // 폴더 및 파일의 경로 설정 (선택한 경로가 파일이면 이 설정은 무시된다.)
		
		TreeMap<Double, Double> dB11 = spl1.dB(1,1);
		TreeMap<Double, Double> dB21 = spl1.dB(2,1);
		
		TreeMap<Double, Double> angle11 = spl1.angle(1,1);
		TreeMap<Double, Double> angle21 = spl1.angle(2,1);
		
		TreeMap<Double, Double> specLine = new TreeMap<>();
		Double specLineValue = -13.0;
		for (int i=3500; i<=3700; i++) {
			specLine.put(i*1e6,  specLineValue);   
		}
        
       List<Double> xAxisRange = List.of( // autoRange_true(not 0.0)_false(0.0), freqStart, freqEnd, tickUnit
    		10.0,		// autoRange: ~0.0, manualRange: 0.0
    		dB11.firstEntry().getKey()-0.1e9,
//    		specLine.firstEntry().getKey()-0.25e9,
    		dB11.lastEntry().getKey()+0.1e9,
//    		specLine.lastEntry().getKey()+0.25e9,
    		0.2e9
        );
       
       List<Double> yAxisRange = List.of( // autoRange_true(not 0.0)_false(0.0), min, max, tickUnit
    		10.0,		// autoRange: ~0.0, manualRange: 0.0
       		-50.0,		
       		5.0,
       		10.0
       );
       
       List<String> seriesName = List.of(
       		"Spec Line",
       		"spl1 S11",
       		"spl1 S21"
       );

       // public LineChart<Number, Number> plotdB(List<Double> xAxisRange, List<Double> yAxisRange, List<String> seriesName, TreeMap<Double, Double>... mapSeries) {
       // public LineChart<Number, Number> plotAngle(List<Double> xAxisRange, List<Double> yAxisRange, List<String> seriesName, TreeMap<Double, Double>... mapSeries) {
       lineChart = spl1.plotdB(xAxisRange, yAxisRange, seriesName, dB11, dB21, specLine);
       pane.getChildren().add(lineChart);
	}
}
	

