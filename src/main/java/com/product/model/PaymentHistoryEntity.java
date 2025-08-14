package com.product.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name ="Payment_History")
public class PaymentHistoryEntity {
	
	 @Id
	    private int payment_id;
	    private int account_id;
	    private String beneficiary;
	    private String beneficiary_acc_no;
	    private double amount;
	    private String reference_no;
	    private String status;
	    private String reason_code;
	    private LocalDateTime created_at;
}
