package com.product.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.product.helper.GenAccountNumber;
import com.product.model.AccountEntity;
import com.product.model.UserEntity;
import com.product.repo.AccountRepository;
import com.product.repo.UserRepository;
import com.product.request.AccountRequest;

public class AccountService {
	
	  @Autowired
	    private UserRepository userRepo;
	  
	  @Autowired
	  AccountRepository accRepo;
	
	@PostMapping("/create_account")
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequest accountRequest, HttpSession session) {
		
		
		UserEntity user  = (UserEntity) session.getAttribute("user");
		
		long mobileNum = user.getMobileNum();
	   user = userRepo.findByMobileNum(mobileNum);
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
	    }
	    
	    String bankAccountNumber = String.valueOf(GenAccountNumber.generateAccountNumber());
	    long userId = user.getUserId();
	    
	    AccountEntity newAccount = new AccountEntity();
	    newAccount.setUserId(userId);
	    newAccount.setAccountNumber(bankAccountNumber);
	    newAccount.setAccountName(accountRequest.getAccountName());
	    newAccount.setAccountType(accountRequest.getAccountType());
	    newAccount.setBalance(BigDecimal.ZERO);
	    newAccount.setCreatedAt(LocalDateTime.now());

	    // Save account
	    accRepo.save(newAccount);

		
        return ResponseEntity.ok(accRepo.findByUserId(userId));


}
}