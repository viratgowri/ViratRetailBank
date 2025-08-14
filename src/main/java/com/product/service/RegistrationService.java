package com.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.swing.text.html.HTML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.product.helper.MailMessenger;
import com.product.helper.Messagetemplate;
import com.product.helper.Token;
import com.product.model.UserEntity;
import com.product.repo.UserRepository;
import com.product.request.RegistrationConfirmRequest;
@Service
public class RegistrationService {

	@Autowired
    private UserRepository userRepo;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@PostMapping("/register")
	public ResponseEntity<?> registration(@RequestBody RegistrationConfirmRequest req, BindingResult bindingResult) {

	    if (bindingResult.hasErrors()) {
	        List<String> errorMessages = new ArrayList<>();
	        for (FieldError error : bindingResult.getFieldErrors()) {
	            errorMessages.add(error.getDefaultMessage());
	        }
	        return ResponseEntity.badRequest().body(errorMessages);
	    }

	    // Check if user already exists
	    UserEntity existingUser = getUserDetailsByMobileNo(req);
	    if (existingUser != null) {
	        return ResponseEntity.badRequest().body("User already registered.");
	    }

	    // Check if passwords match
	    if (!req.getPassword().equals(req.getConfirmPassword())) {
	        return ResponseEntity.badRequest().body("Passwords do not match.");
	    }
	    // Generate token and verification code
	    String token = Token.generateToken();
	    int code = new Random().nextInt(123 * 123);

	    // Create email body
	    String emailBody = Messagetemplate.htmlEmailTemplate(token, Integer.toString(code));
	    
	    // Prepare user entity
	    UserEntity userEntity = new UserEntity();
	    userEntity.setMobileNum(req.getMobileNumber());
	    userEntity.setFirstName(req.getFirstName());
	    userEntity.setLastName(req.getLastName());
	    userEntity.setUserType(req.getUsertype());
	    userEntity.setEmail(req.getEmail());
	    userEntity.setToken(token);
	    userEntity.setCode(code);
	    // Encode and set password
	    String encodedPassword = passwordEncoder.encode(req.getPassword());
	    userEntity.setUserPwd(encodedPassword);

	    // Generate and set user ID
	    Integer lastUserId = userRepo.getLastUserId();
	    userEntity.setUserId((lastUserId == null) ? 1 : lastUserId + 1);

	    // Save user
	    userRepo.save(userEntity);

	 
		/*
		 * try { MailMessenger.htmlEmailMessenge("viratgowri007gs@gmail.com",
		 * req.getEmail(), "Verify Account", emailBody); } catch (MessagingException e)
		 * { throw new RuntimeException("Email sending failed", e); }
		 */

	    // Build response
	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "Registration successful. Please check your email and verify your account.");
	    response.put("user", userEntity);

	    return ResponseEntity.ok(response);
	}

 
	
	
	
	public UserEntity getUserDetailsByMobileNo(RegistrationConfirmRequest req) {
		return userRepo.findByMobileNum(req.getMobileNumber());
 }
}

//




   