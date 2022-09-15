
package com.hibret.StudentMgt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hibret.StudentMgt.model.Student;
import com.hibret.StudentMgt.repository.StudentRepository;
import com.hibret.StudentMgt.service.StudentService;

import java.util.Arrays; 
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest 
public class StudentServiceTest {
	/**
	 * Autowire in the service we want to test
	 */

	@Autowired private StudentService service;

	/**
	 * Create a mock implementation of the StudentRepository
	 */
	@MockBean private StudentRepository repository;

	@Test
	@DisplayName("Test findById Success") 
	void testFindById() { // Setup our mockrepository 
		
		Student Student1 = Student.builder() .firstName("adonay")
				.lastName("eshetu") .email("ady@gmail.com") .build();
		doReturn(Optional.of(Student1)).when(repository).findById(1l);

		// Execute the service call 
		Optional<Student> returnedStudent = Optional.of(service.getStudentById(1l));

		
		// Assert the response 
		Assertions.assertTrue(returnedStudent.isPresent(),"Student  found");
		Assertions.assertSame(returnedStudent.get(), Student1,"The Student returned was the same as the mock"); }

	@Test
	//@Disabled("disabled until the error is fixed")
	@DisplayName("Test findById Not Found")
	void testFindByIdNotFound() throws NoSuchElementException  { 
		//Setup our mock repository
		//doReturn(Optional.empty()).when(repository).findById(1l);
		when(repository.findById(1l)).thenReturn(Optional.empty());
		// Execute the service call
		Optional<Student> returnedStudent =Optional.of(service.getStudentById(1l));

		// Assert the response 
		//Assertions.assertFalse(returnedStudent.isPresent(),"Student should not be found");
		//assertThrows(StudentNotFoundException.class, () -> service.getStudentById(1l),"Student should not be found");	
	}

	@Test
	@DisplayName("Test findAll")
	void testFindAll() { 
		// Setup our mockrepository 
		Student Student1 = Student.builder() .firstName("adonay")
				.lastName("eshetu") .email("ady@gmail.com") .build();
		
		Student Student2 = Student.builder() .firstName("haile") 
				.lastName("mekonen").email("haile@gmail.com") .build(); 
		
		doReturn(Arrays.asList(Student1,
						Student2)).when(repository).findAll();

				// Execute the service call 
		List<Student> Students = service.getAllStudents();

				// Assert the response 
		Assertions.assertEquals(2, Students.size(),"findAll should return 2 Students"); }

	@Test
	@Order(3)
	@Rollback(value = false)
	@DisplayName("Test save Student") 
	void testSave() { // Setup our mock repository 
		
		Student Student1 = Student.builder() .firstName("adonay")
				.lastName("eshetu") .email("ady@gmail.com") .build();
		doReturn(Student1).when(repository).save(any());

		// Execute the service call 
		Student returnedStudent = service.saveStudent(Student1);

		// Assert the response 
		Assertions.assertNotNull(returnedStudent,"The saved Student should not be null"); } 


@Test
@Disabled("disabled until the error is fixed")
@Order(4)
@Rollback(value = false)
public void updateStudentTest(){

    Student student = service.getStudentById(1L);

    student.setEmail("ady@gmail.com");

    Student studentUpdated =  service.saveStudent(student);

    Assertions.assertEquals("ady@gmail.com", studentUpdated.getEmail());

}

@Test
@Disabled("disabled until the error is fixed")
@Order(5)
@Rollback(value = false)
public void deleteStudentTest(){

    Student student = service.getStudentById(1L);

    service.deleteStudentById(student.getId());

    Student student1 = null;

    Optional<Student> optionalStudent = Optional.ofNullable(service.getStudentById(1L));

    if(optionalStudent.isPresent()){
    	student1 = optionalStudent.get();
    }

    Assertions.assertNull(student1);
}
}


