package com.example.springdatajpa.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.springdatajpa.model.Student;
import com.example.springdatajpa.service.StudentService;


@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class, secure = false)
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService studentService;

	Student mockStudent = new Student("Student123", "Test", "Tester");
	
	List<Student> mockList = new ArrayList<Student>();	
	String exampleStudentJSON = "{ \"name\":\"New Student\",\"description\":\"New\"}";

	// unit-testing the code in the Controller (although doesn't do much) and mocking service it calls
	// uses mockmvc to mock the endpoint requests
	@Test
	public void retrieveAllStudents() throws Exception {

		mockList.add(mockStudent);
		Mockito.when(
				studentService.retrieveAllStudents()).thenReturn(mockList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/studentlist").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "[{\"id\":\"Student123\",\"name\":\"Test\",\"description\":\"Tester\"}]";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}

	@Test
	public void createStudent() throws Exception {

		Mockito.when(
				studentService.addStudent(Mockito
						.any(Student.class))).thenReturn(mockStudent);

		//Send student as body to /students/new
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/students/new")
				.accept(MediaType.APPLICATION_JSON).content(exampleStudentJSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/students/new/Student123", response
				.getHeader(HttpHeaders.LOCATION));

	}

}