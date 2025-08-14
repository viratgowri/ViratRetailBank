package com.product.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.product.model.UserEntity;
import com.product.repo.AccountRepository;
import com.product.repo.PaymentRepository;
import com.product.repo.TransactRepository;
import com.product.request.LoginRequest;
import com.product.request.PaymentRequest;
import com.product.request.TransferRequest;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletResponse;
@Service
public class TransactService {

	  static double currentBalance;
	  double newBalance;
	    LocalDateTime currentDateTime = LocalDateTime.now();

	  @Autowired
	  private static  AccountRepository accRepo;
	  @Autowired
	  PaymentRepository payrepo;
	  
	  @Autowired
	  TransactRepository transRepo;
	  
    public  ResponseEntity deposit(@RequestBody Map<String, String> requestMap, HttpSession session) {
		 String depositAmount = requestMap.get("deposit_amount");
	        String accountID = requestMap.get("account_id");
		
	        UserEntity user  = (UserEntity) session.getAttribute("user");
	        
	        int acc_id = Integer.parseInt(accountID);
			 long userId = user.getUserId();
			 
		        double depositAmountValue = Double.parseDouble(depositAmount);

		        if (depositAmountValue == 0) {
		            return ResponseEntity.badRequest().body("Deposit amount cannot be zero.");
		        }
		        
		        currentBalance = accRepo.getAccountBalance(userId, acc_id);
		        newBalance = currentBalance + depositAmountValue;
		        
		        accRepo.changeAccountsBalanceById(newBalance, acc_id);

		        transRepo.logTransaction(acc_id, "deposit", depositAmountValue, "online", "success", "Deposit Transaction Successfull", currentDateTime);

	        

		        Map<String, Object> response = new HashMap<>();
		        response.put("message", "Amount Deposited Successfully.");
		        response.put("accounts", accRepo.getUserAccountsById(userId)); 

		        return ResponseEntity.ok(response);
		        
	}
    
   public ResponseEntity transfer(@RequestBody TransferRequest request, HttpSession session) {
    	 String transfer_from = request.getSourceAccount();
         String transfer_to = request.getTargetAccount();
         String transfer_amount = request.getAmount();

         
         int transferFromId = Integer.parseInt(transfer_from);
         int transferToId = Integer.parseInt(transfer_to);
         double transferAmount = Double.parseDouble(transfer_amount);
         
         if (transferFromId == transferToId) {
             return ResponseEntity.badRequest().body("Cannot Transfer Into The Same Account, Please select the appropriate account to perform transfer.");

         }
         
         if (transferAmount == 0) {
             return ResponseEntity.badRequest().body("Cannot Transfer an amount of 0 (Zero) value, please enter a value greater than.");
         }

       //TODO: GET LOGGED IN USER:
         UserEntity  user = (UserEntity) session.getAttribute("user");

 	    long userId = user.getUserId();
         double currentBalanceOfAccountTransferringFrom = accRepo.getAccountBalance(userId, transferFromId);


         //TODO: CHECK IF TRANSFER AMOUNT IS MORE THAN CURRENT BALANCE:
         if (currentBalanceOfAccountTransferringFrom < transferAmount) {
             //Log Failed Transaction
        	 transRepo.logTransaction(transferFromId, "transfer", transferAmount, "online", "failed", "Insufficient funds.", currentDateTime);
             return ResponseEntity.badRequest().body("You have insufficient Funds to perform this transfer.");
         }
	  
         double currentBalanceOfAccountTransferringTo = accRepo.getAccountBalance(userId, transferToId);

         //TODO: SET NEW BALANCE

         double newBalanceOfAccountTransferringFrom = currentBalanceOfAccountTransferringFrom - transferAmount;

         double newBalanceOfAccountTransferringTo = currentBalanceOfAccountTransferringTo + transferAmount;

         //Changed The Balance Of The Account Transferring From:
         accRepo.changeAccountsBalanceById(newBalanceOfAccountTransferringFrom, transferFromId);

         //Changed The Balance Of The Account Transferring To:
         accRepo.changeAccountsBalanceById(newBalanceOfAccountTransferringTo, transferToId);

         //Log Successfull Transaction:
         transRepo.logTransaction(transferFromId, "Transfer", transferAmount, "online", "success", "Transfer Transaction Successfull", currentDateTime);

         Map<String, Object> response = new HashMap<>();
         response.put("message", "Transfer completed successfully.");
         response.put("accounts", accRepo.getUserAccountsById(userId)); // Token'i JSON yanıtının içine ekleyin

         return ResponseEntity.ok(response);

     }    	

    @PostMapping("/withdraw")
 public   ResponseEntity withdraw(@RequestBody Map<String, String> requestMap, HttpSession session) {
    	
    	 String withdrawalAmount = requestMap.get("withdrawal_amount");
         String accountId = requestMap.get("account_id");
         
         int account_id = Integer.parseInt(accountId);
         double withdrawal_amount = Double.parseDouble(withdrawalAmount);
         
         if (withdrawal_amount == 0) {
             return ResponseEntity.badRequest().body("Withdrawal amount cannot be 0 value.");

         }
         
         UserEntity  user = (UserEntity) session.getAttribute("user");

  	    long userId = user.getUserId();
         currentBalance = accRepo.getAccountBalance(userId, account_id);


         //TODO: CHECK IF WITHDRAW AMOUNT IS MORE THAN CURRENT BALANCE:
         if (currentBalance < withdrawal_amount) {
             //Log Failed Transaction
        	 transRepo.logTransaction(account_id, "withdrawal", withdrawal_amount, "online", "failed", "Insufficient funds.", currentDateTime);
             return ResponseEntity.badRequest().body("You have insufficient Funds to perform this transfer.");
         }

         //TODO: SET NEW BALANCE
         double newBalance = currentBalance - withdrawal_amount;

         //Changed The Balance Of The Account Transferring From:
         accRepo.changeAccountsBalanceById(newBalance, account_id);


         //Withdrawal Successfull Transaction
         transRepo.logTransaction(account_id, "Withdrawal", withdrawal_amount,"online","success","Withdrawal Transaction Successfull",currentDateTime);

         Map<String, Object> response = new HashMap<>();
         response.put("message", "Withdrawal Successfull!");
         response.put("accounts", accRepo.getUserAccountsById(userId)); 

         return ResponseEntity.ok(response);

     }
         
    
    
    @PostMapping("/payment")
    public  ResponseEntity payment(@RequestBody PaymentRequest request, HttpSession session) {

        String beneficiary = request.getBeneficiary();
        String account_number = request.getAccount_number();
        String account_id = request.getAccount_id();
        String reference = request.getReference();
        String payment_amount = request.getPayment_amount();
        
        
        int accountID = Integer.parseInt(account_id);
        double paymentAmount = Double.parseDouble(payment_amount);

        //TODO: CHECK FOR 0 (ZERO) VALUES:
        if (paymentAmount == 0) {
            return ResponseEntity.badRequest().body("Payment amount cannot be 0.");

        }

        UserEntity  user = (UserEntity) session.getAttribute("user");

  	    long userId = user.getUserId();
        currentBalance = accRepo.getAccountBalance(userId, accountID);

        //TODO: CHECK IF PAYMENT AMOUNT IS MORE THAN CURRENT BALANCE:
        if (currentBalance < paymentAmount) {
            String reasonCode = "Coult not Processed Payment due to insufficient funds.";
            payrepo.makePayment(accountID, beneficiary, account_number, paymentAmount, reference, "failed", reasonCode, currentDateTime);
            //Log Failed Transaction
            transRepo.logTransaction(accountID, "Payment", paymentAmount, "online", "failed", "Insufficient funds.", currentDateTime);
            return ResponseEntity.badRequest().body("You have insufficient Funds to perform this payment.");
        }

        //TODO: SET NEW BALANCE FOR ACCOUNT PAYING FROM:
        newBalance = currentBalance - paymentAmount;

        //TODO: UPDATE ACCOUNT PAYING FROM:
        accRepo.changeAccountsBalanceById(newBalance, accountID);

        //TODO: MAKE PAYMENT:
        String reasonCode = "Payment Processed Successfully!";

        payrepo.makePayment(accountID, beneficiary, account_number, paymentAmount, reference, "success", reasonCode, currentDateTime);

        //Log successfull transaction:
        transRepo.logTransaction(accountID, "Payment", paymentAmount,"online","success","Payment Transaction Successfull",currentDateTime);

        Map<String, Object> response = new HashMap<>();
        response.put("message", reasonCode);
        response.put("accounts", accRepo.getUserAccountsById(userId)); // Token'i JSON yanıtının içine ekleyin




        return ResponseEntity.ok(response);

    }
    


}
