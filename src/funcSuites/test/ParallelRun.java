package funcSuites.test;

import java.util.List;
import java.util.stream.Stream;

public class ParallelRun {

	public static void main(String[] args) {
		List<String> list = List.of("홍길동", "신용권", "감자바", "람다식", "박병렬");
		
		
		//순차처리
		Stream<String> stream = list.stream();
		stream.forEach(x -> print(x));
		
		System.out.println();
		//병렬처리
		Stream<String> parallelStream = list.parallelStream();
		parallelStream.forEach(x -> print(x));
	}
	
	public static void print(String str) {
		System.out.println(str + " : " + Thread.currentThread().getName());
	}
	
	
	
	
}


	
