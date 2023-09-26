package funcSuites.sortTxtArray;

import java.util.ArrayList;
import java.util.Arrays;

public class SortTxtArrayStreamRun {	

	public static void main(String[] args) {
		String[] strArray = {
				"a3.3.s2p",
				"b02.s1p",
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
		
		SortTxtArrayStream sortTxt = new SortTxtArrayStream();

		System.out.println(Arrays.toString(sortTxt.sort(strArray)));
		


	}
}