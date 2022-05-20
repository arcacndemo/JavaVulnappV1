package com.accenture.sec.service;

import java.util.List;
import java.util.Map;

import com.accenture.sec.business.bean.EmployeeBean;
import com.accenture.sec.business.bean.EmployeeSearchBean;

public interface EmployeeService {
EmployeeBean addEmployee(EmployeeBean bean);

Map<Integer, String> getDepartmentMap() throws Exception;

List<EmployeeBean> employeeList(EmployeeSearchBean employeeSearch);
}
