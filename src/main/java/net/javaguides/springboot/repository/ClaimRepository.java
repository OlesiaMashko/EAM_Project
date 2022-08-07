package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.javaguides.springboot.model.Claims;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.User;
@Transactional
@Repository
public interface ClaimRepository extends JpaRepository<Claims, Long>{

	@Query(value="select * from claims u where u.product_name = ?1",nativeQuery=true)
	Page<Claims> findByProductName(String productName, Pageable pageable);
	
	@Modifying
	@Query(value="update claims u set u.status = ?1 where u.id = ?2",nativeQuery=true)
	void approveClaim(String status, long id);

}
