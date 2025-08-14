package com.product.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.product.request.AccountRequest;
import com.product.service.AppService;

import io.swagger.v3.oas.annotations.Operation;

@Controller
@RequestMapping("/app")
public class AppController {
	
	@GetMapping(path = "/dashboard", produces = "application/json")
    @Operation
    public ResponseEntity<?> dashboard(HttpSession session,
                                           @Valid @RequestBody AccountRequest accountRequest) {
        return AppService.dashboard(accountRequest, session);
    }
	
	@GetMapping(path = "/payment_history", produces = "application/json")
    @Operation
    public ResponseEntity<?> payment_history(HttpSession session) {
        return AppService.payment_history( session);
	
}
	
	@GetMapping(path = "/transaction_history", produces = "application/json")
    @Operation
    public ResponseEntity<?> TransactiontHistory(HttpSession session) {
        return AppService.transactiontHistory( session);
	
}

	   

}
