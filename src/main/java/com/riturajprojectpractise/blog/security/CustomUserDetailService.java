package com.riturajprojectpractise.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.riturajprojectpractise.blog.entities.User;
import com.riturajprojectpractise.blog.exceptions.ResourceNotFoundException;
import com.riturajprojectpractise.blog.repositories.UserRepo;



@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","email"+username,0));
	
		// need some correction(in exception declaration) probably can add extra constructor
		
		return user;
	}

}
