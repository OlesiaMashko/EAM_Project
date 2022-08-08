package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Admin;
import net.javaguides.springboot.model.User;

	@Repository
	public interface AdminRepository extends JpaRepository<Admin, Long>{


		@Query(value="select * from Admin u where u.username = ?1 AND u.password = ?2",nativeQuery=true)
		List<Admin> findByUserName(String username, String password);

	}


