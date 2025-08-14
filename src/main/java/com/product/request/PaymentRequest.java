package com.product.request;

import lombok.Data;

@Data
public class PaymentRequest {
	  private String beneficiary;
	    private String account_number;
	    private String account_id;
	    private String reference;

	    private String payment_amount;
}
