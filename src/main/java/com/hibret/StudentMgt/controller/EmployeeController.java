package com.hibret.StudentMgt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
@Controller
//@RestController
@RequestMapping
public class EmployeeController {
	@RequestMapping("/employees")
	public String viewEmployees() {
	   return "employees";
	}
	@RequestMapping("/add-employee")
	public String addEmployee() {
	   return "add-employee";
	}

	
	@RequestMapping("/employee/detail/{id}")
	public String employeeDetail(@PathVariable Long id) {
		return "employee_detail";
	}
	
	@RequestMapping("/employee/edit/{id}")
	public String editEmployeeForm(@PathVariable Long id) {
		return "edit_employee";
	}
	
	//used to test the api using rest template
	@RequestMapping("/employee")
	public String viewEmployeeList() {
		String url="http://localhost:8080/api/employees";
		RestTemplate restTemplate=new RestTemplate();
		String result=restTemplate.getForObject(url, String.class);
	   return result;
	}
	
}
