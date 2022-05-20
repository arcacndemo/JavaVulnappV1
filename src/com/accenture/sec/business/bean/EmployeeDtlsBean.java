package com.accenture.sec.business.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="EmployeeInfo")
public class EmployeeDtlsBean {
	private List<EmployeeBean> employeedtl;

	public List<EmployeeBean> getEmployeedtl() {
		return employeedtl;
	}

	public void setEmployeedtl(List<EmployeeBean> employeedtl) {
		this.employeedtl = employeedtl;
	}

}
