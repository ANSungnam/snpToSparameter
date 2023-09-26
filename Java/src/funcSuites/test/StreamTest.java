package funcSuites.test;

import java.util.List;
import java.util.stream.Stream;

public class StreamTest {
	public static void main(String[] args) {
		List<String> list = List.of("홍길동", "신용권", "감자바");
		Stream<String> stream = list.stream();
		stream.forEach(x -> System.out.println	(x));
		
		List<Student> list1 = List.of(new Student("홍길동", 90), new Student("신용권", 92));
		Stream<Student> stream1 = list1.stream();
		stream1.forEach(x -> {
//			String name = x.getName();
//			Integer score = x.getScore();
			System.out.println(x.name + "-" + x.score);
		});
	}
}

class Student {
	public String name;
	public Integer score;
	
	Student(String name, Integer score){
		this.name = name;
		this.score = score;
	}
	
	String getName() {
		return this.name;
	}
	
	Integer getScore() {
		return this.score;
	}
}
