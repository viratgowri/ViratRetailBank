package com.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.product.model.TransHistoryEntity;

import org.springframework.data.repository.query.Param;

public interface TransactHistoryRepository extends JpaRepository<TransHistoryEntity, Integer> {

    @Query(value = "SELECT * FROM transaction_history WHERE user_id = :user_id", nativeQuery = true)
    List<TransHistoryEntity> getTransactionRecordsById(@Param("user_id") long userId);
    
    @Query(value = "SELECT * FROM transaction_history WHERE account_id = :account_id", nativeQuery = true)
    List<TransHistoryEntity> getTransactionRecordsByAccountId(@Param("account_id") long accountId);

}

