package com.ot.VendorTool.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import com.ot.VendorTool.entity.Product;
import com.ot.VendorTool.entity.ProductImage;
import com.ot.VendorTool.repository.ProductImageRepository;
import com.ot.VendorTool.repository.ProductRepository;

@Repository
public class ProductDao {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public Product getProductById(long id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			return product.get();
		} else {
			return null;
		}
	}

	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	public List<Product> getProductByName(String productName) {
		Optional<List<Product>> product = productRepository.findByName(productName);
		if (product.isPresent()) {
			return product.get();
		} else {
			return null;
		}
	}

	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}

	public Page<Product> findProductsWithPaginationAndSorting(int offset, int pageSize, String field) {
		Page<Product> products = productRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return products;
	}

	public Optional<ProductImage> downloadProductImage(Long imageId) {
		return productImageRepository.findById(imageId);
	}
}