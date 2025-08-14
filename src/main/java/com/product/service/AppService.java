package com.product.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.model.AccountEntity;
import com.product.model.PaymentHistoryEntity;
import com.product.model.TransHistoryEntity;
import com.product.model.UserEntity;
import com.product.repo.AccountRepository;
import com.product.repo.PaymentHistoryRepository;
import com.product.repo.TransactHistoryRepository;
import com.product.repo.UserRepository;
import com.product.request.AccountRequest;
@Service
public class AppService {
	
	  @Autowired
	    private static UserRepository userRepo;
	  
	  @Autowired
	  private static  AccountRepository accRepo;
	  
	  @Autowired
	static
	  PaymentHistoryRepository payHisRepo;

	  @Autowired
	static
	  TransactHistoryRepository tranHisRepo;
	  
	public static  ResponseEntity<?> dashboard(@Valid AccountRequest accountRequest, HttpSession session) {

		UserEntity user  = (UserEntity) session.getAttribute("user");
		 long userId = user.getUserId();

		    List<AccountEntity> getUserAccounts = accRepo.getUserAccountsById(userId);
		    
	        BigDecimal totalAccountsBalance = accRepo.getTotalBalance(userId);

	        Map<String, Object> response = new HashMap<>();
	        response.put("userAccounts", getUserAccounts); 
	        response.put("totalBalance", totalAccountsBalance); 
	        return ResponseEntity.ok(response);

	    }

	public static  ResponseEntity<?> payment_history(HttpSession session) {
		// TODO Auto-generated method stub
		UserEntity user  = (UserEntity) session.getAttribute("user");
		 long userId = user.getUserId();

	        List<PaymentHistoryEntity> userPaymentHistory = payHisRepo.getPaymentsRecordsById(userId);
	        
	        Map<String, List> response = new HashMap<>();
	        response.put("payment_history", userPaymentHistory); 

	        return ResponseEntity.ok(response);

    }

	public static  ResponseEntity<?> transactiontHistory(HttpSession session) {
		// TODO Auto-generated method stub
		UserEntity user  = (UserEntity) session.getAttribute("user");
		 long userId = user.getUserId();
		 
	        List<TransHistoryEntity> userTransactionHistory = tranHisRepo.getTransactionRecordsById(userId);

	        Map<String, List> response = new HashMap<>();
	        response.put("transaction_history", userTransactionHistory); 


	        return ResponseEntity.ok(response);

	    }

		
	}
	
	


