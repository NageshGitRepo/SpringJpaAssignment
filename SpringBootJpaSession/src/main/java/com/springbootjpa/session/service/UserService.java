package com.springbootjpa.session.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springbootjpa.session.exception.ResourceNotFoundException;
import com.springbootjpa.session.model.User;
import com.springbootjpa.session.repository.UserRepository;

@Service
public class UserService
{
	@Autowired
    UserRepository userRepo;
	
	public User saveUserDetails(User user)
	{
	  return userRepo.save(user);	
	}
	
	public ResponseEntity<User> getUserDetailsById(int userId)  
	{
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));		
	    return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	public List<User> getUserDetailsOrderByName()
	{
		return userRepo.findAllByOrderByNameDesc();
	}
	public String deleteUserById(int userId) throws ResourceNotFoundException
	{
		 User usr = getUserDetailsById(userId).getBody();
		 userRepo.deleteById(usr.getId());
		 return "User with userId: "+ userId+" deleted successfully";
	}
	public User updateUser(int userId,User user) 
	{
		User usr = getUserDetailsById(userId).getBody();
		
		usr.setCreatedBy(user.getCreatedBy());
		usr.setCreatedOn(user.getCreatedOn());
		usr.setEmailAddress(user.getEmailAddress());
		usr.setPassword(user.getPassword());
		usr.setName(user.getName());
		
		userRepo.save(usr);
		return userRepo.getById(usr.getId());
	}
	
	public List<User> getUsersByNameStartWith(String name)
	{
		return userRepo.findByNameStartingWith(name);
	}
	public List<User> getNameStartLike(String name)
	{
		return userRepo.findByNameLike(name);
	}
	public List<User> getNameStartNotLike(String name)
	{
		return userRepo.findByNameNotLike(name);
	}
	public List<User> getNameNot(String name)
	{
		return userRepo.findByNameNot(name);
	}
	public List<User> getCreatedBetween(Date fromDate,Date toDate)
	{
		return userRepo.findByCreatedOnBetween(fromDate,toDate);
	}
	
	
}
