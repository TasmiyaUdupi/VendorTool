package com.ot.VendorTool.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ot.VendorTool.entity.JwtRequest;
import com.ot.VendorTool.entity.JwtResponse;
import com.ot.VendorTool.entity.ResponseStructure;
import com.ot.VendorTool.service.AuthenticateService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins = "*")
public class AuthenticationController {

	private static final Logger logger = Logger.getLogger(AuthenticationController.class);
	
	@Autowired
	private AuthenticateService authenticateService;

	@ApiOperation(value = "Authenticate By UserName and password", notes = "input jwtRequest --- output jwtResponse obj")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 400, message = "Invalid Username or password") })
	@PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ResponseStructure<JwtResponse>> authenticate(@RequestBody JwtRequest jwtRequest)
			throws Exception {
		logger.info("Recieved request to Authenticate By UserName and password ");
		return authenticateService.authenticate(jwtRequest);
	}

}
