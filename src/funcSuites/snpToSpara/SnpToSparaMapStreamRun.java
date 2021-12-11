package funcSuites.snpToSpara;

import java.io.IOException;
import java.util.Arrays;

public class SnpToSparaMapStreamRun {
	
	public static void main(String[] args) throws IOException {
		long beforeTime = System.currentTimeMillis();

		final String splPath ="D:\\Data\\InBand_s2p";  // snp 파일을 포함하는 최상위 폴더

		SnpToSparaMapStream spl1 = new SnpToSparaMapStream(splPath);  // S parameter 추출을 위한 객체 생성		
		
		spl1.setSnpPath(1,1,20);    // 폴더 및 파일의 경로 설정 (선택한 경로가 파일이면 이 설정은 무시된다.)
		
		
		System.out.println(""); 
		System.out.println("[1] 각 섹션의 첫 번째 폴더와 파일은 " + Arrays.toString(spl1.pathTree(-1,0,0))); 
		System.out.println("[1] 각 섹션의 첫 번째 폴더와 파일은 " + Arrays.toString(spl1.pathTree(0,-1,0))); 
		System.out.println("[1] 각 섹션의 첫 번째 폴더와 파일은 " + Arrays.toString(spl1.pathTree(0,0,-1))); 
		System.out.println("[2] 최상위 폴더에서 4번째 폴더의 하위폴더를 표시" + Arrays.toString(spl1.pathTree(3,-1,-1)));  // -1은 첫 번째 값만 의미있고, 그 이후의 값들은 무시된다. 
		
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

		

		
		
//		System.out.println(Arrays.toString(spl1.getSnpPath()));
//		System.out.println(Arrays.toString(spl1.pathTree(3,-1,-1)));
//		System.out.println(spl1.freq.toString());  // 객체 spl1의 S22 dB 값
//		System.out.println(spl1.dB(1,2).get(3.1E9));  // 객체 spl1의 S22 dB 값
//		System.out.println(spl1.angle(1,2).get(3.1E9));  // 객체 spl1의 S22 dB 값
//		System.out.println(spl1.dB(1,2).get(4.1E9));  // 객체 spl1의 S22 dB 값
//		System.out.println(spl1.angle(1,2).get(4.1E9)); 
		
		
		
		
		
		

//		System.out.println(Arrays.toString(spl1.pathTree(3,-1,-1)));
//		System.out.println(spl1.freq().toString());  // 객체 spl1의 S22 dB 값
//		System.out.println(spl1.dB(1,2).get(3.1E9));  // 객체 spl1의 S22 dB 값
//		System.out.println(spl1.angle(1,2).get(3.1E9));  // 객체 spl1의 S22 dB 값
//		System.out.println(spl1.dB(1,2).get(4.1E9));  // 객체 spl1의 S22 dB 값
//		System.out.println(spl1.angle(1,2).get(4.1E9)); 
//		
//		System.out.println(spl1.freq.size());  // 객체 spl1의 S22 dB 값
//		System.out.println(spl1.dB(2,2).size());
//		System.out.println(spl1.angle(2,2).size());
//		System.out.println(portNum1);
//		System.out.println(Arrays.toString(spl1.Angle(3,3)));
//		
//		SnpToSpara spl2 = new SnpToSpara(spl2Path);
//		Integer[] folderTree = spl2.pathTree();
//		Long[] freq2 = sp2.freqSet();
//		int portNum2 = spl2.portNumSet();
//		
//		System.out.println(Arrays.toString(folderTree));
//		System.out.println(portNum2);
//		System.out.println(Arrays.toString(spl2.dB(3,3)));
//		System.out.println(Arrays.toString(spl2.Angle(3,3)));
	
//		System.out.println(freq[0]);
//		System.out.println(freq[1]);
//		System.out.println(freq[2]);
//		System.out.println(freq[3]);	
//		System.out.println(freq.length);
		
		long afterTime = System.currentTimeMillis(); 
		long secDiffTime = (afterTime - beforeTime);
		System.out.println("시간차이(m) : "+secDiffTime);
	}
}