/*  
	1. 한글은 utf-8로 작성하였다.
	2. snp 파일을 불러와서  S parameter로 변환하는 프로그램이다.
	  1) public SnpToSparaMap(String snpFolder) : 생성자 초기화
	    - snp 파일을 포함하는 최상위 폴더의 경로를 final로 입력받는다.abstract
	    - p최상위 폴더부터 snp 파일까지의 경로 구조, 즉 pathPath가 어떻게 되는지를 결정하기 위해, pathTreeSize를 구한다.
	  2) public String[] pathTree(Integer... arr) : 폴더의 pathTree를 확인할 수 있다.
	    - pathTree(0,0,0)은 폴더1(0), 폴더2(0), 파일명(0)의 이름을 나타낸다.abstract
	    - 섹션 중에서 -1을 사용하면 그 계층의 폴더 전체를 나타낸다. 예를들면, pathTree(0,-1,0)은 폴더1(0)의 하위 폴더 전체를 나타낸다. 
	  3) snpPath의 getter와 setter[ public void setSnpPath(Integer... snpPath) ]
	    - snpPath가 변경되면 불러오는 snp 파일이 변경되기 때문에, freq, dB, angle의 값도 변경된다.class  -
	  4) private ArrayList<ArrayList<Double>> snpToDataInit()  
	    - snp 파일에서 주석을 제외한 데이터를 추출하고, string에서 2D double list 로 변환한다.
	  5) snpToData로부터, freq, dB, angle을 구하고, dB와 angle은 freq와 합하여 map 형태로 만든다.
*/

/*
	1. Korean was written in utf-8.
	2. It is a program that calls snp file and converts it to S parameter.
		1) public SNPToSparaMap(String snpFolder) : Initialize the constructor
			- Receives the path of the top-level folder including the snp file as final.
			- In order to determine the path structure from the top-level folder to the snp file, that is, pathPath, find pathTreeSize.
		2) public String[] pathTree(Integer... arr) : You can check the pathTree of the folder.
			- pathTree(0,0,0) indicates the names of folder 1 (0), folder 2 (0), and file name (0). abstract
			- If -1 is used in the section, the entire folder in the hierarchy is indicated. For example, pathTree(0,-1,0) represents the entire subfolder of folder 1(0).
		3) snpPath getter and setter[ public void setSnpPath(Integer... snpPath) ]
			- If the snpPath is changed, the snp file to be called is changed, so the values ​​of freq, dB, and angle are also changed. class -
		4) private ArrayList<ArrayList<Double>> snpToDataInit()
			- Extracts data excluding comments from snp file and converts it from string to 2D double list.
		5) Obtain freq, dB, and angle from snpToData, and combine dB and angle with freq to form a map.
*/

package funcSuites.snpToSpara;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import funcSuites.sortTxtArray.SortTxtArrayStream;

public class SnpToSparaMapStream {
	public SortTxtArrayStream sortTxt = new SortTxtArrayStream();    // Sort Array
	public final String snpFolder;
	public final Integer[] pathTreeSize;
	private Integer[] snpPath;
	
	private List<ArrayList<Double>> snpToData;
	public List<Double> freq;	
	
	public SnpToSparaMapStream(String snpFolder) {
		this.snpFolder = snpFolder;
		this.pathTreeSize = this.pathTreeInit();
		System.out.println("* The pathTreeSize of the " + this.getClass().getSimpleName() + " object is " +  Arrays.toString(pathTreeSize) + ".");
		System.out.println("** Firstly, please set the snp file like this:    instance.setSnpPath(0,0,0, ...); ");
		System.out.println("*** In the index of pathTree, " +(-1) + " indicates the entire folder list in the section, and Only the first " + (-1) + " is meaningful.\n");
	}

	// 폴더의 계층이 어떤지 확인
	private Integer[] pathTreeInit() {
		// ** Folder tree of samples
		List<Integer> pathList = new ArrayList<Integer>();    	
		String pathReference = this.snpFolder;
		Path path = Paths.get(pathReference);	
		int pathTreePosition;
		try {
			for (pathTreePosition=0; Files.walk(path, pathTreePosition+1).count()>Files.walk(path, pathTreePosition).count(); pathTreePosition++) {
				File[] fileList = new File(pathReference).listFiles();			
				List<String> pathString = Arrays.stream(fileList).map(x->x.getPath()).collect(Collectors.toList());
				pathReference = pathString.get(0);
				pathList.add(pathString.size());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return pathList.toArray(new Integer[0]);
	}

	public String[] pathTree(Integer... pathTreeIndex) {		
		List<String> pathNameList = new ArrayList<>();    	
    	String pathReference = this.snpFolder;
		Path path = Paths.get(pathReference);
		try {
			for (int pathTreeSearch=0; Files.walk(path, pathTreeSearch+1).count()>Files.walk(path, pathTreeSearch).count(); pathTreeSearch++) {
				File[] fileList = new File(pathReference).listFiles();
				List<String> pathNameString = sortTxt.sort(Arrays.stream(fileList).map(x->x.getName()).collect(Collectors.toList()));
				List<String> pathString = sortTxt.sort(Arrays.stream(fileList).map(x->x.getPath()).collect(Collectors.toList()));

				if(pathTreeIndex[pathTreeSearch]<0) {
					return pathNameString.toArray(new String[0]);	
				} else {
					pathReference = pathString.get(pathTreeIndex[pathTreeSearch]);
					pathNameList.add(pathNameString.get(pathTreeIndex[pathTreeSearch]));				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pathNameList.toArray(new String[0]);
	}
	
	public Integer[] getSnpPath() {
		return snpPath;
	}
	
	public void setSnpPath(Integer... snpPath) {
		this.snpPath = snpPath;
		this.snpToData = this.snpToDataInit();
		this.freq = this.freqInit();
	}
	
	// snp 파일에서 주석을 제거하고 data만 추출
	private List<ArrayList<Double>> snpToDataInit() {    	
		List<ArrayList<Double>> listRawData = new ArrayList<ArrayList<Double>>();
    	String pathReference = this.snpFolder;
		Path path = Paths.get(pathReference);	

		try {
			for (int pathTreePosition=0; Files.walk(path, pathTreePosition+1).count()>Files.walk(path, pathTreePosition).count(); pathTreePosition++) {
				File[] fileList = new File(pathReference).listFiles();			
				List<String> pathString = sortTxt.sort(
						Arrays.stream(fileList).map(x->x.getPath()).collect(Collectors.toList()));
				pathReference = pathString.get(snpPath[pathTreePosition]);	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			List<String> strRawData;		
			strRawData = Files.lines(Paths.get(pathReference))
				.filter(x->!Character.valueOf(x.charAt(0)).equals('!') && !Character.valueOf(x.charAt(0)).equals('#'))
				.collect(Collectors.toList());
			
			for (int i=0; i<strRawData.size(); i++) {
				String[] lineArray =strRawData.get(i).split("\t");
				listRawData.add(new ArrayList<Double>());
				for (int j=0; j<lineArray.length; j++) {
					listRawData.get(listRawData.size()-1).add(Double.parseDouble(lineArray[j]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listRawData;
	}
	
	// 데이터 구조에서 포트 개수를 확인
	public int portNum() {
		List<ArrayList<Double>> RawData = this.snpToData;	
		int portNumber = 0;
		if ((RawData.get(0).size() == 9) && (Double.toString(RawData.get(1).get(0)).length() != 0)) {
			portNumber = 2;
		} else if ((RawData.get(0).size() == 7) && (Double.toString(RawData.get(1).get(0)).length() != 0)) {
			portNumber = 3;
		} else if ((RawData.get(0).size() == 9) && (Double.toString(RawData.get(1).get(0)).length() != 0)) {
			portNumber = 4;
		}
		return portNumber;
	}

	// 주파수 추출
	private List<Double> freqInit() {
		List<ArrayList<Double>> RawData = this.snpToData;	
		int portNum = this.portNum();
		List<Double> freqList = new ArrayList<>();
		
		if (portNum == 2) {
			for (int i = 0; i < RawData.size(); i++) {
				freqList.add(RawData.get(i).get(0));
			}
		} else if (portNum == 3) {
			for (int i = 0; i < RawData.size(); i = i + portNum) {
				freqList.add(RawData.get(i).get(0));
			}
		} else if (portNum == 4) {
			for (int i = 0; i < RawData.size(); i= i + portNum) {
				freqList.add(RawData.get(i).get(0));
			}
		}
		return freqList;
	}
	
	// dB값 추출
	public TreeMap<Double, Double> dB(int numOutput, int numInput) {		
		List<ArrayList<Double>> RawData = this.snpToData;	
		int portNum = this.portNum();
		List<Double> dBList = new ArrayList<>();
		
		if (portNum == 2) {
			for (int i = 0; i < RawData.size(); i++) {
				dBList.add(RawData.get(i).get((numOutput-1)*2 +1 + (numInput-1)*4));
			}
		} else if ((portNum == 3) && (numInput == 1)) {
			for (int i = 0; i < RawData.size(); i = i + portNum) {
				dBList.add(RawData.get(i).get((numOutput-1)*2 + 1));
			}			
		} else if ((portNum == 3) && (numInput == 2)) {
			for (int i = 1; i < RawData.size(); i = i + portNum) {
				dBList.add(RawData.get(i).get((numOutput-1)*2 + 1));
			}
		} else if ((portNum == 3) && (numInput == 3)) {
			for (int i = 2; i < RawData.size(); i = i + portNum) {
				dBList.add(RawData.get(i).get((numOutput-1)*2 + 1));
			}
		} else if ((portNum == 4) && (numInput == 1)) {
			for (int i = 0; i < RawData.size(); i = i + portNum) {
				dBList.add(RawData.get(i).get((numOutput-1)*2 + 1));
			}			
		} else if ((portNum == 4) && (numInput == 2)) {
			for (int i = 1; i < RawData.size(); i = i + portNum) {
				dBList.add(RawData.get(i).get((numOutput-1)*2 + 1));
			}			
		} else if ((portNum == 4) && (numInput == 3)) {
			for (int i = 2; i < RawData.size(); i = i + portNum) {
				dBList.add(RawData.get(i).get((numOutput-1)*2 + 1));
			}			
		} else if ((portNum == 4) && (numInput == 4)) {
			for (int i = 3; i < RawData.size(); i = i + portNum) {
				dBList.add(RawData.get(i).get((numOutput-1)*2 + 1));
			}			
		}
		TreeMap<Double, Double> dBMap = new TreeMap<>();
		for(int i = 0; i<dBList.size(); i++) {
		    dBMap.put(freq.get(i), dBList.get(i));
		}
		return dBMap;
	}
	
	// S parameter의  angle 추출
	public TreeMap<Double, Double> angle(int numOutput, int numInput) {		
		List<ArrayList<Double>> RawData = this.snpToData;	
		int portNum = this.portNum();
		List<Double> angleList = new ArrayList<>();
		
		if (portNum == 2) {
			for (int i = 0; i < RawData.size(); i++) {
				angleList.add(RawData.get(i).get((numOutput-1)*2 +2 + (numInput-1)*4));
			}
		} else if ((portNum == 3) && (numInput == 1)) {
			for (int i = 0; i < RawData.size(); i = i + portNum) {
				angleList.add(RawData.get(i).get((numOutput-1)*2 + 2));
			}			
		} else if ((portNum == 3) && (numInput == 2)) {
			for (int i = 1; i < RawData.size(); i = i + portNum) {
				angleList.add(RawData.get(i).get((numOutput-1)*2 + 2));
			}
		} else if ((portNum == 3) && (numInput == 3)) {
			for (int i = 2; i < RawData.size(); i = i + portNum) {
				angleList.add(RawData.get(i).get((numOutput-1)*2 + 2));
			}
		} else if ((portNum == 4) && (numInput == 1)) {
			for (int i = 0; i < RawData.size(); i = i + portNum) {
				angleList.add(RawData.get(i).get((numOutput-1)*2 + 2));
			}			
		} else if ((portNum == 4) && (numInput == 2)) {
			for (int i = 1; i < RawData.size(); i = i + portNum) {
				angleList.add(RawData.get(i).get((numOutput-1)*2 + 2));
			}			
		} else if ((portNum == 4) && (numInput == 3)) {
			for (int i = 2; i < RawData.size(); i = i + portNum) {
				angleList.add(RawData.get(i).get((numOutput-1)*2 + 2));
			}			
		} else if ((portNum == 4) && (numInput == 4)) {
			for (int i = 3; i < RawData.size(); i = i + portNum) {
				angleList.add(RawData.get(i).get((numOutput-1)*2 + 2));
			}			
		}
		TreeMap<Double, Double> angleMap = new TreeMap<>();
		for(int i = 0; i<angleList.size(); i++) {
		    angleMap.put(freq.get(i), angleList.get(i));
		}
		return angleMap;
	}
}