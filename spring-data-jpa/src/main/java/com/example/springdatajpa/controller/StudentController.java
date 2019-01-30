package com.example.springdatajpa.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.springdatajpa.model.Student;
import com.example.springdatajpa.service.StudentService;


@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/studentlist")
	public List<Student> retrieveStudents() {
		return studentService.retrieveAllStudents();
	}
	
	@GetMapping("/test")
	public String test() {
		return "hello";
	}
	
	@PostMapping("/students/new")
	public ResponseEntity<Void> registerStudentFor(@RequestBody Student newStudent) {
		
		Student student = studentService.addStudent(newStudent);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(student.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/students/{studentId}")
	public Student retrieveStrudent(@PathVariable String studentId) {
		return studentService.retrieveStudent(studentId);
	}
}
