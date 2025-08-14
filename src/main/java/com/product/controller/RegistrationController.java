package com.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.product.request.RegistrationConfirmRequest;
import com.product.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
@Log4j2
@Controller
@RequestMapping("/public")
public class RegistrationController {
	
	@Autowired
	RegistrationService registrationService;

	@PostMapping(path = "/register" , produces = "application/json")
	@Operation
    public ResponseEntity registration(@RequestBody RegistrationConfirmRequest req, BindingResult bindingResult)  {
		return registrationService.registration(req, bindingResult);

	}

}


