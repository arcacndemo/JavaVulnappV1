package com.accenture.sec.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.sec.business.bean.EmployeeBean;
import com.accenture.sec.business.bean.EmployeeSearchBean;
import com.accenture.sec.dao.EmployeeDAO;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDAO employeeDAO;
	@Override
	public EmployeeBean addEmployee(EmployeeBean bean) {
		return employeeDAO.addEmployee(bean);
	}
	
	@Override
	public Map<Integer, String> getDepartmentMap() throws Exception {
		return employeeDAO.getDepartmentMap();
	}

	@Override
	public List<EmployeeBean> employeeList(EmployeeSearchBean employeeSearch) {
		return employeeDAO.getEmployeeList(employeeSearch);
	}

}
