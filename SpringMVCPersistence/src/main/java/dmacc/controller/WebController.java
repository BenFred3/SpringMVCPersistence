package dmacc.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import dmacc.beans.Menu;
import dmacc.repository.MenuRepository;

@Controller
public class WebController 
{
	@Autowired
	MenuRepository repo;
	
	@GetMapping("/viewAll")
	public String viewAllMenus(Model model) 
	{
		model.addAttribute("menus", repo.findAll());
		return "results";
	}
	
	
	@GetMapping("/inputMenu")
	public String addNewMenu(Model model) 
	{
	    Menu m = new Menu();
	    model.addAttribute("newMenu", m);
	    return "input";
	}
	
	@PostMapping("/inputMenu")
	public String addNewMenu(@ModelAttribute Menu m, Model model)
	{
		repo.save(m);
		model.addAttribute("menus", repo.findAll());
		return "results";
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model) 
	{
	    Menu m = repo.findById((long) id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    model.addAttribute("menu", m);
	    return "update";
	}

	
	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") int id, @Valid Menu m, 
	  BindingResult result, Model model) 
	{
	    if (result.hasErrors()) 
	    {
	        m.setId(id);
	        return "update";
	    }
	         
	    repo.save(m);
	    model.addAttribute("menus", repo.findAll());
		return "results";
	}
	     
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") int id, Model model)
	{
	    Menu m = repo.findById((long) id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    repo.delete(m);
	    model.addAttribute("menus", repo.findAll());
		return "results";
	}
	
}