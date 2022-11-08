package tn.esprit.spring.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.dao.entities.Role;
import tn.esprit.spring.dao.entities.User;
import tn.esprit.spring.services.inters.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	 @Autowired
	  IUserService userService;
	 
	 @GetMapping("getAllUsers") 
		public List<User> getAllUsers() 
		{
			return userService.showAllUsers();
		} 
		
	@GetMapping("getUser/{username}")
		public Optional<User> getUser(@PathVariable("username")String username)
		{
		return userService.findByUsername(username);
		}


		
	@GetMapping("/getusersbyrole/{roles}")
		public List<User> getUsersByRole(@PathVariable("roles") Set<Role> roles)
		{
			return userService.showUsersByRole(roles);
		}
	 
	@PutMapping("/modify-name/{username}")
	   public String modifyName(@PathVariable("username")String username, @RequestParam String name) 
	   {
		return userService.modifyName(username, name);
       }
	
	@PutMapping("/modify-email/{username}")
	   public String modifyEmail(@PathVariable("username")String username, @RequestParam String email) 
	   {
		return userService.modifyEmail(username, email);
       }
	
	@DeleteMapping("deleteUser/{idUser}") 
	public String DeleteUser(@PathVariable("idUser")Long idUser )
	{
		return userService.deleteUser(idUser);
	}

}
