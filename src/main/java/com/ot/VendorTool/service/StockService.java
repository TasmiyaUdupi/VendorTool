package com.ot.VendorTool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ot.VendorTool.dao.StockDao;
import com.ot.VendorTool.entity.ResponseStructure;
import com.ot.VendorTool.entity.Stock;
import com.ot.VendorTool.exception.DataNotFoundException;
import com.ot.VendorTool.exception.IdNotFoundException;

@Service
public class StockService {

	@Autowired
	private StockDao stockDao;

	public ResponseEntity<ResponseStructure<Stock>> saveStock(Stock stock) {

		ResponseStructure<Stock> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Stock Saved Successfully");
		responseStructure.setData(stockDao.saveStock(stock));
		return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Stock>> getStockById(long id) {

		ResponseStructure<Stock> responseStructure = new ResponseStructure<>();
		Stock stock = stockDao.getStockById(id);
		if (stock != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Stock Details By Id");
			responseStructure.setData(stock);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Stock ID " + id + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Stock>>> getAllStock() {

		ResponseStructure<List<Stock>> responseStructure = new ResponseStructure<>();
		List<Stock> list = stockDao.getAllStock();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Stock Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Stock Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<List<Stock>>> getStockByType(String type) {

		ResponseStructure<List<Stock>> responseStructure = new ResponseStructure<>();
		List<Stock> stocks = stockDao.getStockByType(type);
		for (Stock stock : stocks) {
			if (stock.getType().equalsIgnoreCase(type) && stock.getType() != null) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Stock Fetched By Type");
				responseStructure.setData(stocks);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new DataNotFoundException("Stock Data Not Present");
			}
		}
		throw new DataNotFoundException("Stock Data Not Present");
	}

	public ResponseEntity<ResponseStructure<Page<Stock>>> findStocksWithPaginationAndSorting(int offSet, int pageSize,
			String field) {

		ResponseStructure<Page<Stock>> responseStructure = new ResponseStructure<>();
		Page<Stock> page = stockDao.findStocksWithPaginationAndSorting(offSet, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details Of Stock Fetched");
			responseStructure.setData(page);
			responseStructure.setRecordCount(page.getSize());
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Stock Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteStock(long id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Stock stock = stockDao.getStockById(id);
		if (stock != null) {
			stockDao.deleteStock(stock);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Stock Of Id" + id + "Deleted");
			responseStructure.setData("Stock Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Stock Id" + id + " Not Found");
		}
	}
}
