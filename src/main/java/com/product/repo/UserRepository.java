package com.product.repo;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.model.UserEntity;

import org.springframework.data.repository.query.Param;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> { 
	
	 UserEntity findByMobileNum(long mobileNum);

	
	 @Modifying
	    @Transactional
	    @Query(value = "UPDATE users SET token = NULL, code = NULL, verified = 1, verified_at = NOW(), updated_at = NOW() " +
	                   "WHERE token = :token AND code = :code", nativeQuery = true)
	    void verifyAccount(@Param("token") String token, @Param("code") String code);

	    @Query(value = "SELECT token FROM users WHERE token = :token", nativeQuery = true)
	    String checkToken(@Param("token") String token);


	    @Query(value = "SELECT MAX(user_id) FROM users", nativeQuery = true)
	    Integer getLastUserId();

	    
	    @Query(value="SELECT * FROM users WHERE email = :email", nativeQuery = true)
	    UserEntity getUserDetails(@Param("email")String email);

		
		  @Modifying
		    @Transactional
		    @Query(value = "INSERT INTO Customer (user_name, email, password, token, code) " +
		                   "VALUES(:user_name, :email, :password, :token, :code)", nativeQuery = true)
		    void registerUser(
		        @Param("user_name") String userName,
		        @Param("email") String email,
		        @Param("password") String password,
		        @Param("token") String token,
		        @Param("code") String code
		    );
		  
		    UserEntity findByEmail(String email);


}