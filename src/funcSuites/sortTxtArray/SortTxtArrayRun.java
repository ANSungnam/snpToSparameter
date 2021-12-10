package funcSuites.sortTxtArray;

import java.util.ArrayList;
import java.util.Arrays;

public class SortTxtArrayRun {	

	public static void main(String[] args) {
		String[] strArray = {
				"01.s2p",
				"02.s1p",
				"3.s2p",
				"10.s2p",
				"1.1.s2p" 
				};
		String[] strArray2 = {
				"11.s2p",
				"01.s1p",
				"3.s2p",
				"10.s2p",
				"1.1.s2p" 
				};
		ArrayList<String> strList2 = new ArrayList<>(Arrays.asList(strArray2));		
		
		SortTxtArray sortTxt = new SortTxtArray();
		
//		System.out.println(Arrays.toString(sortTxt.sort(strArray)));
		System.out.println(Arrays.toString(sortTxt.sort(strArray)));

	}
}