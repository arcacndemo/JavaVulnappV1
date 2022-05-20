package com.accenture.sec.dao;

import java.util.List;
import java.util.Map;

import com.accenture.sec.business.bean.EmployeeBean;
import com.accenture.sec.business.bean.EmployeeSearchBean;

public interface EmployeeDAO {
	EmployeeBean addEmployee(EmployeeBean bean);

	Map<Integer, String> getDepartmentMap() throws Exception;

	List<EmployeeBean> getEmployeeList(EmployeeSearchBean employeeSearch);
}
