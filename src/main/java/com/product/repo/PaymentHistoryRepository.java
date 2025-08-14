package com.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.product.model.PaymentHistoryEntity;

import feign.Param;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity,Integer> {

	@Query(value = "SELECT * FROM Payment_History WHERE user_id = :userId", nativeQuery = true)
	List<PaymentHistoryEntity> getPaymentsRecordsById(@Param("userId") long userId);

	
}