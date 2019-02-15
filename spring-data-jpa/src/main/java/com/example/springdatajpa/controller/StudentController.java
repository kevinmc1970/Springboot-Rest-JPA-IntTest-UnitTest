package com.example.springdatajpa.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
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
	
	// this tests controlleradvice - need to uncomment customized Handler class
	@PostMapping("/students/new")
	public ResponseEntity<Void> registerStudentFor(@Valid @RequestBody Student newStudent) {
		Student student = null;
		try {
			student = studentService.addStudent(newStudent);
		} catch (Exception ex) {
	        throw new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Student not created - exception handled by spring 5 ResponseStatusException ", ex);
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(student.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	// this tests spring 5 ResponseStatusException - bascially throws this exception which is simply added to response body
	// can be used in conjunction with controlleradvice too (if remove @Valid here so no clash)
	@PostMapping("/students/newError")
	public ResponseEntity<Void> registerStudentForError(@RequestBody Student newStudent) {
		Student student = null;

		// basically would throw this exception either in catch block or when something goes wrong
		// can then change the error message to suit match the cause etc
		throw new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Student not created - exception handled by spring 5 ResponseStatusException ");
	}
	
	
	@GetMapping("/students/{studentId}")
	public Student retrieveStrudent(@PathVariable String studentId) {
		return studentService.retrieveStudent(studentId);
	}
}
