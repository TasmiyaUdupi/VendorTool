package com.ot.VendorTool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.VendorTool.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{

	List<Stock> findStockByType(String type);



}
