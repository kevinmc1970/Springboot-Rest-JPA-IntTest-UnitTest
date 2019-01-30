package com.example.springdatajpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springdatajpa.model.Student;
import com.example.springdatajpa.model.StudentRepository;


@Component
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	static {
		//Initialize Data

		Student ranga = new Student("Student1", "Ranga Karanam",
				"Hiker, Programmer and Architect");

		Student satish = new Student("Student2", "Satish T",
				"Hiker, Programmer and Architect");
	}

	public List<Student> retrieveAllStudents() {
		return (List<Student>) studentRepository.findAll();
	}

	public Student retrieveStudent(String studentId) {
		Optional studentOptional = studentRepository.findById(studentId);
		return (Student) studentOptional.orElseGet(null);
	}

	public Student addStudent(Student newStudent) {
		// TODO Auto-generated method stub
		return studentRepository.save(newStudent);
	}

	public void delete(Student student) {
		studentRepository.delete(student);
		
	}

}
