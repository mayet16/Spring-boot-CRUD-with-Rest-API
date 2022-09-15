package com.hibret.StudentMgt;

import java.util.NoSuchElementException;

public class StudentNotFoundException extends NoSuchElementException {
	public StudentNotFoundException(String message) {
		super(message);
	}
}