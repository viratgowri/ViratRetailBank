package com.product.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.product.request.LoginRequest;
import com.product.request.PaymentRequest;
import com.product.request.TransferRequest;
import com.product.service.TransactService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/transact")
public class TransactController {
	
	@Autowired
	private TransactService transactService;

	
	@PostMapping(path = "/deposit", produces = "application/json")
	@Operation(summary = "Make a deposit")
	public ResponseEntity<?> deposit(@RequestBody Map<String, String> requestMap, HttpSession session) {
	    return transactService.deposit(requestMap, session);
	}


@PostMapping(path = "/transfer", produces = "application/json")
@Operation
public ResponseEntity transfer(@RequestBody TransferRequest request, HttpSession session) {

    return transactService.transfer(request, session); 

}

@PostMapping(path = "/withdraw", produces = "application/json")
@Operation
public ResponseEntity withdraw(@RequestBody Map<String, String> requestMap, HttpSession session) {

    return transactService.withdraw(requestMap, session); 

}
@PostMapping("/payment")

public ResponseEntity payment(@RequestBody PaymentRequest request, HttpSession session) {

    return transactService.payment(request, session); 


}




}