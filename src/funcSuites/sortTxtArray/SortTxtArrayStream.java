package funcSuites.sortTxtArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class SortTxtArrayStream {
	String[] txtArray = null;
	
	// sort() is applied to any combination of String and Number.
	public String[] sort(String[] txtArray) {
		this.txtArray = txtArray;
		Integer[] indexSort = txtArrayRank();
		String[] sortedTxtArray = new String[indexSort.length];
		for (int i = 0; i < indexSort.length; i++)
        {
        	sortedTxtArray[i] = txtArray[indexSort[i]];
        }
		return sortedTxtArray;
	}
	
	public ArrayList<String> sort(ArrayList<String> txtList) {
		this.txtArray = txtList.toArray(new String[0]);	
		Integer[] indexSort = txtArrayRank();
		String[] sortedTxtArray = new String[indexSort.length];
		for (int i = 0; i < indexSort.length; i++)
        {
        	sortedTxtArray[i] = this.txtArray[indexSort[i]];
        }
		ArrayList<String> sortedTxtList = new ArrayList<>(Arrays.asList(sortedTxtArray));
		return sortedTxtList;		
	}
	
	public  String[] sort(File[] txtFile) {
		this.txtArray = new String[txtFile.length];
		for (int i = 0; i < txtFile.length; i++) {
			this.txtArray[i] = txtFile[i].getPath();
		}		
		Integer[] indexSort = txtArrayRank();
		String[] sortedTxtArray = new String[indexSort.length];
		for (int i = 0; i < indexSort.length; i++)
        {
        	sortedTxtArray[i] = this.txtArray[indexSort[i]];
        }
		return sortedTxtArray;
	}
		
	// The reason for using rank without using sort is that rank gives the same value to the same data, but sort is arbitrarily ordered.
	// txtArrayRank() is effective for mixed-String-Number.
	private Integer[] txtArrayRank() {
		String[] str0 = new String[this.txtArray.length];
		String[] str1 = new String[this.txtArray.length];
		Double[] double1 = new Double[this.txtArray.length];
		String[] str2 = new String[this.txtArray.length];
		
		for(int i=0; i<this.txtArray.length; i++) {
			String delExt = this.txtArray[i].replaceAll("\\.\\w+$", "");
			String delExtTail = delExt.replaceAll("\\.?\\D+$", "");
			String delExtTailNum = delExtTail.replaceAll("\\d+\\.?\\d*$", "");
			
			str0[i] = this.txtArray[i].substring(0, delExtTailNum.length());
			str1[i] = this.txtArray[i].substring(delExtTailNum.length(), delExtTail.length());
			double1[i] = Double.parseDouble(str1[i]);
			str2[i] = this.txtArray[i].substring(delExtTail.length(), delExt.length());	
		}
			
		Integer[] rank0 = rankIndexSub(str0);
		Integer[] rank1 = rankIndexSub(double1);
		Integer[] rank2 = rankIndexSub(str2);
		Integer[] rankSum = new Integer[this.txtArray.length];
		
		for(int i=0; i<this.txtArray.length; i++) {
			rankSum[i] = rank0[i]*1000000 + rank1[i]*1000 + rank2[i];
		}		
		Integer[] sortedIndex = sortIndexSub(rankSum);
		return sortedIndex;
	}	
	
	public Integer[] rankIndexSub(String[] txtArray) {
		Integer[] indexStr= new Integer[txtArray.length];		
		for (int i = 0; i < txtArray.length; i++) {
			int indexPivot = 1;
			for (int j = 0; j < txtArray.length; j++) {
				if(txtArray[i].compareTo(txtArray[j]) > 0 ) {
					indexPivot++;
				}
			}
			indexStr[i] = indexPivot;
		}
		return indexStr;
	}
	
	public Integer[] rankIndexSub(Integer[] txtArray) {
		Integer[] indexStr= new Integer[txtArray.length];		
		for (int i = 0; i < txtArray.length; i++) {
			int indexPivot = 1;
			for (int j = 0; j < txtArray.length; j++) {
				if(txtArray[i].compareTo(txtArray[j]) > 0 ) {
					indexPivot++;
				}
			}
			indexStr[i] = indexPivot;
		}
		return indexStr;
	}
	
	public Integer[] rankIndexSub(Double[] txtArray) {
		Integer[] indexStr= new Integer[txtArray.length];		
		for (int i = 0; i < txtArray.length; i++) {
			int indexPivot = 1;
			for (int j = 0; j < txtArray.length; j++) {
				if(txtArray[i].compareTo(txtArray[j]) > 0 ) {
					indexPivot++;
				}
			}
			indexStr[i] = indexPivot;
		}
		return indexStr;
	}
	
	// sortIndexSub is used to sort String, but it is not effective for mixed-String-Number.
	public Integer[] sortIndexSub(String[] txtArray) {
		Integer[] indexStr= Arrays.stream(IntStream.range(0, txtArray.length).toArray()).boxed().toArray(Integer[]::new);
	    Comparator<Integer> txtArrayIndexComparatorInstance = (x1, x2) -> txtArray[x1].compareTo(txtArray[x2]);		
//		for (int i = 0; i < txtArray.length; i++)
//        {
//        	indexStr[i] = i;
//        }		  
//		Comparator<Integer> txtArrayIndexComparatorInstance = new Comparator<Integer>() {   
//	    @Override
//	    public int compare(Integer index1, Integer index2) {
//	        return txtArray[index1].compareTo(txtArray[index2]);
//	    }};
	    Arrays.sort(indexStr,  txtArrayIndexComparatorInstance); // This sort is different from method sort of SortTxtArray.
		return indexStr;
	}
	
	public Integer[] sortIndexSub(Double[] txtArray) {
		Integer[] indexStr= Arrays.stream(IntStream.range(0, txtArray.length).toArray()).boxed().toArray(Integer[]::new);
	    Comparator<Integer> txtArrayIndexComparatorInstance = (x1, x2) -> txtArray[x1].compareTo(txtArray[x2]);	
//		for (int i = 0; i < txtArray.length; i++)
//        {
//        	indexStr[i] = i;
//        }		
//		Comparator<Integer> txtArrayIndexComparatorInstance = new Comparator<Integer>() {   
//		    @Override
//		    public int compare(Integer index1, Integer index2) {
//		        return txtArray[index1].compareTo(txtArray[index2]);
//		    }
//		};		
		Arrays.sort(indexStr,  txtArrayIndexComparatorInstance); // This sort is different from method sort of SortTxtArray.
		return indexStr;
	}
	
	public Integer[] sortIndexSub(Integer[] txtArray) {
		Integer[] indexStr= Arrays.stream(IntStream.range(0, txtArray.length).toArray()).boxed().toArray(Integer[]::new);
	    Comparator<Integer> txtArrayIndexComparatorInstance = (x1, x2) -> txtArray[x1].compareTo(txtArray[x2]);	
//		for (int i = 0; i < txtArray.length; i++)
//        {
//        	indexStr[i] = i;
//        }		
//		Comparator<Integer> txtArrayIndexComparatorInstance = new Comparator<Integer>() {   
//		    @Override
//		    public int compare(Integer index1, Integer index2) {
//		        return txtArray[index1].compareTo(txtArray[index2]);
//		    }
//		};
		Arrays.sort(indexStr,  txtArrayIndexComparatorInstance); // This sort is different from method sort of SortTxtArray.
		return indexStr;
	}	
	

}
