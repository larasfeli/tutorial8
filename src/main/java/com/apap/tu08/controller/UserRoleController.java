package com.apap.tu08.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tu08.model.UserRoleModel;
import com.apap.tu08.service.UserRoleService;

@Controller
@RequestMapping("user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	private boolean info = false;
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.adduser(user);
		System.out.println("page add");
		return "home";
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
    private String update(@ModelAttribute UserRoleModel user, Model model) {
		model.addAttribute("error", false);
        return "update-user";
    }
	
	@RequestMapping(value = "/updatePasswordOk", method = RequestMethod.POST)
	private String updatePassSubmit(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "passwordLama") String passwordLama,
			@RequestParam(value = "passwordBaru") String passwordBaru,
			@RequestParam(value = "passwordBaruKonfirm") String passwordBaruKonfirm,
			@ModelAttribute UserRoleModel user,
			Model model) {
		user = userService.findUserByUsername(this.getPrincipal());		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(this.validasiPassword(passwordBaru) == true && this.validasiPassword(passwordBaruKonfirm) == true) {
			if(encoder.matches(passwordLama, user.getPassword())) {
				if(passwordBaru.equals(passwordBaruKonfirm)) {
					user.setPassword(encoder.encode(passwordBaru));
					userService.adduser(user);
					return "home";
				}
			}
		}
		info = true;
		model.addAttribute("error", true);
		return "update-user";
	}
	
	public boolean validasiPassword(String pass) {
	      String pattern = "(?=.*[0-9])(?=\\S+$).{8,}";
	      if(pass.matches(pattern)) {
	    	  return true;
	      }else {
	    	  return false;
	      }
	}
	
	private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
	

}
