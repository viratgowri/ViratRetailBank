package com.product.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Transaction")
public class TransactEntity {
    @Id
    private int transaction_id;
    private int account_id;
    private String transaction_type;
    private double amount;
    private String source;
    private String status;
    private String reason_code;
    
}