package dataCode;

import java.util.Arrays;

public class Student implements Comparable<Student>
{
	private int grade = 0;
	private int id = 0;
	private static Student[] allStudents = new Student[4];
	
	public Student(int grade, int id) {
		this.grade = grade;
		this.id = id;
	}
	
	public static void main(String[] args) {
		Student Student1 = new Student(100, 1010);
		Student Student2 = new Student(80, 1012);
		Student Student3 = new Student(97, 1014);
		Student Student4 = new Student(94, 1016);
		allStudents[0] = Student1;
		allStudents[1] = Student2;
		allStudents[2] = Student3;
		allStudents[3] = Student4;
		Arrays.sort(allStudents);
		print();
	}
	
	public int getID() {
		return this.id;
	}
	
	public int getGrade() {
		return this.grade;
	}
	
	public int compareTo(Student Student2) {
		return this.grade - Student2.getGrade();
	}
	
	public static void print() {
		for(Student student: allStudents) {
			System.out.print(student.getID() + ",");
		}
	}
}
