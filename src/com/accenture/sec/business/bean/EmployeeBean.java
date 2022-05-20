package com.accenture.sec.business.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="employeedtl")
public class EmployeeBean {
private Integer empId;

private String empName;
	
private Double salary; 

private Integer departmentId; 
	
private String gender;

private String city;

/*
 * public EmployeeBean (Integer empId,String empName, Double salary,Integer
 * departmentId,String gender,String city ) { this.empId = empId; this.empName =
 * empName; this.salary = salary; this.departmentId = departmentId; this.gender
 * = gender; this.city = city; }
 */
	
	
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

}
