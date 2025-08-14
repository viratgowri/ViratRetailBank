package com.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "Customer")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "First_Name")
    private String firstName;

    @Column(name = "Last_Name")
    private String lastName;
 
    
    
    @Column(name = "Mobile_no")
    private long mobileNum;

    @Column(name = "user_pwd")
    private String userPwd;
    
    @Column(name = "user_type")
    private String userType;
    
    @Column(name = "isVerified")
    private boolean isVerified;
    
    @Column(name = "Email_Id")
    private String email;
    
    @Column(name = "Token")
    private String token;
    
    @Column(name = "code")
    private int code;

    
    
    
    

    

}