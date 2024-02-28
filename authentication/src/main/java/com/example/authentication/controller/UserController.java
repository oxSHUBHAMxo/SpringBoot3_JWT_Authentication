package com.example.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authentication.dto.AuthRequest;
import com.example.authentication.entity.UserInfo;
import com.example.authentication.service.JwtService;
import com.example.authentication.service.UserInfoService;
  
@RestController
@RequestMapping("/auth") 
public class UserController { 
  
    @Autowired
    private UserInfoService service; 
  
    @Autowired
    private JwtService jwtService;
    
	@Autowired
	private PasswordEncoder encoder; 
  
    @Autowired
    private AuthenticationManager authenticationManager; 
  
    @PostMapping("/addNewUser") 
    public String addNewUser(@RequestBody UserInfo userInfo) 
    { 
    	userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
        return service.addUser(userInfo); 
    } 
  
    @GetMapping("/user/userProfile") 
    @PreAuthorize("hasAuthority('ADMIN')") 
    public String userProfile() { 
        return "Welcome to User Profile"; 
    } 
  
    @GetMapping("/admin/adminProfile") 
    @PreAuthorize("hasAuthority('ADMIN')") 
    public String adminProfile() { 
        return "Welcome to Admin Profile"; 
    } 
  
    @PostMapping("/generateToken") 
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) 
    { 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
        if (authentication.isAuthenticated()) 
        { 
            return jwtService.generateToken(authRequest.getUsername()); 
        } 
        else 
        { 
            throw new UsernameNotFoundException("invalid user request !"); 
        } 
    } 
  
}