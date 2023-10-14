package com.ot.VendorTool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.VendorTool.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<List<Product>> findByName(String productName);

}
