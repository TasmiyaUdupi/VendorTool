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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ot.VendorTool.entity.ResponseStructure;
import com.ot.VendorTool.entity.Stock;
import com.ot.VendorTool.service.StockService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "*")
public class StockController {
	
	private static final Logger logger = Logger.getLogger(StockController.class);

	@Autowired
	private StockService stockService;
	
	@ApiOperation(value = "Save Stock", notes = "Input Is Stock Object and Return Stock Object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "Stock Already Exist") })
	@PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Stock>> saveStock(@RequestBody @Validated Stock stock ) {
		logger.info("Recieved request to add Stock " +stock.toString());
		return stockService.saveStock(stock);
	}
	
	@ApiOperation(value = "Fetch Stock by id", notes = "Input Is Id Of The Stock Object and return Stock Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Stock>> findStockById(@PathVariable long id) {
		logger.info("Recieved request to Fetch Stock By Id " +id);
		return stockService.getStockById(id);
	}
	
	@ApiOperation(value = "Fetch All Stock", notes = "Return The List Of Stocks")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Stocks Object") })
	@GetMapping(value = "/getallStock", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Stock>>> getAllStocks() {
		logger.info("Recieved request to Fetch All Stock " );
		return stockService.getAllStock();
	}
	
	@ApiOperation(value = "Fetch Stock By Type", notes = "Input Is Type Of The Stock Object And Return Stock Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getStockByType/{type}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Stock>>> findProductByName(@PathVariable String type) {
		logger.info("Recieved request to Fetch Stock By Type " +type);
		return stockService.getStockByType(type);
	}
	
	@ApiOperation(value = "Fetch All Stocks With Pagination And Sort", notes = "Return The List Of Stocks With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Stocks Object") })
	@GetMapping(value = "/getAllStocks/{offset}/{pageSize}/{field}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<Stock>>> getStocksWithPaginationAndSort(@PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {
		logger.info("Recieved request to Fetch All Stock With Pagination And Sorting " );
		return stockService.findStocksWithPaginationAndSorting(offset, pageSize, field);
	}
	
	@ApiOperation(value = "Delete Stock Object", notes = "Input Is Id Of The Stock Object And Output Is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/deleteStock/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> deleteProductById(@PathVariable long id) {
		logger.info("Recieved request to Delete Stock By Id " +id);
		return stockService.deleteStock(id);
	}
}
