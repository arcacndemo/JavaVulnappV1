package com.accenture.sec.controller;

import io.github.pixee.security.SystemCommand;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.accenture.sec.business.bean.EmployeeBean;
import com.accenture.sec.business.bean.EmployeeDtlsBean;
import com.accenture.sec.business.bean.EmployeeSearchBean;
import com.accenture.sec.dao.LoginDAO;
import com.accenture.sec.service.EmployeeService;

@Controller
public class EmployeeController {
	
	private static final int BUFFER_SIZE = 4096;
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;
	
	//Vul 16 -- Empty catch block
	@ModelAttribute("departmentList")
	   public Map<Integer, String> getDepartmentMap() throws Exception {
		Map<Integer, String> departmentMap = new HashMap<Integer, String>();
		try {
	      departmentMap = employeeService.getDepartmentMap();
		} catch(ClassCastException e) {
			
		}	catch (Exception e) {
			logger.error("Error in fetching department values", e);
	    	ModelAndView mv = new ModelAndView();
			mv.setViewName("failure");
	        mv.addObject("msg", "Error in fetching department values");
		}
	      
	      return departmentMap;
	   }
	
	/*@RequestMapping(value="listEmployee.html", method=RequestMethod.POST)
	public ModelAndView listEmployee()
	{
		List<EmployeeBean> employeeList = employeeService.employeeList(null);
		
		return new ModelAndView("listEmployee","employeeList",employeeList);
	}*/
	
	@RequestMapping(value="searchEmployee.html", method={ RequestMethod.GET,RequestMethod.POST})
	public ModelAndView searchEmployee(@ModelAttribute("employeeSearchBean") EmployeeSearchBean employeeSearchBean)
	{	
		logger.debug("Inside employee list");
		logger.info("Inside employee list");
		List<EmployeeBean> employeeList = employeeService.employeeList(employeeSearchBean);
		employeeSearchBean.setEmployeeList(employeeList);
		
 		return new ModelAndView("searchListEmployee","employeeSearchBean",employeeSearchBean);
	}

	@RequestMapping(value="loadAddEmployee.html", method=RequestMethod.GET)
	public ModelAndView loadAddEmployeePage()
	{
		return new ModelAndView("addEmployee","employeeBean",new EmployeeBean());
	}
	
	@RequestMapping(value="loadIndex.html", method=RequestMethod.GET)
	public ModelAndView loadIndexPage(HttpServletRequest request, HttpSession sess)
	{	
		/*LoginBean userDtl = (LoginBean) sess.getAttribute("loginBean");
		if(userDtl!=null && !userDtl.getRoleID().isEmpty() && (userDtl.getRoleID()).equals("role000001")) {
			sess.setAttribute("isAdmin", true);
		}*/
		return new ModelAndView("success","employeeBean",new EmployeeBean());
	}
	
	//@Transactional(value="txManager")
	@RequestMapping(value="storeEmployee.html", method=RequestMethod.POST)
	public ModelAndView storeEmployee(@ModelAttribute("employeeBean") @Valid EmployeeBean employeeBean,
			BindingResult result)
	{
		ModelAndView mv=new ModelAndView();
		if(result.hasErrors())
		{
			mv.setViewName("addEmployee");
		}
		else
		{
			employeeBean = employeeService.addEmployee(employeeBean);
			EmployeeBean emp = new EmployeeBean();
			emp.setEmpName(employeeBean.getEmpName());
			
			mv.setViewName("addEmployee");
			mv.addObject("employeeBean",emp);
		}
		return mv;
	}
	
	@RequestMapping(value="uplaodFile.html", method=RequestMethod.GET)
	public ModelAndView uploadFile()
	{
		return new ModelAndView("uploadFile","employeeBean",new EmployeeBean());
	}
	
	//vul 209
	@ExceptionHandler(MultipartException.class)
	@RequestMapping(value="fileupload.html", method=RequestMethod.POST)
	public ModelAndView processUpload(@RequestParam("file") MultipartFile file) throws IOException,MultipartException {
	        // process your file
		System.out.println("ModelAndView --- upload");

		ModelAndView mv= new ModelAndView();

		if (!file.isEmpty()) {
			String name = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			System.out.println("extension"+extension);
			String rootPath = System.getProperty("catalina.home");

			if (extension.equals("txt")) {
				try {
					byte[] bytes = file.getBytes();

					// Creating the directory to store file
					File dir = new File(rootPath + File.separator + "tmpFiles");
					if (!dir.exists())
						dir.mkdirs();
					
					System.out.println("path--------------"+dir.getAbsolutePath()+ File.separator + name);
					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath()+ File.separator + name);
										
					serverFile.setWritable(true); // This restricts user from overriding the file with same filename when set to false
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();		
					
					
					mv.setViewName("uploadFile");
					mv.addObject("msg","You successfully uploaded file=" + name);
					

					return mv;
				} catch (Exception e) {
					mv.setViewName("uploadFile");
					mv.addObject("msg", "You failed to upload " + name + " => ");
					return mv;
				}
			} else if ("zip".equals(extension)) {
				String destDirectory = rootPath + File.separator + "tmpFiles";
				File destDir = new File(destDirectory);
		        if (!destDir.exists()) {
		            destDir.mkdir();
		        }
		        ZipInputStream zipIn = new ZipInputStream(file.getInputStream());
		        ZipEntry entry = zipIn.getNextEntry();
		        // iterates over entries in the zip file
		        while (entry != null && !entry.isDirectory()) {
		            String filePath = destDirectory + File.separator + entry.getName();
		            //fix for zip entry overwrite to be uncommented in non vulnerable app -- Vul 209
		            //checkZipEntryOverwrite(destDir,entry);
		            
		            if (!entry.getName().contains("/")) {
		                // if the entry is a file, extracts it
		                extractFile(zipIn, filePath);
		            } else {
		                // if the entry is a directory, make the directory
		            	int index= filePath.lastIndexOf("/");
		            	String dirPath = filePath.substring(0, index);
		                File dir = new File(dirPath);
		                if(!dir.exists()) {
		                	if(dir.mkdir()) {
		                		System.out.println("Directory created");
		                	}
		                }
		                extractFile(zipIn, filePath);
		            }
		            zipIn.closeEntry();
		            entry = zipIn.getNextEntry();
		        }
		        zipIn.close();
		        
		        mv.setViewName("uploadFile");
				mv.addObject("msg","You successfully uploaded file=" + name);
		    } else {
				mv.setViewName("uploadFile");
				mv.addObject("msg", "Only text files can be uploaded");
				return mv;
			}
			
		} else {
			mv.setViewName("uploadFile");
			mv.addObject("msg", "You failed to upload because the file was empty.");
			return mv;
		}
		return mv;
	}
	
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
	
	private void checkZipEntryOverwrite(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        
    }
	
	@RequestMapping(value="download.html", method=RequestMethod.GET)
	public ModelAndView downloadFile( @RequestParam(value="file")String name,HttpSession session,HttpServletResponse response) throws Exception {
    	ModelAndView mv = new ModelAndView();

		try {
	        String filePath = System.getProperty("catalina.home") + "\\tmpFiles\\";
	        //String fileName = filePath + "file.txt";
	        String fileName = filePath + name;
	        File fileToDownload = new File(fileName);
	        InputStream inputStream = new FileInputStream(fileToDownload);
	        response.setContentType("application/force-download");
	        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".txt");
	        IOUtils.copy(inputStream, response.getOutputStream());
	        response.flushBuffer();
	        inputStream.close();
	        
	        mv.addObject("msg", "File downloaded");
	        mv.setViewName("uploadFile");

	    } catch (Exception e){	 
	    	logger.error("error in download", e);
	    	logger.error("Error in executing command", e);
			mv.setViewName("failure");
	        mv.addObject("msg", "Error in executing command, check log for more information");
	        e.printStackTrace();
	    }
		return mv;
	}
	
	//Vul 99
	@RequestMapping(value="downloadPropFile.html", method=RequestMethod.POST)
	public ModelAndView downloadPropertyFile( HttpServletResponse response) throws Exception {
    	ModelAndView mv = new ModelAndView();

		try (InputStream input = LoginDAO.class.getResourceAsStream("/com/accenture/sec/resources/sec_conn.properties")) {

			Properties prop = new Properties();		
			prop.load(input);
		
			String dbpwd = prop.getProperty("sec_password");
			String filePath = System.getProperty("catalina.home") + "\\tmpFiles\\";
			String fileName = filePath + "sec_conn_encrypted.properties";
			/*
			 * byte[] inputArray = new byte[input.available()]; input.read(inputArray);
			 */
			String encryptedText = encryptWithRSA(dbpwd);
			logger.debug("Encrypted password---",encryptedText);
			prop.setProperty("sec_password_encrypted", encryptedText);
			
			File file = new File(fileName);
			FileOutputStream fileOut = new FileOutputStream(file);
			prop.store(fileOut, "writing encrypted password");							        
	        
	        mv.addObject("msg", "Encrypted file downloaded");
	        mv.setViewName("uploadFile");
	    } catch (Exception e){	 
	    	logger.error("error in downloading property file", e);
			mv.setViewName("failure");
	        mv.addObject("msg", "Error in downloading property file, check log for more information");
	        e.printStackTrace();
	    }
		return mv;
	}
	
	/**
	 * "RSA/NONE/OAEPWithSHA1AndMGF1Padding" - RSA with OAEP padding to be used for it to be secure
	 * @param pwd
	 * @return
	 */
	private String encryptWithRSA(String pwd) {
		byte[] cipherText =null;
		String encryptedText = null;
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	    keyPairGenerator.initialize(4096);
	     // Generate the KeyPair
	    KeyPair keyPair = keyPairGenerator.generateKeyPair();
	        
	        // Get the public and private key
	    PublicKey publicKey = keyPair.getPublic();
	    PrivateKey privateKey = keyPair.getPrivate();
	    
        Cipher cipher = Cipher.getInstance("RSA");
        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        logger.debug("Encrypting pwd key",privateKey);
        //Perform Encryption
        cipherText = cipher.doFinal(pwd.getBytes()) ;
        encryptedText = Base64.getEncoder().encodeToString(cipherText);// Java 8, uses new Base64 class
        
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedByte = Base64.getDecoder().decode(cipher.doFinal(encryptedText.getBytes()));
        String decryptedTxt = Base64.getEncoder().encodeToString(decodedByte);
        
        logger.debug("decryted",decryptedTxt);
        logger.debug("PASSWORD",encryptedText);
        
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} 
        	
		return encryptedText;
	}

	
	@RequestMapping(value = "uploademployee.html", method = RequestMethod.POST)
	public ModelAndView validateEmployee(@RequestParam("file") MultipartFile file) throws IOException, ParserConfigurationException, SAXException { // process your
																										// file
		System.out.println("ModelAndView --- upload");

		ModelAndView mv = new ModelAndView();
		JAXBContext jaxbContext;
		try {
			String path = System.getProperty("user.dir") + "\\EmployeeInfo.xsd";
	        
	        System.out.println("Working Directory = " + path);
			/*
			 * SchemaFactory factory =
			 * SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); Schema schema
			 * = factory.newSchema(new File(path)); Validator validator =
			 * schema.newValidator(); validator.validate(new
			 * StreamSource(file.getInputStream()));
			 */
	        
			
	        SAXParserFactory spf = SAXParserFactory.newInstance();
			/*
			 * spf.setFeature("http://xml.org/sax/features/external-general-entities",
			 * false);
			 * spf.setFeature("http://xml.org/sax/features/external-parameter-entities",
			 * false); spf.setFeature(
			 * "http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			 */

	        //Do unmarshall operation
	        Source xmlSource = new SAXSource(spf.newSAXParser().getXMLReader(),
	                                        new InputSource(file.getInputStream()));

			jaxbContext = JAXBContext.newInstance(EmployeeDtlsBean.class);              			 
		    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		 
		    EmployeeDtlsBean employees = (EmployeeDtlsBean) jaxbUnmarshaller.unmarshal(xmlSource);		    
		    List<EmployeeBean> employeeList = employees.getEmployeedtl();
		    
			/*
			 * for (EmployeeBean employeeBean:employeeList) {
			 * employeeService.addEmployee(employeeBean); }
			 */
		    
		    employeeList.forEach(employeeBean -> {employeeService.addEmployee(employeeBean);});
			
			mv.setViewName("uploadFile");
			mv.addObject("msg","XML is valid and employee details uploaded successfully");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) 
		{
		    e.printStackTrace();
		}

		return mv;
	}
	
	@RequestMapping(value="loadExecCmd.html", method=RequestMethod.GET)
	public ModelAndView loadCommandExecution()
	{
		return new ModelAndView("commandExec","employeeBean",null);
	}
	
	//Vul 003
	@RequestMapping(value="execCmd.html", method={RequestMethod.POST})
	public ModelAndView executeCommand( @RequestParam(value="cmd")String cmd) {
		logger.debug("Command Execution started");
		ModelAndView mv = new ModelAndView();
		List<String> dirList = new ArrayList<String>();
		boolean isCmdValid = Pattern.matches("^[a-zA-Z0-9. /:]{2,25}$", cmd);
		List<String> whiteListCmd = Arrays.asList("cd","dir","type"); 
		String[] inputCmd = cmd.split(" ");
		
		//isCmdValid = isCmdValid && whiteListCmd.stream().anyMatch(wlcmd -> wlcmd.matches(inputCmd[0]));
		isCmdValid = true;
		
		if(isCmdValid) {
		String commandArray[] = {"cmd", "/c", cmd, "C:\\software\\apache-tomcat-8.5.41\\tmpFiles"};
		try {
			Process process = SystemCommand.runCommand(Runtime.getRuntime(), commandArray);
			process.waitFor();
			BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String line;
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            System.out.println(line);
	            dirList.add(line);
	        }
	        r.close();
	        mv.setViewName("commandExec");
	        mv.addObject("dirlist", dirList);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Error in executing command", e);
			mv.setViewName("failure");
	        mv.addObject("msg", "Error in executing command, check log for more information");
		}
		} else {
			mv.setViewName("commandExec");
	        mv.addObject("msg", "Command exeuction not carried out, since it has invalid characters");
		}
		return mv;
	}
	
	//vul- 042 - path manipulation
	@RequestMapping(value="deleteFile.html", method={RequestMethod.POST})
	public ModelAndView deleteFile( @RequestParam(value="fileName")String fileName) {
		
		ModelAndView mv = new ModelAndView();
		//String filepath = "C:\\software\\apache-tomcat-8.5.41\\tmpFiles\\";
		String filepath= System.getProperty("catalina.home")+File.separator+"tmpFiles"+File.separator;
		
		Path path = Paths.get(filepath, fileName);
		File file = path.toFile();
		
		try {
			//boolean isfileNameValid = file.getCanonicalFile().getName().equals(fileName);
			boolean isfileNameValid = true;

			if (isfileNameValid) {
				file.delete();
				System.out.println("File deleted successfully");
				mv.setViewName("commandExec");
				mv.addObject("msg", "File deleted successfully");
			} else {
				System.out.println("Failed to delete the file");
				mv.setViewName("commandExec");
				mv.addObject("msg", "Failed to delete the file");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}	
	 

}
