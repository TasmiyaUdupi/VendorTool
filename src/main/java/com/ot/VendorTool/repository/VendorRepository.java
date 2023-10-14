package com.ot.VendorTool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.VendorTool.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

	Optional<List<Vendor>> findByShopName(String shopName);

	Optional<List<Vendor>> findByShopNumber(String shopNumber);

}
