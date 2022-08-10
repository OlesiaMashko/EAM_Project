package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.model.Claims;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.service.ProductService;
import net.javaguides.springboot.service.UserService;

@Controller
public class ClaimController {
	String tempUsername;
	String tempProductname;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;
	
	@GetMapping("/claimsAdmin/{productName}")
	public String viewClaimPageAdmin(Model model,@PathVariable ( value = "productName") String productName) {
		tempProductname = productName;
		
		return findPaginatedClaimAdmin(1, "product_name", "asc", model);		
	}
	
	@GetMapping("/claims/{productName}")
	public String viewClaimPage(Model model,@PathVariable ( value = "productName") String productName) {
		tempProductname = productName;
		return findPaginatedClaim(1, "product_name", "asc", model);		
	}
	
	@PostMapping("/saveClaim")
	public String saveClaim(@ModelAttribute("claim") Claims claim) {

		
		productService.saveClaim(claim);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForClaim/{productName}")
	public String showFormForClaim(@PathVariable ( value = "productName") String productName, Model model) {
		
		Product product = productService.getProductByName(productName);
		Claims claim = new Claims();
		model.addAttribute("pname", productName);
		model.addAttribute("claim", claim);
		claim.setProductName(productName);
		return "doClaim";
	}
	
	
	@PostMapping("/saveClaimAdmin")
	public String saveClaimAdmin(@ModelAttribute("claim") Claims claim) {
		
		productService.saveClaim(claim);
		return "redirect:/userSpecific/" + tempUsername;
	}
	
	@GetMapping("/showFormForClaimAdmin/{productName}")
	public String showFormForClaimAdmin(@PathVariable ( value = "productName") String productName, Model model) {
		
		Product product = productService.getProductByName(productName);
		
		Claims claim = new Claims();
		model.addAttribute("pname", productName);
		model.addAttribute("claim", claim);
		model.addAttribute("uname", tempUsername);
		claim.setProductName(productName);
		return "doClaimAdmin";
	}
	
	@GetMapping("/approveClaim/{id}")
	public String approveClaim(@PathVariable (value = "id") long id) {
		
		this.productService.approveClaimById(id);
		return "redirect:/claimsAdmin/" + tempProductname;
	}
	
	@GetMapping("/rejectClaim/{id}")
	public String rejectClaim(@PathVariable (value = "id") long id) {
		 
		this.productService.rejectClaimById(id);
		return "redirect:/claimsAdmin/" + tempProductname;
	}
	
	@GetMapping("/pagecAdmin/{pageNo}")
	public String findPaginatedClaimAdmin(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Claims> page = productService.findPaginatedClaimAdmin(pageNo, pageSize, sortField, sortDir,tempProductname);
		List<Claims> listClaims = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listClaims", listClaims);
		model.addAttribute("uname", tempUsername);
		return "displayClaimsAdmin";
	}
	
	@GetMapping("/pagec/{pageNo}")
	public String findPaginatedClaim(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Claims> page = productService.findPaginatedClaim(pageNo, pageSize, sortField, sortDir,tempProductname);
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
}
