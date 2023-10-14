package com.ot.VendorTool.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ot.VendorTool.dao.UserDao;
import com.ot.VendorTool.entity.ResponseStructure;
import com.ot.VendorTool.entity.User;
import com.ot.VendorTool.entity.Vendor;
import com.ot.VendorTool.entity.VendorGstImage;
import com.ot.VendorTool.exception.DataNotFoundException;
import com.ot.VendorTool.exception.DuplicateDataEntryException;
import com.ot.VendorTool.exception.EmailIdNotFoundException;
import com.ot.VendorTool.exception.IdNotFoundException;
import com.ot.VendorTool.exception.InvalidCredentialException;
import com.ot.VendorTool.exception.PhoneNumberNotFoundException;
import com.ot.VendorTool.util.EmailSender;
import com.ot.VendorTool.util.ImageUtils;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private EmailSender emailSender;

	public ResponseEntity<ResponseStructure<User>> saveAdmin(User user) {

		ResponseStructure<User> responseStructure = new ResponseStructure<>();

		if (userDao.getUserByEmail(user.getEmail()) != null || userDao.getUserByPhone(user.getPhone()) != null) {

			throw new DuplicateDataEntryException("Admin Already Exist's");

		} else {
			user.setPassword(encoder.encode(user.getPassword()));
			user.setRole("ROLE_ADMIN");
			emailSender.sendSimpleEmail(user.getEmail(),
					"Greetings \nYour Profile in Vendor Tool Has Been Created.\nThank You.",
					"Hello " + user.getUserName());
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Admin Saved Successfully");
			responseStructure.setData(userDao.saveUser(user));
			return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
		}
	}

	public ResponseEntity<ResponseStructure<User>> getAdminById(long id) {
		ResponseStructure<User> responseStructure = new ResponseStructure<>();
		User user = userDao.getUserById(id);
		if (user != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Admin Details By Id");
			responseStructure.setData(user);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Admin ID " + id + ", NOT FOUND");
		}
	}

	public ResponseEntity<ResponseStructure<List<User>>> getAllAdmin() {
		ResponseStructure<List<User>> responseStructure = new ResponseStructure<>();
		List<User> list = userDao.getAllUser();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Admin Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Admin Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<Page<User>>> getAdminsWithPaginationAndSorting(int offset, int pageSize,
			String field) {
		ResponseStructure<Page<User>> responseStructure = new ResponseStructure<>();
		Page<User> page = userDao.findUsersWithPaginationAndSorting(offset, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Admins Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Admin Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteAdminById(long id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		User user = userDao.getUserById(id);
		if (user != null) {
			userDao.deleteUser(user);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Admin Of Id " + id + " Data Deleted");
			responseStructure.setData("Admin Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Admin Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<User>> updateAdmin(User user) {
		ResponseStructure<User> responseStructure = new ResponseStructure<>();
		User user1 = userDao.getUserById(user.getId());
		if (user1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Admin Updated Successfully");
			user.setPassword(encoder.encode(user.getPassword()));
			user.setRole("ROLE_ADMIN");
			responseStructure.setData(userDao.saveUser(user));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Admin Id " + user.getId() + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<User>> getAdminByEmail(String email) {
		User user = userDao.getUserByEmail(email);
		if (user != null) {
			ResponseStructure<User> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Admin By Email-Id");
			responseStructure.setData(user);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new EmailIdNotFoundException("Admin-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<User>> getAdminByPhone(String phone) {
		User user = userDao.getUserByPhone(phone);
		if (user != null) {
			ResponseStructure<User> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Admin By PhoneNumber");
			responseStructure.setData(user);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new PhoneNumberNotFoundException("Admin-PhoneNumber : " + phone + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<User>>> getUserByName(String userName) {
		ResponseStructure<List<User>> responseStructure = new ResponseStructure<>();
		List<User> users = userDao.getUserByName(userName);
		for (User user : users) {
			if (user.getUserName().equalsIgnoreCase(userName) && users.size() > 0) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Fetched Admin By Name");
				responseStructure.setData(users);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new DataNotFoundException("Admin Data Not Present");
			}
		}
		throw new DataNotFoundException("Admin Data Not Present");
	}

	public ResponseEntity<ResponseStructure<Object>> validateUserByEmail(String email, String password) {

		User user = userDao.getUserByEmail(email);
		if (user != null) {
			if (encoder.matches(password, user.getPassword())) {
				ResponseStructure<Object> responseStructure = new ResponseStructure<Object>();
				int otp = (int) (Math.random() * (9999 - 1000) + 1000);
				user.setOtp(otp);
				userDao.saveUser(user);
				emailSender.sendSimpleEmail(user.getEmail(),
						"Enter the Otp to Validate Your Self \n The Generated Otp " + otp, "Verify Your Otp");
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("OTP SENT");
				responseStructure.setData(user);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new InvalidCredentialException("Invalid Password");
			}
		} else {
			throw new EmailIdNotFoundException("Admin-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<User>> validateOtp(int otp) {
		User user = userDao.getUserByOtp(otp);
		if (user != null) {
			ResponseStructure<User> responseStructure = new ResponseStructure<User>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Success");
			responseStructure.setData(user);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new InvalidCredentialException("Invalid OTP");
		}
	}

	public ResponseEntity<ResponseStructure<Object>> verifyEmailBeforeUpdate(String email) {
		User user = userDao.getUserByEmail(email);
		if (user != null) {
			ResponseStructure<Object> responseStructure = new ResponseStructure<>();
			String uuid = UUID.randomUUID().toString();
			String partOfUuid = uuid.substring(0, 11);
			if (partOfUuid.contains("-")) {
				String replace = partOfUuid.replace("-", "");
				user.setUuid(replace);
				userDao.saveUser(user);
				emailSender.sendSimpleEmail(user.getEmail(),
						"Enter the Unique to Validate Your Account \n The Generated Unique ID " + replace,
						"Verify Your Unique Id Before You Change YOur Password");
			} else {
				user.setUuid(partOfUuid);
				userDao.saveUser(user);
				emailSender.sendSimpleEmail(user.getEmail(),
						"Enter the Unique to Validate Your Account \n The Generated Unique ID " + partOfUuid,
						"Verify Your Unique Id Before You Change YOur Password");
			}
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Verify Admin By Email-Id");
			responseStructure.setData("Uuid Send To User Email-Id Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new EmailIdNotFoundException("User-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Object>> updatePasswordByUuid(String uuid, String newPassword) {
		ResponseStructure<Object> responseStructure = new ResponseStructure<>();
		User user = userDao.getUserByUuid(uuid);
		if (user != null) {
			user.setPassword(encoder.encode(newPassword));
			userDao.saveUser(user);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Password Reset");
			responseStructure.setData("Successfully Password Updated");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new InvalidCredentialException("User-Uuid : " + uuid + ", Not Match");
		}
	}

//	          Vendor CRUD Operations......

	public ResponseEntity<ResponseStructure<Vendor>> saveVendor(Vendor vendor) {

		ResponseStructure<Vendor> responseStructure = new ResponseStructure<>();

		if (userDao.getUserByEmail(vendor.getEmail()) != null || userDao.getUserByPhone(vendor.getPhone()) != null) {

			throw new DuplicateDataEntryException("Vendor Already Exist's");

		} else {
			vendor.setPassword(encoder.encode(vendor.getPassword()));
			emailSender.sendSimpleEmail(vendor.getEmail(),
					"Greetings \nYour Profile in Vendor Tool Has Been Created.\nThank You.",
					"Hello " + vendor.getUserName());
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Admin Saved Successfully");
			responseStructure.setData(userDao.saveVendor(vendor));
			return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
		}
	}

	public ResponseEntity<ResponseStructure<Vendor>> getVendorById(long id) {
		ResponseStructure<Vendor> responseStructure = new ResponseStructure<>();
		Vendor vendor = userDao.getVendorById(id);
		if (vendor != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Admin Vendors By Id");
			responseStructure.setData(vendor);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Vendor ID " + id + ", NOT FOUND");
		}
	}

	public ResponseEntity<ResponseStructure<List<Vendor>>> getAllVendor() {
		ResponseStructure<List<Vendor>> responseStructure = new ResponseStructure<>();
		List<Vendor> list = userDao.getAllVendor();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Vendor Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Vendor Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<Page<Vendor>>> getVendorsWithPaginationAndSorting(int offset, int pageSize,
			String field) {
		ResponseStructure<Page<Vendor>> responseStructure = new ResponseStructure<>();
		Page<Vendor> page = userDao.findVendorsWithPaginationAndSorting(offset, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Vendors Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Vendor Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteVendorById(long id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Vendor vendor = userDao.getVendorById(id);
		if (vendor != null) {
			userDao.deleteUser(vendor);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Vendor Of Id " + id + " Data Deleted");
			responseStructure.setData("Vendor Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Vendor Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Vendor>> updateVendor(Vendor vendor) {
		ResponseStructure<Vendor> responseStructure = new ResponseStructure<>();
		Vendor vendor1 = userDao.getVendorById(vendor.getId());
		if (vendor1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Admin Updated Successfully");
			vendor.setPassword(encoder.encode(vendor.getPassword()));
			vendor.setRole("ROLE_VENDOR");
			responseStructure.setData(userDao.saveVendor(vendor));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Vendor Id " + vendor.getId() + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Vendor>>> getVendorByShopName(String shopName) {
		ResponseStructure<List<Vendor>> responseStructure = new ResponseStructure<>();
		List<Vendor> vendors = userDao.getVendorByShopName(shopName);
		for (Vendor vendor : vendors) {
			if (vendor.getShopName().equalsIgnoreCase(shopName) && vendors.size() > 0) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Fetched Vendor By Shop-Name");
				responseStructure.setData(vendors);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new DataNotFoundException("Vendor Data Not Present");
			}
		}
		throw new DataNotFoundException("Vendor Data Not Present");
	}

	public ResponseEntity<ResponseStructure<List<Vendor>>> getVendorByShopNumber(String shopNumber) {
		ResponseStructure<List<Vendor>> responseStructure = new ResponseStructure<>();
		List<Vendor> vendors = userDao.getVendorByShopNumber(shopNumber);
		for (Vendor vendor : vendors) {
			if (vendor.getUserName().equalsIgnoreCase(shopNumber) && vendors.size() > 0) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Fetched Vendor By Shop-Number");
				responseStructure.setData(vendors);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new DataNotFoundException("Vendor Data Not Present");
			}
		}
		throw new DataNotFoundException("Vendor Data Not Present");
	}

	public ResponseEntity<ResponseStructure<String>> uploadVendorGstImage(long vendorId, MultipartFile file) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Vendor vendor = userDao.getVendorById(vendorId);
		if (vendor != null) {
			if (vendor.getGstImage() != null) {

				if (file.getSize() > 4 * 1024 * 1024) {
					throw new InvalidCredentialException("File size exceeds the maximum allowed limit (4MB)");
				} else {
					try {
						vendor.setGstImage(VendorGstImage.builder().name(file.getOriginalFilename())
								.type(file.getContentType()).imageData(ImageUtils.compressImage(file.getBytes()))
								.id(vendor.getGstImage().getId()).build());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					vendor.setGstImage(VendorGstImage.builder().name(file.getOriginalFilename())
							.type(file.getContentType()).imageData(ImageUtils.compressImage(file.getBytes())).build());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			userDao.saveVendor(vendor);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Vendor GST Image Saved Successfully");
			responseStructure.setData("Image Saved Successfully For Vendor of Id -> " + vendorId);
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		} else {

			throw new IdNotFoundException("ID " + vendorId + " not Found");
		}
	}

	public ResponseEntity<?> downloadVendorGstImageByVendorId(long vendorId) {
		Vendor vendor = userDao.getVendorById(vendorId);
		if (vendor != null) {
			VendorGstImage vendorGstImage = vendor.getGstImage();
			if (vendorGstImage != null) {
				vendorGstImage.setImageData(ImageUtils.decompressImage(vendorGstImage.getImageData()));
				byte[] img = vendorGstImage.getImageData();
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(img);
			} else {
				throw new IdNotFoundException("Vendor GST Image is Not Present" + vendorId);
			}
		} else {
			throw new IdNotFoundException("Vendor Id is Not Present" + vendorId);
		}
	}

}
