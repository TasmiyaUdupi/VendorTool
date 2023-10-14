package com.ot.VendorTool.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.ot.VendorTool.entity.Stock;
import com.ot.VendorTool.repository.StockRepository;

@Repository
public class StockDao {

	@Autowired
	private StockRepository stockRepository;
	
	public Stock saveStock(Stock stock) {
		return stockRepository.save(stock);
	}
	
	public Stock getStockById(long id) {
		Optional<Stock> stock=stockRepository.findById(id);
		if(stock.isPresent()) {
			return stock.get();
		}else {
			return null;
		}
	}
	
	public List<Stock> getAllStock(){
		return stockRepository.findAll();
	}
	
	
	public List<Stock> getStockByType(String type){
		return stockRepository.findStockByType(type);
	}
	
	public Page<Stock> findStocksWithPaginationAndSorting(int offset, int pageSize, String field) {
		Page<Stock> stocks = stockRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return stocks;
	}
	
	public void deleteStock(Stock stock) {
		stockRepository.delete(stock);
	}
}
