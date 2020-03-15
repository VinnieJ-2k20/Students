package com.gmail.ptimofejev;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import com.gmail.ptimofejev.enumerations.Gender;
import com.gmail.ptimofejev.exceptions.GroupSizeExceededException;
import com.gmail.ptimofejev.interfaces.Voenkom;

public class Main {

	public static void main(String[] args) {
		Group group1 = new Group();
		group1.setFaculty("Four Chords");
		group1.setGrade(4);
		fillGroup(group1);
		System.out.println(group1);
		
		File groupBuffer = new File("Group Buffer.txt");
		try {
			System.out.println(groupBuffer.createNewFile());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try (ObjectOutputStream ous1 = new ObjectOutputStream(new FileOutputStream(groupBuffer))) {
			ous1.writeObject(group1);
			System.out.println("group successfully written");
		} catch (IOException e) {
			System.out.println("Error occured while writing the group");
		}
		Group deserializedGroup = null;
		try (ObjectInputStream ois1 = new ObjectInputStream(new FileInputStream(groupBuffer))) {
			deserializedGroup = (Group) ois1.readObject();
			System.out.println("Group successfully read");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error occured while reading the group");
		}
		System.out.println();
		System.out.println("Deserialized group:");
		System.out.println(deserializedGroup.toString());
		
		
	}

	public static void fillGroup(Group group) {
		try {
			group.addStudent(new Student("James", "Hetfield", Gender.MALE, 17, 630308));
			group.addStudent(new Student("Jimmy", "Hendrix", Gender.MALE, 17, 421127));
			group.addStudent(new Student("Lita", "Ford", Gender.FEMALE, 18, 580919));
			group.addStudent(new Student("Eric", "Clapton", Gender.MALE, 18, 450330));
			group.addStudent(new Student("George", "Harrison", Gender.MALE, 19, 430225));
			group.addStudent(new Student("Joan", "Jett", Gender.FEMALE, 17, 580922));
			group.addStudent(new Student("Keith", "Richards", Gender.MALE, 18, 431218));
			group.addStudent(new Student("Nancy", "Wilson", Gender.FEMALE, 19, 540316));
			group.addStudent(new Student("Jimmy", "Page", Gender.MALE, 19, 440109));
//			group.addStudent(new Student("John", "Fogerty", Gender.MALE, 18, 450528));
//			group.addStudent(new Student("Dave", "Grohl", Gender.MALE, 17, 690114));
		} catch (GroupSizeExceededException e) {
			System.out.println(e.getMessage());
		}
	}

}
