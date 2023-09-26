package funcSuites.graph;

import java.io.IOException;
import java.util.Map;

import funcSuites.snpToSpara.SnpToSparaMap;

import java.awt.EventQueue;

public class PlotType2Run {   
    public static void main(String[] args) {
    	String chartTitle = "chartTitle";
    	String xLabel = "xLabel";
    	String yLabel = "yLabel";

    	final String splPath2 ="D:\\Data\\InBand_s2p";
    	SnpToSparaMap spl1 = new SnpToSparaMap(splPath2);    	
    	spl1.setSnpPath(0,0,0);    // 폴더 및 파일의 경로 설정 (선택한 경로가 파일이면 이 설정은 무시된다.)
            	
    	var ex = new PlotType2(chartTitle, xLabel, yLabel);    	

		for (Map.Entry<Double, Double> keys : spl1.dB(1,1).entrySet()) {
			ex.series.get(0).add(keys.getKey(), keys.getValue());   
		}
		for (Map.Entry<Double, Double> keys : spl1.dB(2,1).entrySet()) {
			ex.series.get(1).add(keys.getKey(), keys.getValue());    
		}
		for (Map.Entry<Double, Double> keys : spl1.dB(1,2).entrySet()) {
			ex.series.get(2).add(keys.getKey(), keys.getValue());     
		}
		for (Map.Entry<Double, Double> keys : spl1.dB(2,2).entrySet()) {
			ex.series.get(3).add(keys.getKey(), keys.getValue());     
		}
    	
        EventQueue.invokeLater(() -> {            
            ex.setVisible(true);
        });
    }
}

