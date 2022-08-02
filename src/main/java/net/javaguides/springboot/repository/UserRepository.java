package net.javaguides.springboot.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value="select * from Users u where u.username = ?1 AND u.password = ?2",nativeQuery=true)
	List<User> findByUserName(String username, String password);
}
