package com.gmail.ptimofejev;

import java.io.Serializable;

import com.gmail.ptimofejev.enumerations.Gender;

public class Student extends Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int studentID;

	public Student() {
		super();
	}

	public Student(String name, String surname, Gender gender, int age, int studentID) {
		super(name, surname, gender, age);
		this.studentID = studentID;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + studentID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (studentID != other.studentID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String text = super.toString() + ", studentID: " + studentID + ";";
		return text;
	}

}
