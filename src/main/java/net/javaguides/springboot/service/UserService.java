package net.javaguides.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public boolean getUser(String username, String password) {
		
		if((userRepository.findByUserName(username,password)).size()>0) {
			return true;
		}
		return false;
	}
	
	public void saveUser(User user) {
		this.userRepository.save(user);
	}
}
