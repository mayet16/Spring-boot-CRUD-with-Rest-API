package com.hibret.StudentMgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hibret.StudentMgt.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
