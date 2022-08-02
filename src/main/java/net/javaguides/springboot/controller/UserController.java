package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserService;

@Controller
public class UserController {
	String uname;
	String pname;
	
	
	@Autowired
	private UserService userService;
	
	
	
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginPage() {
		return "login";
	}
	
	@PostMapping("/login")
	public String welcomePage(ModelMap model,Model model1, @RequestParam String username, @RequestParam String password) {
		
		if(userService.getUser(username, password)) {
			model.put("username",username);
			System.out.println(username);
			uname = username;
			return "redirect:/";
			
		}
		model.put("errMsg", "Wrong username or password, please try again!");
		return "login";
	}
	
	
	
	
	
	@GetMapping("/showNewUserForm")
	public String showNewUserForm(Model model) {
		// create model attribute to bind form data
		User user = new User();
		model.addAttribute("user", user);
		return "registration";
	}
	
	
	
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") User user,@RequestParam String username) {
		// save employee to database
		uname = username;
		userService.saveUser(user);
		return "redirect:/";
	}
	
}
