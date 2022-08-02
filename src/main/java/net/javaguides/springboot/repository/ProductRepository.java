package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query(value="select * from products u where u.username = ?1",nativeQuery=true)
	Page<Product> findByUserName(String username, Pageable pageable);
	
	@Query(value="select * from products u where u.product_name = ?1",nativeQuery=true)
	Product findByName(String productName);
	
}
