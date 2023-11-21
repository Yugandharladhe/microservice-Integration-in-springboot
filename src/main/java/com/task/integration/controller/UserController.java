package com.task.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.integration.dto.AccessToken;
import com.task.integration.dto.Customer;
import com.task.integration.dto.LoginCredentials;
import com.task.integration.services.UserService;

@RestController
public class UserController {

	@Autowired
	UserService service;
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody LoginCredentials credentials)
	{
		
		AccessToken token=service.login(credentials);
		return ResponseEntity.ok(token);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer,@RequestHeader("authorization") String authorization)
	{
		
		String msg=service.createCustomer(customer,authorization);
		return ResponseEntity.ok(msg);
	}
	
	@GetMapping("/get_all_customers")
	public ResponseEntity<?> createCustomer(@RequestHeader("authorization") String authorization)
	{
		
		Customer[] data=service.getCustomers(authorization);
		return ResponseEntity.ok(data);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> createCustomer(@RequestParam("uuid") String uuid,@RequestParam("cmd") String cmd,@RequestHeader("authorization") String authorization)
	{
		String msg=service.deleteCustomer(authorization, cmd, uuid);
		return ResponseEntity.ok(msg);
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer,@RequestParam("uuid") String uuid,@RequestParam("cmd") String cmd,@RequestHeader("authorization") String authorization)
	{
		String msg=service.updateCustomer(authorization, cmd, uuid,customer);
		return ResponseEntity.ok(msg);
	}
}
