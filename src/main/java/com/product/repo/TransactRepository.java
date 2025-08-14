package com.product.repo;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.product.model.TransactEntity;

import feign.Param;

@Repository
public interface TransactRepository extends CrudRepository<TransactEntity, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO transaction_history(account_id, transaction_type, amount, source, status, reason_code, created_at)" +
            "VALUES(:account_id, :transaction_type, :amount, :source, :status, :reason_code, :created_at)",nativeQuery = true)
    void logTransaction(@Param("account_id")int account_id,
                        @Param("transaction_type")String transaction_type,
                        @Param("amount")double amount,
                        @Param("source")String source ,
                        @Param("status")String status,
                        @Param("reason_code")String reason_code,
                        @Param("created_at")LocalDateTime created_at);
}