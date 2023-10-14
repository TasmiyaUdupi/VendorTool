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
import org.springframework.web.multipart.MultipartFile;

import com.ot.VendorTool.entity.ResponseStructure;
import com.ot.VendorTool.entity.Vendor;
import com.ot.VendorTool.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/vendor")
@CrossOrigin(origins = "*")
public class VendorController {
	
	private static final Logger logger = Logger.getLogger(VendorController.class);

	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "Save Vendor", notes = "Input is Vendor Object and return Vendor object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "Vendor Already Exist") })
	@PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Vendor>> saveVendor(@RequestBody @Validated Vendor vendor) {
		logger.info("Recieved request to add Vendor " +vendor.getId() +vendor.getUserName());

		return userService.saveVendor(vendor);
	}
	
	@ApiOperation(value = "Fetch Vendor by id", notes = "Input Is Id Of The Vendor Object and return Vendor Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Vendor>> findVendorById(@PathVariable long id) {
		logger.info("Recieved request to Fetch Vendor By Id "+id);
		return userService.getVendorById(id);
	}
	
	@ApiOperation(value = "Fetch All Vendor", notes = "Return The List Of Vendors")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Users Object") })
	@GetMapping(value = "/getAllVendor", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Vendor>>> getAllVendors() {
		logger.info("Recieved request to Fetch All Vendors ");
		return userService.getAllVendor();
	}
	
	@ApiOperation(value = "Fetch All Vendors With Pagination And Sort", notes = "Return The List Of Vendors With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Vendors Object") })
	@GetMapping(value = "/getAllVendors/{offset}/{pageSize}/{field}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<Vendor>>> getVendorsWithPaginationAndSort(@PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {
		logger.info("Recieved request to Fetch All Vendor With Pagination And Sorting");
		return userService.getVendorsWithPaginationAndSorting(offset, pageSize, field);
	}

	@ApiOperation(value = "Delete Vendor Object", notes = "Input Is Id Of The Vendor Object And Output Is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/deleteVendor/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> deleteVendorById(@PathVariable long id) {
		logger.info("Recieved request to Delete Vendor By Id "+id);
		return userService.deleteVendorById(id);
	}

	@ApiOperation(value = "Update Vendor Object", notes = "Input Is Vendor Object And Return Updated Vendor Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/updateVendor", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Vendor>> updateVendor(@RequestBody @Validated Vendor vendor) {
		logger.info("Recieved request to Update Vendor "+vendor.getId() +vendor.getUserName());
		return userService.updateVendor(vendor);
	}
	
	@ApiOperation(value = "Fetch Vendor By Shop-Name", notes = "Input Is Shop-Name Of The Vendor Object And Return Vendor Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getVendorbyShopName/{shopName}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Vendor>>> findVendorByShopName(@PathVariable String shopName) {
		logger.info("Recieved request to Fetch Vendor By Shop-Name "+shopName);
		return userService.getVendorByShopName(shopName);
	}
	
	@ApiOperation(value = "Fetch Vendor By Shop-Number", notes = "Input Is Shop-Number Of The Vendor Object And Return Vendor Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getVendorbyShopNumber/{shopNumber}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Vendor>>> findVendorByShopNumber(@PathVariable String shopNumber) {
		logger.info("Recieved request to Fetch Vendor By Shop-Number "+shopNumber);
		return userService.getVendorByShopNumber(shopNumber);
	}
	
	@ApiOperation(value = "Save VendorGstImage", notes = "Input is VendorGstImage file")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),@ApiResponse(code = 404, message = "Not Found") })
	@PostMapping(value = "/uploadVendorGstImage", consumes = { MediaType.ALL_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> uploadVendorGstImage(@RequestParam long vendorId,
			@RequestParam("image") MultipartFile file) {
		logger.info("Recieved request to Save Vendor GST Image "+vendorId);
		return userService.uploadVendorGstImage(vendorId, file);
	}

	@ApiOperation(value = "Download VendorGstImage", notes = "Input is Vendor Id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/downloadVendorGstImage", produces = { MediaType.ALL_VALUE })
	public ResponseEntity<?> downloadProductImage(@RequestParam long vendorId) {
		logger.info("Recieved request to Download Vendor GST Image "+vendorId);
		return userService.downloadVendorGstImageByVendorId(vendorId);
	}
}
