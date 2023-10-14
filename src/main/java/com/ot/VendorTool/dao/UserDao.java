package com.ot.VendorTool.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import com.ot.VendorTool.entity.User;
import com.ot.VendorTool.entity.Vendor;
import com.ot.VendorTool.repository.UserRepository;
import com.ot.VendorTool.repository.VendorRepository;



@Repository
public class UserDao {

	@Autowired
	private UserRepository userRepository;
	

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public User getUserById(long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}

	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	public Page<User> findUsersWithPaginationAndSorting(int offset, int pageSize, String field) {
		Page<User> admins = userRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return admins;
	}


	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public User getUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}

	public User getUserByPhone(String phone) {
		Optional<User> user=userRepository.findByPhone(phone);
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}

	public List<User> getUserByName(String userName) {
		Optional<List<User>> user=userRepository.findByUserName(userName);
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}
	
	
	public User getUserByOtp(int otp) {
		Optional<User> user=userRepository.findByOtp(otp);
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}
	
	public User getUserByUuid(String uuid) {
		Optional<User> user=userRepository.findByUuid(uuid);
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}
	
	
	
		   //	Vendor CRUD Operations....
	
	
	@Autowired
	private VendorRepository vendorRepository;
	
	
	public Vendor saveVendor(Vendor vendor) {
		return vendorRepository.save(vendor);
	}
	
	public Vendor getVendorById(long id ) {
		Optional<Vendor> vendor =vendorRepository.findById(id);
		if(vendor.isPresent()) {
			return vendor.get();
		}else {
			return null;
		}
	}
	
	public List<Vendor> getAllVendor(){
		return vendorRepository.findAll();
	}
	
	public Page<Vendor> findVendorsWithPaginationAndSorting(int offset, int pageSize, String field) {
		Page<Vendor> vendors = vendorRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return vendors;
	}
	
	public void deleteVendor(Vendor vendor) {
		userRepository.delete(vendor);
	}
	
	public List<Vendor> getVendorByShopName(String shopName) {
		Optional<List<Vendor>> vendor = vendorRepository.findByShopName(shopName);
		return vendor.orElse(null);
		
	}
	
	public List<Vendor> getVendorByShopNumber(String shopNumber) {
		Optional<List<Vendor>> vendor = vendorRepository.findByShopNumber(shopNumber);
		return vendor.orElse(null);
	}
}


