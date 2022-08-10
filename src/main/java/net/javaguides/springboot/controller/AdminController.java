package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.ProductService;
import net.javaguides.springboot.service.UserService;

@Controller
public class AdminController {
	
	String uname;
	String pname;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/adminLogin",method=RequestMethod.GET)
	public String AadminLoginPage() {
		return "adminLogin";
	}
	
	@PostMapping("/adminLogin")
	public String AdminPage(ModelMap model,Model model1, @RequestParam String username, @RequestParam String password) {
		
		if(userService.getAdmin(username, password)) {
			model.put("username",username);
			System.out.println(username);
			//uname = username;
			return "redirect:/users";
			
		}
		model.put("errMsg", "Bad Credential");
		return "adminLogin";
	}
	
	@GetMapping("/users")
	public String viewUsers(Model model) {
		
		return findPaginatedUser(1, "id", "asc", model);		
	}
	
	@GetMapping("/userSpecific/{username}")
	public String viewUserProducts(Model model,@PathVariable ( value = "username") String username) {
		uname = username;
		return findPaginatedAdmin(1, "id", "asc", model);		
	}
	
	@GetMapping("/pageu/{pageNo}")
	public String findPaginatedUser(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<User> page = productService.findPaginatedUser(pageNo, pageSize, sortField, sortDir);
		List<User> listUsers = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listUsers", listUsers);
		return "displayUsers";
	}
	
	@GetMapping("/pageAdmin/{pageNo}")
	public String findPaginatedAdmin(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir,uname);
		List<Product> listProducts = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listProducts", listProducts);
		return "productListAdmin";
	}
}
