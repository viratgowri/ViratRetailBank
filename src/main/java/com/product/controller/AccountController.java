package com.product.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.product.request.AccountRequest;
import com.product.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;

public class AccountController {
	
	  @Autowired
	    private AccountService accountService;
	
    @PostMapping(path = "/create_account", produces = "application/json")
    @Operation
    public ResponseEntity<?> createAccount(HttpSession session,
                                           @Valid @RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest, session);
    }
}


