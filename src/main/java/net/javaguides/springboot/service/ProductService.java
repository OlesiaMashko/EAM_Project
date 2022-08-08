package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.User;

public interface ProductService {
	List<Product> getAllProducts(String username);
	void saveProduct(Product employee);
	Product getProductById(long id);
	void deleteProductById(long id);
	Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String uname);
	Page<User> findPaginatedUser(int pageNo, int pageSize, String sortField, String sortDirection);
	List<Product> getAllProducts();
	Product getProductByName(String productName);
	
}
