package com.task.integration.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.task.integration.dto.AccessToken;
import com.task.integration.dto.Customer;
import com.task.integration.dto.LoginCredentials;

@Service
public class UserService {

	@Autowired
	RestTemplate restTemplate;



	public AccessToken login(LoginCredentials credentials)
	{
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		this.restTemplate.setMessageConverters(messageConverters);
		
		
		HttpHeaders headers=new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginCredentials> entity=new HttpEntity<>(credentials,headers);
		
		String url="https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
		ResponseEntity<AccessToken> response=restTemplate.exchange(url,HttpMethod.POST,entity,AccessToken.class);
		System.out.println(response.getBody());
		return response.getBody();
		
	}

	public String createCustomer(Customer customer,String token)
	{
//		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//		messageConverters.add(converter);
//		this.restTemplate.setMessageConverters(messageConverters);
		
		HttpHeaders headers=new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", "Bearer "+token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Customer> entity=new HttpEntity<>(customer,headers);
		
		String url="https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";
		ResponseEntity<String> response=restTemplate.exchange(url,HttpMethod.POST,entity,String.class);
		System.out.println(response.getBody().stripLeading().stripTrailing());
		return response.getBody().stripLeading().stripTrailing();	
	}
	
	
	public Customer[] getCustomers(String token)
	{
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		this.restTemplate.setMessageConverters(messageConverters);
		
		HttpHeaders headers=new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", "Bearer "+token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Customer> entity=new HttpEntity<>(headers);
		
		String url="https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
		
		ResponseEntity<Customer[]> response=restTemplate.exchange(url,HttpMethod.GET,entity,Customer [].class);
		System.out.println(response.getBody());
		return response.getBody();
	}

	public String deleteCustomer(String token,String cmd,String uuid)
	{
		HttpHeaders headers=new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.ALL));
		headers.set("Authorization", "Bearer "+token);
		HttpEntity<Customer> entity=new HttpEntity<>(headers);
		
		String url="https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?";
		StringBuilder builder =new StringBuilder(url);
		builder.append("cmd="+cmd+"&uuid="+uuid);
		url=builder.toString();
		System.out.println(url);
		
		ResponseEntity<String> response=restTemplate.exchange(url,HttpMethod.POST,entity,String.class);
		System.out.println(response.getBody().stripLeading().stripTrailing());
		return response.getBody().stripLeading().stripTrailing();
	}
	
	public String updateCustomer(String token,String cmd,String uuid,Customer customer)
	{
		HttpHeaders headers=new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Customer> entity=new HttpEntity<>(customer,headers);
		
		String url="https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?";
		StringBuilder builder =new StringBuilder(url);
		builder.append("cmd="+cmd+"&uuid="+uuid);
		url=builder.toString();
		System.out.println(url);
		System.out.println(customer.toString());
		
		ResponseEntity<String> response=restTemplate.exchange(url,HttpMethod.POST,entity,String.class);
		System.out.println(response.getBody().stripLeading().stripTrailing());
		return response.getBody().stripLeading().stripTrailing();
	}
	
}
