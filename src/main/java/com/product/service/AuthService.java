package com.product.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.product.helper.JwtService;
import com.product.helper.Token;
import com.product.model.UserEntity;
import com.product.repo.UserRepository;
import com.product.request.LoginRequest;
import com.product.request.RegistrationConfirmRequest;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<?> login(HttpServletResponse servletResponse, HttpSession session, LoginRequest loginReq) {
       // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    	String email = loginReq.getEmail();
        String password = loginReq.getPassword();
        long mobileNum = loginReq.getMobileNumber();
      //  String encodedPassword = encoder.encode(password);

     
        // Retrieve user
        UserEntity user = userRepo.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
        }

        // Check password
        String storedHashedPassword = user.getUserPwd();
        if (!BCrypt.checkpw(password, storedHashedPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password.");
        }

		/*
		 * // Check if account is verified if (!user.isVerified()) { return
		 * ResponseEntity.status(HttpStatus.FORBIDDEN).
		 * body("Account verification required."); }
		 */

        // Generate JWT
        String jwt = jwtService.generateToken(String.valueOf(user.getMobileNum()));
        System.out.println("JWT from login: " + jwt);
        System.out.println(jwtService.decodeToken(jwt));

        // Generate custom token (optional)
        String token = Token.generateToken();

        // Build response
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Authentication confirmed");
        responseBody.put("access_token", jwt);
        responseBody.put("custom_token", token);
        responseBody.put("user", user); // optional: return user details

        // Set session attributes
        session.setAttribute("user", user);
        session.setAttribute("token", jwt);
        session.setAttribute("authenticated", true);

        return ResponseEntity.ok(responseBody);
    }
    
}