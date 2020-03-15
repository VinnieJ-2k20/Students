package com.gmail.ptimofejev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.gmail.ptimofejev.comparators.StudentAgeComparator;
import com.gmail.ptimofejev.comparators.StudentGenderComparator;
import com.gmail.ptimofejev.comparators.StudentIDComparator;
import com.gmail.ptimofejev.comparators.StudentSurnameComparator;
import com.gmail.ptimofejev.enumerations.Gender;
import com.gmail.ptimofejev.exceptions.GroupSizeExceededException;
import com.gmail.ptimofejev.interfaces.Voenkom;

public class Group implements Voenkom, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String faculty;
	private int grade;
	private List<Student> studentList = new ArrayList<Student>(10);
	
	public Group() {
		
	}

	public Group(String faculty, int grade, List<Student> studentList) {
		this.faculty = faculty;
		this.grade = grade;
		this.studentList = studentList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}
	
	public boolean addStudent(Student student) throws GroupSizeExceededException {
		if (student == null) {
			return false;
		}
		if (checkDuplication(student)) {
			return false;
		}
		if (studentList.size() >= 10) {
			throw new GroupSizeExceededException();
		}
		studentList.add(student);
		return true;
	}

	public boolean checkDuplication(Student student) {
		for (Student st : studentList) {
			if (st.equals(student)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addStudentInterative() throws GroupSizeExceededException {
		return addStudent(createStudentInteractive());
	}
	
	public Student createStudentInteractive() {
		Student junior = new Student();
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("Input new student`s surname:");
			junior.setSurname(scan.nextLine());
			System.out.println("Input new student`s name:");
			junior.setName(scan.nextLine());
			System.out.println("Input new student`s gender (1 - male, 2 - female): ");
			int genderSel = scan.nextInt();
			if (genderSel < 1 || genderSel > 2)
				throw new ArithmeticException();
			if (genderSel == 1) {
				junior.setGender(Gender.MALE);
			} else {
				junior.setGender(Gender.FEMALE);
			}
			System.out.println("Input new student`s age: ");
			int age = scan.nextInt();
			if (age < 0)
				throw new ArithmeticException();
			junior.setAge(age);
			System.out.println("Input new student`s ID (up to 6 digits): ");
			int studentID = scan.nextInt();
			if (studentID > 999999 || studentID < 0)
				throw new ArithmeticException();
			junior.setStudentID(studentID);
		} catch (ArithmeticException e) {
			System.out.println("Wrong inputs, try again");
			return null;
		}
		return junior;
	}
	
	public boolean removeStudent(String surname) {
		for (Student st : studentList) {
			if (st.getSurname().equalsIgnoreCase(surname)) {
				studentList.remove(st);
				return true;
			}
		}
		return false;
	}
	
	public Student searchBySurname(String surname) {
		for (Student st : studentList) {
			if (st.getSurname().equalsIgnoreCase(surname)) {
				return st;
			}
		}
		return null;
	}
	
	public void sortBySurname() {
		Collections.sort(studentList, new StudentSurnameComparator());
	}
	
	public void sortByAge() {
		Collections.sort(studentList, new StudentAgeComparator());
	}
	
	public void sortByGender() {
		Collections.sort(studentList, new StudentGenderComparator());
	}
	
	public void sortByStudentID() {
		Collections.sort(studentList, new StudentIDComparator());
	}
	
	@Override
	public List<Student> getRecruitsList() {
		List<Student> recruits = new ArrayList<Student>();
		for (Student st : studentList) {
			if (st.getAge() >= 18 && st.getGender() == Gender.MALE) {
				recruits.add(st);
			}
		}
		return recruits;
	}
	
	public void writeGroupToFile(File file) throws IOException {
		System.out.println(file.createNewFile());
		int written = 0;
		try (PrintWriter pw1 = new PrintWriter(file)) {
			for (Student st : studentList) {
				if (st != null) {
					pw1.println(st.getName() + ";" + st.getSurname() + ";"
							+ st.getAge() + ";" + st.getGender() + ";"
							+ st.getStudentID());
					written += 1;
				}
			}
			System.out.println("Group written to file. Entries: " + written);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readGroupFromFile(File file) throws GroupSizeExceededException {
		String nextline = null;
		int read = 0;
		int errors = 0;
		try (BufferedReader br1 = new BufferedReader(new FileReader(file))) {
			while ((nextline = br1.readLine()) != null) {
				String[] inputs = nextline.split(";");
				if (inputs.length != 5) {
					errors += 1;
					continue;
				}
				if (readStudentFromString(inputs) == null) {
					errors +=1; 
					continue;
				}
				addStudent(readStudentFromString(inputs));
				read += 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		System.out.println(read + " students added to group. Invalid entries: " + errors);
		}
	}
	
	public Student readStudentFromString(String[] inputs) {
		Student add = new Student();
		if (inputs[0].substring(0, 3).equals("﻿")) {
			inputs[0] = inputs[0].substring(3, inputs[0].length());
		}
		add.setName(inputs[0]);
		add.setSurname(inputs[1]);
		try {
			int age = Integer.valueOf(inputs[2]);
			if (age < 0) {
				throw new ArithmeticException();
			}
			add.setAge(age);
			Gender gender = Gender.valueOf(inputs[3]);
			add.setGender(gender);
			int studentID = Integer.valueOf(inputs[4]);
			if (studentID < 0 || studentID > 999999) {
				throw new ArithmeticException();
			}
			add.setStudentID(studentID);
		} catch (IllegalArgumentException | ArithmeticException e) {
			return null;
		}
		return add;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
		result = prime * result + grade;
		result = prime * result + ((studentList == null) ? 0 : studentList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (faculty == null) {
			if (other.faculty != null)
				return false;
		} else if (!faculty.equals(other.faculty))
			return false;
		if (grade != other.grade)
			return false;
		if (studentList == null) {
			if (other.studentList != null)
				return false;
		} else if (!studentList.equals(other.studentList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		sortBySurname();
		StringBuilder sb1 = new StringBuilder(faculty + " student list:" + System.lineSeparator());
		if (studentList.size() == 0) {
			return sb1.toString() + "Group is empty";
		}
		int num = 1;
		for (Student st : studentList) {
			sb1.append(num + ": " + st.toString() + System.lineSeparator());
			num+=1;
		}
		return sb1.toString();
	}
	
}
