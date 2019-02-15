package com.example.springdatajpa.controller;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springdatajpa.SpringDataJpaApplication;
import com.example.springdatajpa.model.Student;
import com.example.springdatajpa.service.StudentService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataJpaApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

// with this being a test the in-memory Derby database is used rather than the actual database
// everything else is the actual service and endpoints hit to test responses etc 
public class StudentControllerIT {

	@LocalServerPort
	private int port;
		
	@Autowired
	StudentService studentService;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	
	Student testStudent;

	@Before
	public void before() {
//		headers.add("Authorization", createHttpAuthenticationHeaderValue(
//				"user1", "secret1"));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		// add testStudent to database
		testStudent = new Student("Student1", "Kevin",
				"Programmer");
		studentService.addStudent(testStudent);
	}
	
	@After
	public void after() {
		//delete testStudent
		studentService.delete(testStudent);
	}

	@Test
	public void testRetrieveStudents() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/studentlist"),
				HttpMethod.GET, entity, String.class);

		String expected = "[{name:Kevin,description:Programmer}]";

		System.out.println("******************************response = " + response.getBody());
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	public void addStudent() {

		Student student = new Student("Student1", "Ranga Karanam",
				"Hiker, Programmer and Architect");

		HttpEntity<Student> entity = new HttpEntity<Student>(student, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/students/new"),
				HttpMethod.POST, entity, String.class);

		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
		System.out.println("########### actual - " + actual);
		assertTrue(actual.contains("/students/new/"));

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

//	private String createHttpAuthenticationHeaderValue(String userId,
//			String password) {
//
//		String auth = userId + ":" + password;
//
//		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset
//				.forName("US-ASCII")));
//
//		String headerValue = "Basic " + new String(encodedAuth);
//
//		return headerValue;
//	}

}
