package funcSuites.sortTxtArray;

import java.util.Comparator;

class ArrayIndexComparator implements Comparator<Integer>  
// ArrayIndexComparator comparator = new ArrayIndexComparator(String[] array);
// Arrays.sort(comparator.indexStr, comparator)	
// comparator.indexStr : sorted
{
    private final String[] array;
    public Integer[] indexStr;
    public ArrayIndexComparator(String[] array)
    {
        this.array = array;
        indexStr = new Integer[array.length];
        for (int i = 0; i < array.length; i++)
        {
        	indexStr[i] = i;
        }
    }    
    @Override
    public int compare(Integer index1, Integer index2)
    {
        return array[index1].compareTo(array[index2]);
    }
}