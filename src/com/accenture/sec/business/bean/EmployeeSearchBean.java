package com.accenture.sec.business.bean;

import java.util.List;

public class EmployeeSearchBean {

private String empName;

private List<EmployeeBean> employeeList;

	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public List<EmployeeBean> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<EmployeeBean> employeeList) {
		this.employeeList = employeeList;
	}



}
