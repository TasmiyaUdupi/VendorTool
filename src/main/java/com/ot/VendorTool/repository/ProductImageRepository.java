package com.ot.VendorTool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ot.VendorTool.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

	@Query(nativeQuery = true, value = ("SELECT *,0 as clazz FROM product_image p WHERE product_id = ?"))
	List<ProductImage> findAllProductImageByProductId(long productId);

}
