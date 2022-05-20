package com.accenture.sec.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Employee")
public class EmployeeEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EmployeeId")
private Integer empId;
	@Column(name="Name")
private String empName;
	@Column(name="City")
private String city;
	@Column(name="Gender")
private String gender;
	@Column(name="Salary")
private Double salary; 
	@Column(name="DepartmentId")
private Integer departmentId; 	

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
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
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

}
