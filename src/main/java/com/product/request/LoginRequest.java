package com.product.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {


		private String password;
		
		 private long mobileNumber;
		 

		    private String userName;
		    
		    @NotBlank
		    private String Email;
		 
	
}


