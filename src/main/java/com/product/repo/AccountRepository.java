package com.product.repo;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.model.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
	
	  List<AccountEntity> findByUserId(long userId);

	  AccountEntity findByAccountNumber(String accountNumber);
	  
	  @Query("SELECT a FROM AccountEntity a WHERE a.userId = :userId")
	  List<AccountEntity> getUserAccountsById(@Param("userId") Long userId);
	    
	  @Query(value="SELECT sum(balance) FROM accounts WHERE user_id = :user_id",nativeQuery= true)
	    BigDecimal getTotalBalance(@Param("user_id") long userId);

	    @Query(value="SELECT balance FROM accounts WHERE user_id = :user_id AND account_id = :account_id",nativeQuery= true)
	    double getAccountBalance(@Param("user_id") long userId, @Param("account_id") int  account_id);
	    
	    @Modifying
	    @Query(value = "UPDATE accounts set balance = :new_balance WHERE account_id= :account_id", nativeQuery = true)
	    @Transactional
	    void changeAccountsBalanceById(@Param("new_balance") double new_balance, @Param("account_id")int account_id);

}
