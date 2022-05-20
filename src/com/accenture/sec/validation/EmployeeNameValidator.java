package com.accenture.sec.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmployeeNameValidator implements ConstraintValidator<EmployeeNameValidatorVal, String>{

	@Override
	public void initialize(EmployeeNameValidatorVal arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String empName, ConstraintValidatorContext arg1) {
		if(empName.contains("a"))
			return false;
		else
			return true;
	}

}
