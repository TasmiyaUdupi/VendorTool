package com.ot.VendorTool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ot.VendorTool.entity.ResponseStructure;
import com.ot.VendorTool.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/password")
@CrossOrigin(origins = "*")
public class PasswordResetController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Forget Password", notes = "Inputs are User-Email and return Uuid")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Fond") })
	@PostMapping(value = "/verifyEmailBeforeUpdatePassword/{email}", produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ResponseStructure<Object>> validateUserByEmail(@PathVariable String email) {
		return userService.verifyEmailBeforeUpdate(email);
	}

	@ApiOperation(value = "Forget Password", notes = "Inputs are User-Password and return User Object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PatchMapping(value = "/forget/{uuid}/{newPassword}", produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ResponseStructure<Object>> updatePassword(@PathVariable String uuid,
			@PathVariable String newPassword) {
		return userService.updatePasswordByUuid(uuid, newPassword);
	}

}