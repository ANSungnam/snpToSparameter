/*  
	1. 한글은 utf-8로 작성하였다.
	2. 1차원 배열을 불러와서 정렬하여 전달해준다. 문자가 숫자가 혼합된 이름에 대해서도 정렬이 가능하다.
	  1) public String[] sort(String[] txtArray): 1차원 배열을 받아서 정렬해준다.
	    - txtArrayRank()는 1차원 배열에 대해서 정렬 순위를 매기는데, 같은 값의 데이터는 동일한 순위를 매긴다.
	    - 정렬된 인덱스를 1차원 배열에 반영하여 정렬한다.
	  2) txtArrayRank(): 문자가 숫자가 혼합된 이름을 정렬해준다.
	    - 입력 받은 문자열은 다음의 규칙을 가진다.
	    - (문자열 혹은 숫자로 시작) + (숫자이거나 빈칸) + (문자이거나 빈칸) + .확장자
	    - 확장자를 제외한 나머지 문자열을 세 개의 문자열로 분리한다.
	    - 첫번째 문자열에 순위를 매기고 1000000를 곱하고, 
	      두 번째 문자열에 순위를 매기고, 1000을 곱하고, 
	      세 번째 문자열에 순위를 매기고, 1을 곱한 후, 
	      세 개의 숫자를 더하면 그 문자열의 순위를 정확하게 매길 수 있다. 	     
	  3) public Integer[] rankIndexSub(String[] txtArray): sort가 아니라 순위를 매긴다.
	    - sort의 문제점은 같은 문자열도 다른 index값으로 처리하지만,
	    - rank는 같은 문자열을 동일한 순위로 매길 수 있기 때문에 
	    - 문자열을 쪼개서 가중치를 부여하기에 적절한 방식이다.
	  4) public Integer[] sortIndexSub(String[] txtArray)
	    - 이 메서드는 이 클래스에서 사용되지 않았다. 
	    - 이유는 문자와 숫자 조합의 문자열에는 적용하기에 적당하지 않기 때문이다.
*/

/*
	1. Korean was written in utf-8.
	2. Calls a one-dimensional array, sorts it and delivers it. Sorting is also possible for names with mixed letters and numbers.
		1) public String[] sort(String[] txtArray): Receives a one-dimensional array and sorts it.
			- txtArrayRank() ranks a one-dimensional array, and data with the same value is ranked the same.
			- Sort by reflecting the sorted index in the one-dimensional array.
		2) txtArrayRank(): Sorts a name with a mixture of letters and numbers.
			- The input string has the following rules.
			- (starts with a string or number) + (number or space) + (letters or space) + .extension
			- Separates the remaining strings except for the extension into three strings.
			- rank the first string and multiply by 1000000,
			rank the second string, multiply by 1000,
			rank the third string, multiply by 1,
			By adding up the three numbers, you can rank the string correctly.
		3) public Integer[] rankIndexSub(String[] txtArray): It ranks, not sort.
			- The problem with sort is that the same string is treated as a different index value, but
			Because rank can rank the same string in the same rank
			- This is an appropriate way to split a string and assign weights to it.
		4) public Integer[] sortIndexSub(String[] txtArray)
			- This method is not used in this class.
			- The reason is that it is not suitable to apply to character and numeric character strings.
*/

package funcSuites.sortTxtArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
	
	public List<String> sort(List<String> txtList) {
		this.txtArray = txtList.toArray(new String[0]);	
		Integer[] indexSort = txtArrayRank();
		String[] sortedTxtArray = new String[indexSort.length];
		for (int i = 0; i < indexSort.length; i++)
        {
        	sortedTxtArray[i] = this.txtArray[indexSort[i]];
        }
		List<String> sortedTxtList = new ArrayList<>(Arrays.asList(sortedTxtArray));
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
