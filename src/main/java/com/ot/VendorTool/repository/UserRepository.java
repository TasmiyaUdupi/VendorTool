package com.ot.VendorTool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.VendorTool.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByEmail(String email);

	public Optional<User> findByPhone(String phone);

	public Optional<List<User>> findByUserName(String name);

	public Optional<User> findByOtp(int otp);

	public Optional<User> findByUuid(String uuid);
	

}
