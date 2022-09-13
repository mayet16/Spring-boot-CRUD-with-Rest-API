
package com.hibret.StudentMgt.service;

import java.util.List;

import com.hibret.StudentMgt.model.Student;

public interface StudentService {
	
	List<Student> getAllStudents();
	
	Student saveStudent(Student student);
	
	Student getStudentById(Long id);
	
	Student updateStudent(Student student);
	
	void deleteStudentById(Long id);
}
