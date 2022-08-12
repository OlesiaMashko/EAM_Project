package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.javaguides.springboot.model.Claims;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.User;

public interface ProductService {
	List<Product> getAllProducts(String username);
	void saveProduct(Product product);
	void saveClaim(Claims claim);
	Product getProductById(long id);
	void deleteProductById(long id);
	void approveClaimById(long id);
	void rejectClaimById(long id);
	Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String uname);
	Page<Product> findPaginatedAdmin(int pageNo, int pageSize, String sortField, String sortDirection, String uname);
	List<Product> getAllProducts();
	Product getProductByName(String productName);
	Page<Claims> findPaginatedClaim(int pageNo, int pageSize, String sortField, String sortDirection, String pname);
	Page<Claims> findPaginatedClaimAdmin(int pageNo, int pageSize, String sortField, String sortDirection, String pname);
	Page<User> findPaginatedUser(int pageNo, int pageSize, String sortField, String sortDirection);
	
}
