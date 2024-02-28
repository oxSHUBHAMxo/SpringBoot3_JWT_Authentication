package com.example.authentication.service;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

import com.example.authentication.configuration.UserInfoDetails;
import com.example.authentication.entity.UserInfo;
import com.example.authentication.repository.UserInfoRepository;

import java.util.Optional; 

@Service
public class UserInfoService implements UserDetailsService { 

	@Autowired
	private UserInfoRepository repository; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{ 
		Optional<UserInfo> userDetail = repository.findByName(username); 
		return userDetail.map(UserInfoDetails::new) 
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
	} 

	public String addUser(UserInfo userInfo) 
	{
		repository.save(userInfo); 
		return "User Added Successfully"; 
	} 


} 

