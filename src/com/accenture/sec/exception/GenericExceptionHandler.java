package com.accenture.sec.exception;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GenericExceptionHandler {
	@ExceptionHandler(value = MultipartException.class)
	public ModelAndView handleFileUploadException(MultipartException mpex, HttpServletRequest request) {
 
		ModelAndView modelAndVew = new ModelAndView("failure");
		modelAndVew.addObject("msg", mpex.getMessage());
		return modelAndVew;
	}
 
	@ExceptionHandler(value = {Exception.class,IOException.class})
	public ModelAndView handleException(Exception ex, HttpServletRequest request) {
 
		ModelAndView modelAndVew = new ModelAndView("failure");
		modelAndVew.addObject("msg", ex.getMessage());
		return modelAndVew;
	}
	
	@ExceptionHandler(value = SQLException.class)
	public ModelAndView handleSQLException(Exception ex, HttpServletRequest request) {
 
		ModelAndView modelAndVew = new ModelAndView("failure");
		modelAndVew.addObject("msg", ex.getMessage());
		return modelAndVew;
	}
}
