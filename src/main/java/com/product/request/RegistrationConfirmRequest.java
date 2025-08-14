package com.product.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;



//import com.product.util.BaseReq;

import lombok.Data;

@Data	
public class RegistrationConfirmRequest  {
	@NotNull
    private String firstName;
	
	@NotNull
    private String lastName;

    @NotNull
    @Min(100000000)
    @Max(9999999999L)
    private long mobileNumber;
    
    private String usertype;

    @NotNull
    private String password;
    
    @NotNull
    private String confirmPassword;
    
    private String email;

	
}
