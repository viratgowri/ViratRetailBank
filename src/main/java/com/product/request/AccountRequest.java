package com.product.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountRequest {

	  @NotBlank
	    private String accountName;

	    @NotBlank
	    private String accountType;

		 private long mobileNumber;

}
