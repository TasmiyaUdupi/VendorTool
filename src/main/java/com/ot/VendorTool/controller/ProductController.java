package com.ot.VendorTool.controller;

import java.io.IOException;
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

import com.ot.VendorTool.entity.Product;
import com.ot.VendorTool.entity.ResponseStructure;
import com.ot.VendorTool.service.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {
	
	private static final Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@ApiOperation(value = "Save Product", notes = "Input Is Product Object and Return Product Object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "Product Already Exist") })
	@PostMapping(value = "/save/{vendorId}/{stockId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Product>> saveProduct(@RequestBody @Validated Product product,
			@PathVariable long vendorId, @PathVariable long stockId) {
		logger.info("Recieved request to add Product " +product.getId() + product.getName());
		return productService.saveProduct(product, vendorId, stockId);
	}

	@ApiOperation(value = "Fetch Product by id", notes = "Input Is Id Of The Product Object and return Product Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Product>> findProductById(@PathVariable long id) {
		logger.info("Recieved request to add Product " +id);
		return productService.getProductById(id);
	}

	@ApiOperation(value = "Fetch All Product", notes = "Return The List Of Products")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Products Object") })
	@GetMapping(value = "/getallproduct", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Product>>> getAllProducts() {
		logger.info("Recieved request to Fetch All Products " );
		return productService.getAllProduct();
	}

	@ApiOperation(value = "Delete Product Object", notes = "Input Is Id Of The Product Object And Output Is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/deleteproduct/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> deleteProductById(@PathVariable long id) {
		logger.info("Recieved request to Delete Product By Id "+ id );
		return productService.deleteProductById(id);
	}

	@ApiOperation(value = "Update Product Object", notes = "Input Is Product Object And Return Updated Product Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/updateproduct", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Product>> updateProduct(@RequestBody @Validated Product product) {
		logger.info("Recieved request to Update Product " +product.getId() +product.getName());
		return productService.updateProduct(product);
	}

	@ApiOperation(value = "Fetch Product By Name", notes = "Input Is Name Of The Product Object And Return Product Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getproductbyname/{name}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Product>>> findProductByName(@PathVariable String name) {
		logger.info("Recieved request to Fetch Product By Name " + name);
		return productService.getProductByName(name);
	}

	@ApiOperation(value = "Fetch All Products With Pagination And Sort", notes = "Return The List Of Products With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Products Object") })
	@GetMapping(value = "/getAllProducts/{offset}/{pageSize}/{field}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<Product>>> getProductsWithPaginationAndSort(@PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {
		logger.info("Recieved request to Fetch All Products With Pagination And Sorting" );
		return productService.findProductsWithPaginationAndSorting(offset, pageSize, field);
	}

	@ApiOperation(value = "Save ProductImage", notes = "Input is ProductImage file")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PostMapping(value = "/uploadProductImage", consumes = { MediaType.ALL_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> uploadProductImage(@RequestParam("productId") long productId,
			@RequestParam("file") List<MultipartFile> file) throws IOException {
		logger.info("Recieved request to Upload Product Image " +productId);
		return productService.uploadProductImages(productId, file);
	}

	@ApiOperation(value = "Download ProductImage", notes = "Input is Product Id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/downloadProductImage", produces = { MediaType.ALL_VALUE })
	public ResponseEntity<?> downloadProductImage(@RequestParam long productId) {
		logger.info("Recieved request to Download Product Image " );
		
		return productService.downloadProductImage(productId);
	}
}