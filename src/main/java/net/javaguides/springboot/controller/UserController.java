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

import net.javaguides.springboot.model.Claims;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.ProductService;
import net.javaguides.springboot.service.UserService;

@Controller
public class UserController {
	String uname;
	String pname;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	
	
	
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
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "id", "asc", model);		
	}
	
	@GetMapping("/showNewProductForm")
	public String showNewProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("uname", uname);
		model.addAttribute("product", product);
		product.setUsername(uname);
		
		System.out.println(uname);
		return "new_product";
	}
	
	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute("product") Product product) {
		
		
		productService.saveProduct(product);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		Product product = productService.getProductById(id);
		
		model.addAttribute("product", product);
		return "update_product";
	}
	
	
	@GetMapping("/showNewUserForm")
	public String showNewUserForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "registration";
	}
	
	@GetMapping("/claims/{productName}")
	public String viewClaimPage(Model model,@PathVariable ( value = "productName") String productName) {
		pname = productName;
		return findPaginatedClaim(1, "product_name", "asc", model);		
	}
	
	@PostMapping("/saveClaim")
	public String saveClaim(@ModelAttribute("claim") Claims claim) {
		// save employee to database
		
		productService.saveClaim(claim);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForClaim/{productName}")
	public String showFormForClaim(@PathVariable ( value = "productName") String productName, Model model) {
		
		// get employee from the service
		Product product = productService.getProductByName(productName);
		
		// set employee as a model attribute to pre-populate the form
		Claims claim = new Claims();
		model.addAttribute("pname", productName);
		model.addAttribute("claim", claim);
		claim.setProductName(productName);
		return "doClaim";
	}
	
	@GetMapping("/pagec/{pageNo}")
	public String findPaginatedClaim(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Claims> page = productService.findPaginatedClaim(pageNo, pageSize, sortField, sortDir,pname);
		List<Claims> listClaims = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listClaims", listClaims);
		return "displayClaims";
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
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
		return "index";
	}
	
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") User user,@RequestParam String username) {
		// save employee to database
		uname = username;
		userService.saveUser(user);
		return "redirect:/";
	}
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.productService.deleteProductById(id);
		return "redirect:/";
	}
	
}
