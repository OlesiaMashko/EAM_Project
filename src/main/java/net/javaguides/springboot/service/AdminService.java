package net.javaguides.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;

import net.javaguides.springboot.model.Admin;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.AdminRepository;
import net.javaguides.springboot.repository.UserRepository;

public class AdminService {
	@Autowired
	AdminRepository adminRepository;
	
	public boolean getAdmin(String username, String password) {
		
		if((adminRepository.findByUserName(username,password)).size()>0) {
			return true;
		}
		return false;
	}
	
	public void saveAdmin(Admin admin) {
		this.adminRepository.save(admin);
	}
}
