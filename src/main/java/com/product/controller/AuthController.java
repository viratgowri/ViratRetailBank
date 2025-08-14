package com.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.product.helper.JwtService;
import com.product.repo.UserRepository;
import com.product.request.LoginRequest;
import com.product.request.RegistrationConfirmRequest;
import com.product.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Log4j2
@Controller
@RequestMapping("/auth")
public class AuthController {


    private UserRepository userRepository;

	@Autowired
	AuthService authService;

    public JwtService jwtService;

    @Autowired
    public AuthController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    
	
	@PostMapping(path = "/login", produces = "application/json")
	@Operation
	public ResponseEntity login(HttpServletResponse servletResponse,
	                            HttpSession session,
	                            @Valid @RequestBody LoginRequest loginReq) {
	    log.debug("*** Login Method Invoked ***");
	    return authService.login(servletResponse, session, loginReq); 

	}

	


    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){

        System.out.println("Hi Virat, you successfully handled the token part in logout." + session.getAttribute("token"));
        session.invalidate();

        return ResponseEntity.ok("Logged out successfully.");

    }
}