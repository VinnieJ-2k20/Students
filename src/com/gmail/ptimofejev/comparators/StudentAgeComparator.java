package com.gmail.ptimofejev.comparators;

import java.util.Comparator;

import com.gmail.ptimofejev.Student;

public class StudentAgeComparator implements Comparator<Student> {

	@Override
	public int compare(Student o1, Student o2) {
		if (o1 != null && o2 == null) {
			return 1;
		}
		if (o1 == null && o2 != null) {
			return -1;
		}
		if (o1 == null && o2 == null) {
			return 0;
		}
		return o1.getAge() - o2.getAge();
	}

}
