package com.ot.VendorTool.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ot.VendorTool.dao.ProductDao;
import com.ot.VendorTool.dao.StockDao;
import com.ot.VendorTool.dao.UserDao;
import com.ot.VendorTool.entity.Product;
import com.ot.VendorTool.entity.ProductImage;
import com.ot.VendorTool.entity.ResponseStructure;
import com.ot.VendorTool.entity.Stock;
import com.ot.VendorTool.entity.Vendor;
import com.ot.VendorTool.exception.DataNotFoundException;
import com.ot.VendorTool.exception.IdNotFoundException;
import com.ot.VendorTool.repository.ProductImageRepository;
import com.ot.VendorTool.util.ImageUtils;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private StockDao stockDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProductImageRepository productImageRepository;

	public ResponseEntity<ResponseStructure<Product>> saveProduct(Product product, long vendorId, long stockId) {

		ResponseStructure<Product> responseStructure = new ResponseStructure<>();

		Vendor vendor = userDao.getVendorById(vendorId);
		Stock stock = stockDao.getStockById(stockId);
		if (vendor != null) {
			if (stock != null) {
				product.setHandlingCharges(10.0);
				double pro = product.getPrice() * product.getQuantity();
				product.setTotalSalesPrice(pro + (pro * (10.0 / 100)));
				product.setStock(stock);
				product.setVendor(vendor);
				responseStructure.setStatus(HttpStatus.CREATED.value());
				responseStructure.setMessage("Product Saved Successfully");
				responseStructure.setData(productDao.saveProduct(product));
				return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
			} else {
				throw new IdNotFoundException("Stock ID" + stockId + "Not Found");
			}
		} else {
			throw new IdNotFoundException("Vendor ID" + vendorId + "Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Product>> getProductById(long id) {

		ResponseStructure<Product> responseStructure = new ResponseStructure<>();
		Product product = productDao.getProductById(id);
		if (product != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Product Details By Id");
			responseStructure.setData(product);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Product ID " + id + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Product>>> getAllProduct() {
		ResponseStructure<List<Product>> responseStructure = new ResponseStructure<>();
		List<Product> list = productDao.getAllProduct();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Product Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Product Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<Page<Product>>> findProductsWithPaginationAndSorting(int offset,
			int pageSize, String field) {

		ResponseStructure<Page<Product>> responseStructure = new ResponseStructure<>();
		Page<Product> page = productDao.findProductsWithPaginationAndSorting(offset, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Product Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Product Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteProductById(long id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Product product = productDao.getProductById(id);
		if (product != null) {
			productDao.deleteProduct(product);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Product Of Id " + id + " Data Deleted");
			responseStructure.setData("Product Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Product Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Product>>> getProductByName(String productName) {
		ResponseStructure<List<Product>> responseStructure = new ResponseStructure<>();
		List<Product> products = productDao.getProductByName(productName);
		for (Product product : products) {
			if (product.getName().equalsIgnoreCase(productName) && product.getName() != null) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Fetched Product By Name");
				responseStructure.setData(products);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new DataNotFoundException("Product Data Not Present");
			}
		}
		throw new DataNotFoundException("Product Data Not Present");
	}

	public ResponseEntity<ResponseStructure<Product>> updateProduct(Product product) {
		ResponseStructure<Product> responseStructure = new ResponseStructure<>();
		Product product1 = productDao.getProductById(product.getId());
		if (product1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Product Updated Successfully");

			responseStructure.setData(productDao.saveProduct(product));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Product Id " + product.getId() + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<String>> uploadProductImages(long productId, List<MultipartFile> files) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		try {
			/* Retrieve the product from the database */
			Product product = productDao.getProductById(productId);

			if (product != null) {
				List<ProductImage> productImages = new ArrayList<>();
				for (MultipartFile file : files) {
					if (!file.isEmpty()) {
						ProductImage productImage = new ProductImage();
						productImage.setName(file.getOriginalFilename());
						productImage.setType(file.getContentType());
						productImage.setImageData(ImageUtils.compressImage(file.getBytes()));
						productImage.setProduct(product);
						productImages.add(productImage);
					}
				}
				if (!productImages.isEmpty()) {
					/* Save the list of image entities */
					productImageRepository.saveAll(productImages);
					return new ResponseEntity<>(responseStructure, HttpStatus.OK);
				} else {
					/* No valid files were provided */
					responseStructure.setMessage("No valid files provided.");
					return new ResponseEntity<>(responseStructure, HttpStatus.BAD_REQUEST);
				}
			} else {
				/* Product not found, return an error response */
				responseStructure.setMessage("Product not found for ID " + productId);
				return new ResponseEntity<>(responseStructure, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			/* Handle other exceptions, such as IOException */
			responseStructure.setMessage("An error occurred: " + e.getMessage());
			return new ResponseEntity<>(responseStructure, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> downloadProductImage(long productId) {
		List<ProductImage> productImages = productImageRepository.findAllProductImageByProductId(productId);

		if (productImages.size() > 0) {
			for(ProductImage productImage :productImages) {
				productImage.setImageData(ImageUtils.decompressImage(productImage.getImageData()));
				byte[] img = productImage.getImageData();
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(img);
			}
			return null;
		}else {
			throw new DataNotFoundException("The No Image Of Product ");
		}
//		if (imageOptional.isPresent()) {
//			ProductImage productImage = imageOptional.get();
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.IMAGE_JPEG); // You can set the appropriate media type
//
//			return new ResponseEntity<>(productImage.getImageData(), headers, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
		
	}
}