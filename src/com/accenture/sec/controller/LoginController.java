package com.accenture.sec.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.accenture.sec.business.bean.LoginBean;
import com.accenture.sec.business.bean.UserAccountBean;
import com.accenture.sec.business.bean.UserAccountFormBean;
import com.accenture.sec.business.bean.UserProfileForm;
import com.accenture.sec.dao.LoginDAO;
import com.sun.mail.util.BASE64EncoderStream;


@Controller
@SessionAttributes("userObj")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private static Cipher ecipher;
	private static SecretKey key;
	
	@Autowired
	LoginDAO dao;
	
	@RequestMapping(value="loginEmployee.html", method=RequestMethod.GET)
	public ModelAndView loadAddEmployeePage()
	{
		return new ModelAndView("login","loginBean",new LoginBean());
	}
	
	@RequestMapping(value="listUser.html", method=RequestMethod.GET)
	public ModelAndView listUser() throws SQLException
	{
		List<UserAccountBean> listUsers = dao.listUser();
		UserAccountFormBean userAccounts = new UserAccountFormBean();
		userAccounts.setUserAccounts(listUsers);
		
		return new ModelAndView("listUser","listUsersBean",userAccounts);
	}
	
	@RequestMapping(value="validate.html", method=RequestMethod.POST)
	public ModelAndView validateLogin(@ModelAttribute LoginBean bean, HttpServletRequest request,HttpSession sess, HttpServletResponse response)
	{
		//fetching request data
		//wrapping it into model object
		//use dao class to validate the data
		//navigating to the appropriate response
		logger.info("Inside login validation info");
		logger.debug("Inside login validation debug");
		logger.trace("Inside login validation trace");
		String uname = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		
		bean=new LoginBean();
		bean.setUserName(uname);
		bean.setPassword(pwd);
				
		String res = dao.validateLogin(bean);
		
		ModelAndView mv= new ModelAndView();
		
		if("success".equals(res))
		{
			mv.setViewName("success");
			mv.addObject("msg","Welcome "+bean.getUserName());
			mv.addObject("userObj", bean);
			sess.setAttribute("loginBean", bean); // Made available across controllers
			
			  Cookie userRole = new Cookie("userRole",bean.getRoleID());
			  //userRole.setHttpOnly(true); // vul 0056, 0078, 0004
			  //userRole.setSecure(true);
			  userRole.setMaxAge(600); // Vul 111 -- persistent cookiee
			  response.addCookie(userRole);
			 
			
			System.out.println(sess.getAttribute("userObj"));

			return new ModelAndView("success","loginBean",bean);
		}
		else
		{
			mv.setViewName("login");
			mv.addObject("msg","Invalid username or password");
		}
		return mv;
	}
	
	@RequestMapping(value="addUser.html", method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView addUser(@ModelAttribute("userAccountBean") UserAccountBean userAccountBean)
	{
		Optional<UserAccountBean> optUserAcc = Optional.ofNullable(userAccountBean);
		
		if(optUserAcc.isPresent() && optUserAcc.get().getUserName()!=null) {
			userAccountBean= optUserAcc.get();
			userAccountBean.setRoleID("role000002");
			//userAccountBean.setUserName(HtmlUtils.htmlEscape(userAccountBean.getUserName()));
			String escapedVal = StringEscapeUtils.escapeHtml4(userAccountBean.getUserName());
			escapedVal= StringEscapeUtils.escapeEcmaScript(escapedVal);
			System.out.println("escapedVal4 and ecma"+escapedVal);
			escapedVal= HtmlUtils.htmlEscape(escapedVal);
			System.out.println("htmlEscape"+HtmlUtils.htmlEscape(userAccountBean.getUserName()));
			System.out.println("escapehtml3"+StringEscapeUtils.escapeHtml3(userAccountBean.getUserName()));
			
			optUserAcc.get().setUserName(escapedVal);
			userAccountBean = dao.addUser(userAccountBean);
			
			UserAccountBean user = new UserAccountBean();
			user.setUserName(userAccountBean.getUserName());
			
			return new ModelAndView("addUser","userAccountBean",user);

		} else {
			return new ModelAndView("addUser","userAccountBean",new UserAccountBean());
		}
	}
	
	//vul 107
	@RequestMapping(value="addUserProfile.html", method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView addUserProfile(@ModelAttribute("userProfile") UserProfileForm userProfile)
	{
		if(userProfile.getUserName()!=null) {
			// generate secret key using DES/AES algorithm
			String encrypted = encryptDES(userProfile.getPwd());
	        userProfile.setEncryptionKey(encrypted);
	        userProfile = dao.addUserProfile(userProfile);							
			
            UserProfileForm user = new UserProfileForm();
			user.setUserName(userProfile.getUserName());
			
			return new ModelAndView("addUserProfile","userProfile",user);

		} else {
			return new ModelAndView("addUserProfile","userProfile",new UserProfileForm());
		}
	}
	
	private String encryptDES(String password) {
		
		String encrypted=null;
		try {
			key = KeyGenerator.getInstance("DES").generateKey();
		 
            ecipher = Cipher.getInstance("DES");
         // initialize the ciphers with the given key
            
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            // encode the string into a sequence of bytes using the named charset
            
            // storing the result into a new byte array. 
         
            byte[] utf8 = password.getBytes("UTF8");
            byte[] enc = ecipher.doFinal(utf8);
         
            // encode to base64	         
            enc = BASE64EncoderStream.encode(enc);
            encrypted = new String(enc);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
		return encrypted;
	}
	
	//vul 125 and 132
	private String encryptAES(String password) {
		
		String encrypted=null;
		try {
			key = KeyGenerator.getInstance("AES").generateKey();
		 
			//"AES/CBC/PKCS5PADDING" mode to be used to avoid insecure mode of operation
            ecipher = Cipher.getInstance("AES");
         // initialize the ciphers with the given key
            
            ecipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));
            
            // encode the string into a sequence of bytes using the named charset            
            // storing the result into a new byte array. 
         
            byte[] utf = password.getBytes("UTF-8");
            byte[] enc = ecipher.doFinal(utf);
            
            encrypted = new String(enc, "UTF-8");
         
            // encode to base64	         
            //encrypted = Base64.getEncoder().encodeToString(enc);
            
            ecipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));            
            //byte[] decrptByte=ecipher.doFinal(encrypted.getBytes("ISO-8859-1"));
            byte[] decrptByte=ecipher.doFinal(encrypted.getBytes("UTF-8"));
            
            String decrypted = new String(decrptByte);
            logger.debug("Text password----",password);
            logger.debug("Encrypted Text password----",encrypted);
            logger.debug("Decrypted Text password----",decrypted);
            
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		return encrypted;
	}		

}
