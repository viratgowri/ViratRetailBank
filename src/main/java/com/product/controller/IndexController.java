package com.product.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.repo.UserRepository;

@RestController
	public class IndexController {

	    @Autowired
	    private UserRepository userRepository;

	    @GetMapping("/")
	    public String getIndex(){
	        return null ;
	    }

	    @GetMapping("/verify")
	    public ResponseEntity<?> getVerify(@RequestParam("token")String token, @RequestParam("code")String code){

	        String dbToken = userRepository.checkToken(token);

	        if(dbToken == null){
	            return ResponseEntity.badRequest().body("This session has expire.");
	        }

	        userRepository.verifyAccount(token,code);
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Verification success." );
	        System.out.println("In Verify Account Controller");
	        return ResponseEntity.ok(response);


	    }
	

}
