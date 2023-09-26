package funcSuites.snpToSpara;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;

public class SnpToSparaMapRun extends Application {
	final static String splPath ="D:\\Data\\InBand_s2p";  // snp 파일을 포함하는 최상위 폴더
	
	public static void main(String[] args) throws IOException {
		
		SnpToSparaMap spl1 = new SnpToSparaMap(splPath);  // S parameter 추출을 위한 객체 생성		
		
		spl1.setSnpPath(1,1,20);    // 폴더 및 파일의 경로 설정 (선택한 경로가 파일이면 이 설정은 무시된다.)

		System.out.println(""); 
		System.out.println("[1] 각 섹션의 첫 번째 폴더와 파일은 " + Arrays.toString(spl1.pathTree(-1,0,0))); 
		System.out.println("[1] 각 섹션의 첫 번째 폴더와 파일은 " + Arrays.toString(spl1.pathTree(0,-1,0))); 
		System.out.println("[1] 각 섹션의 첫 번째 폴더와 파일은 " + Arrays.toString(spl1.pathTree(0,0,-1))); 
		System.out.println("[2] 최상위 폴더에서 4번째 폴더의 하위폴더를 표시" + Arrays.toString(spl1.pathTree(-1,-1,-1)));  // -1은 첫 번째 값만 의미있고, 그 이후의 값들은 무시된다. 
		
		System.out.println("[3] 주파수는 " + spl1.freq.toString());  
		System.out.println("[4] 시작주파수 3.1GHz, 마지막 주파수 4.1GHz 각각의 S12 Magnitude는 " + spl1.dB(1,2).get(3.1E9) + ", " + spl1.dB(1,2).get(4.1E9)); 
		System.out.println("[5] 시작주파수 3.1GHz, 마지막 주파수 4.1GHz 각각의 S12 Angle은 " + spl1.angle(1,2).get(3.1E9) + ", " + spl1.angle(1,2).get(4.1E9)); 
		System.out.println(""); 
		
		spl1.setSnpPath(1,1,1);    // snp 파일의 경로 변경
		System.out.println(""); 
		System.out.println("snp 파일 경로 변경"+ Arrays.toString(spl1.pathTree(1,1,1))); 
		System.out.println(""); 
		System.out.println("[11] 변경된 경로의 폴더와 파일은 " + Arrays.toString(spl1.pathTree(1,1,1))); 
		System.out.println("[12] 최상위 폴더를 표시" + Arrays.toString(spl1.pathTree(-1,2,3)));  // -1은 첫 번째 값만 의미있고, 그 이후의 값들은 무시된다. 
		
		System.out.println("[13] 주파수는 " + spl1.freq.toString());  
		System.out.println("[14] 시작주파수 3.1GHz, 마지막 주파수 4.1GHz 각각의 S12 Magnitude는 " + spl1.dB(1,2).get(3.1E9) + ", " + spl1.dB(1,2).get(4.1E9)); 
		System.out.println("[15] 시작주파수 3.1GHz, 마지막 주파수 4.1GHz 각각의 S12 Angle은 " + spl1.angle(1,2).get(3.1E9) + ", " + spl1.angle(1,2).get(4.1E9)); 
		
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws IOException {
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

// Window Set	
       
       // public LineChart<Number, Number> plotdB(List<Double> xAxisRange, List<Double> yAxisRange, List<String> seriesName, TreeMap<Double, Double>... mapSeries) {
       // public LineChart<Number, Number> plotAngle(List<Double> xAxisRange, List<Double> yAxisRange, List<String> seriesName, TreeMap<Double, Double>... mapSeries) {
		LineChart<Number, Number> lineChart = spl1.plotdB(xAxisRange, yAxisRange, seriesName, dB11, dB21, specLine);
		Scene scene  = new Scene(lineChart, 800, 600); 
		stage.setScene(scene);
		stage.setTitle("Line Chart Sample");
//		stage.setScene(spl1.plotAngle(xAxisRange, yAxisRange, seriesName, angle11, angle21));
        stage.show();
	} 
}