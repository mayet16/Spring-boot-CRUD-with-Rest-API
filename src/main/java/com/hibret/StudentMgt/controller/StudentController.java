
package com.hibret.StudentMgt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hibret.StudentMgt.model.Student;
import com.hibret.StudentMgt.service.StudentService;


@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	
	// handler method to handle list students and return mode and view
	@GetMapping("/students")
	public String listStudents(Model model) {
		model.addAttribute("students", studentService.getAllStudents());
		return "students";
	}

	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		
		// create student object to hold student form data
		Student student = new Student();
		model.addAttribute("student", student);
		return "create_student";
		
	}
	
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveStudent(student);
		return "redirect:/students";
	}
	
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "edit_student";
	}

	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id,
			@ModelAttribute("student") Student student,
			Model model) {
		
		// get student from database by id
		Student existingStudent = studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setEmail(student.getEmail());
		
		// save updated student object
		studentService.updateStudent(existingStudent);
		return "redirect:/students";		
	}
	
	//get single student details
	@GetMapping("/students/{id}")
	public String studentDetails(@PathVariable Long id,
			 Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "student-details";
	}
	// handler method to handle delete student request
	
	@GetMapping("delete/students/{id}")
	public String deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}	
	
	
	//testing the CRUD web application using postman tool
	//for api test
	@GetMapping("api/students")
	public List<Student> listStudentsApi() {
		return studentService.getAllStudents();
	}
	
	//for api testing
	@GetMapping("api/students/{id}") 
	public ResponseEntity<Student> getStudentById(@PathVariable("id") long studentId)
	{
		return new ResponseEntity<>(studentService.getStudentById(studentId),HttpStatus.OK);
				//.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); 
	}
	
	//for api test
	@PostMapping("api/students")
	public ResponseEntity<Student> saveStudentApi(@RequestBody Student student) {
		studentService.saveStudent(student);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}
	
	//for api testing
	@PutMapping("api/students/{id}")
	public ResponseEntity<Student> updateStudentApi(@PathVariable Long id, @RequestBody Student student) {
		
		// get student from database by id
		Student existingStudent = studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setEmail(student.getEmail());
		studentService.updateStudent(existingStudent);
		 return new ResponseEntity<>(existingStudent, HttpStatus.OK);		
	}
	
	//for api testing
	@DeleteMapping("/api/students/{id}")
	public ResponseEntity<String> deleteStud(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return new ResponseEntity<String>("Student deleted successfully!.",
				HttpStatus.OK);
	}
}
