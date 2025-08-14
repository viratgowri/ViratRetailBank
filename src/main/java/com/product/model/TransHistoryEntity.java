package com.product.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Id;


import lombok.Data;

@Data
@Entity
@Table(name = "transaction_history")
public class TransHistoryEntity {

    @Id
    private int transaction_id;
    private int account_id;
    private String transaction_type;
    private double amount;
    private String source;
    private String status;
    private String reason_code;
    private LocalDateTime created_at;
}
