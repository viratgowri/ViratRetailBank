package com.product.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransferRequest {
	@NotBlank
    private String sourceAccount;
	@NotBlank
    private String targetAccount;
	@NotBlank
    private String amount;
}