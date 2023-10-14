package com.ot.VendorTool.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ot.VendorTool.entity.ResponseStructure;
import com.ot.VendorTool.entity.User;
import com.ot.VendorTool.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

	private static final Logger logger = Logger.getLogger(AdminController.class);

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Save Admin", notes = "Input is User Object and return User object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "User Already Exist") })
	@PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<User>> saveUser(@RequestBody @Validated User user) {

		logger.info("Recieved request to add User " +user.getId() + user.getUserName());
		return userService.saveAdmin(user);
	}

	@ApiOperation(value = "Fetch Admin by id", notes = "Input Is Id Of The User Object and return User Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<User>> findUserById(@PathVariable long id) {
		logger.info("Recieved request to Fetch User By Id " + id);
		return userService.getAdminById(id);
	}

	@ApiOperation(value = "Fetch All Admin", notes = "Return The List Of Admins")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Users Object") })
	@GetMapping(value = "/getAllAdmin", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<User>>> getAllUsers() {
		logger.info("Recieved request to Fetch All User " );
		return userService.getAllAdmin();
	}

	@ApiOperation(value = "Delete Admin Object", notes = "Input Is Id Of The Admin Object And Output Is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/deleteAdmin/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> deleteUserById(@PathVariable long id) {
		logger.info("Recieved request to Delete User By Id " + id);
		return userService.deleteAdminById(id);
	}

	@ApiOperation(value = "Update Admin Object", notes = "Input Is Admin Object And Return Updated Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/updateAdmin", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<User>> updateAdmin(@RequestBody @Validated User user) {
		logger.info("Recieved request to Update User " +user.getId() + user.getUserName());
		return userService.updateAdmin(user);
	}

	@ApiOperation(value = "Fetch Admin By Name", notes = "Input Is Name Of The Admin Object And Return Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getAdminbyname/{userName}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<User>>> findUserByName(@PathVariable String userName) {
		logger.info("Recieved request to Fetch User By Name " + userName);
		return userService.getUserByName(userName);
	}

	@ApiOperation(value = "Fetch Admin By Email-Id", notes = "Input Is Email-Id Of The Admin Object And Return Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/email/{email}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<User>> findAdminByEmail(@PathVariable String email) {
		logger.info("Recieved request to Fetch User By Email-Id " +email);
		return userService.getAdminByEmail(email);
	}

	@ApiOperation(value = "Fetch Admin By PhoneNumber", notes = "Input Is PhoneNumber Of The Admin Object And Return Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/phone/{phone}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<User>> findAdminByPhone(@PathVariable String phone) {
		logger.info("Recieved request to Fetch User By Phone Number " + phone);
		return userService.getAdminByPhone(phone);
	}

	@ApiOperation(value = "Validate Admin By Email", notes = "Inputs are Admin email id and password and return Admin object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/validate/email/{email}/{password}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Object>> validateUserByEmail(@PathVariable String email,
			@PathVariable String password) {
		logger.info("Recieved request to Validate User By Email " + email);
		return userService.validateUserByEmail(email, password);
	}

	@GetMapping(value = "/otp", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<User>> validateOtp(@RequestParam int otp) {
		logger.info("Recieved request to Validate User By OTP ");
		return userService.validateOtp(otp);
	}

	@ApiOperation(value = "Fetch All Admins With Pagination And Sort", notes = "Return The List Of Admins With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Users Object") })
	@GetMapping(value = "/getAllAdmins/{offset}/{pageSize}/{field}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<User>>> getAdminsWithPaginationAndSort(@PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {
		logger.info("Recieved request to Fetch All User With Pagination And Sorting ");
		return userService.getAdminsWithPaginationAndSorting(offset, pageSize, field);
	}
}
