package com.usoft.service;

import java.math.BigDecimal;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.mail.smtp.SMTPTransport;
import com.usoft.model.State;
import com.usoft.model.Usuario;
import com.usoft.repo.UserRepositoryInt;

@Service
public class UserService implements UserServiceInt{
	
	@Autowired
	private UserRepositoryInt userRepo;
	
	@Transactional
	@Override
	public boolean createUser(Usuario user) {
		try {
			if(user.getArea()!=null && user.getBirthday()!=null &&
					user.getEmail()!=null && !user.getEmail().isEmpty() &&
					user.getFirstName()!=null && !user.getFirstName().isEmpty() &&
					user.getLastName()!=null && !user.getLastName().isEmpty() &&
					user.getPhone()!=null && !user.getPhone().isEmpty() &&
					user.getIsdeleted().equals(BigDecimal.ZERO) && 
					user.getEmail().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]+)$") &&
					user.getPhone().matches("\\d+")) {
				userRepo.save(user);
			}else {
				throw new Exception("Some field of user is either null, empty, or invalid");
			}
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * This method is a service which purpose is to find all registered users
	 */
	@Transactional
	@Override
	public Iterable<Usuario> findAllUsers(){
		return userRepo.findAll();
	}
	
	/**
	 * This method is a service which purpose is to find an specified (by its id) user
	 */
	@Transactional
	@Override
	public Usuario findUser(Integer id)throws Exception{
		try {
			Usuario user = userRepo.find(id);
			if(user==null) {
				throw new Exception("No user registred with id: " + id);
			}else {
				return user;
			}
		} catch (Exception e) {
			throw new Exception("No user registred with id: " + id);
		}
		
		
	}
	/**
	 * This method change the state of the user, to Deleted, this is a logic remove
	 */
	@Transactional
	@Override
	public void deleteUser(Integer id) throws Exception{
		Usuario user= this.findUser(id);
		if(user.getIsdeleted().equals(BigDecimal.ONE)) {
			throw new Exception("The user is already in state deleted");
		}
		user.setIsdeleted(BigDecimal.ONE);
		userRepo.save(user);
	}
	/**
	 * This method introduce the javax mail to improve a method that verificate emails, and sent a invitation to a aplication, need from parameter, to emails, and the credentials of the user list
	 */
	@Transactional
	@Override
	public void sendEmail(String from,String to,String user, String password) throws Exception {
		if(from.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
				&& to.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
			Properties properties = System.getProperties();  
			properties.put("mail.smtp.host", "smtp.gmail.com"); //optional, defined in SMTPTransport
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.class",
		            "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.starttls.enable", "true"); //TLS/
		    Session session = Session.getDefaultInstance(properties,
		            new javax.mail.Authenticator() {
		                                @Override
		                protected PasswordAuthentication getPasswordAuthentication() {
		                    return new PasswordAuthentication(user,password);
		                }
		            });
		      try{  
		          MimeMessage message = new MimeMessage(session);  
		          message.setFrom(new InternetAddress(from));  
		          message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		          message.setSubject("Create a user in my application");  
		          message.setText("Hello, this is the url to create a user in my application, your username is your email and the password is 'password' " +  "http://pi2sis.icesi.edu.co/usoft/#/login");
		          Transport.send(message);  
		          System.out.println("Email sent successfully....");  	   
		       }catch (MessagingException mex) {
		    	   mex.printStackTrace();
		    	   throw new Exception("Error to send email");
		       } 
		}
	}
	
	@Transactional
	@Override
	public boolean updateUser(Usuario user) {
		
		
		try {
			if(user.getArea()!=null && user.getBirthday()!=null &&
					user.getEmail()!=null && !user.getEmail().isEmpty() &&
					user.getFirstName()!=null && !user.getFirstName().isEmpty() &&
					user.getLastName()!=null && !user.getLastName().isEmpty() &&
					user.getPhone()!=null && !user.getPhone().isEmpty() &&
					user.getEmail().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$") &&
					user.getPhone().matches("\\d+")) {
				
				userRepo.update(user);
				
			}else {
				throw new Exception("Some field of user is either null, empty, or invalid");
			}
			System.out.println("ok");
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}

	

}
