package com.bookmyshow.bookmyshowapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.bookmyshowapi.exceptions.UserAlreadyPresentException;
import com.bookmyshow.bookmyshowapi.exceptions.UserNotFoundException;
import com.bookmyshow.bookmyshowapi.models.User;
import com.bookmyshow.bookmyshowapi.repositories.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/registerUser")
public class RegisterUserControl {
	
	@Autowired
	private UserRepository userRepository;	
	
	@PostMapping("/addUser")
	public String addUser(@RequestBody User user) {
		Optional<User> userOptional = userRepository.findByMobile(user.getMobile());
		log.info("Request to add user with mobile# " + user.getMobile());
		if(userOptional.isPresent()) {
			log.info("User already registered with mobile# " + user.getMobile());
			throw new UserAlreadyPresentException("User already present with mobile# " + user.getMobile());
		}
		User userObject = User.builder().
				name(user.getName()).
				mobile(user.getMobile()).
				build();
		userRepository.save(userObject);
		return "user registered with id# " + userObject.getId();
	}
	
	@GetMapping("/getUser/{mobile}")
	public User getUser(@PathVariable(name = "mobile") String mobile) {
		Optional<User> userOptional = userRepository.findByMobile(mobile);
		log.info("Request to get user with mobile# " + mobile);
		if(!userOptional.isPresent()) {
			log.info("user not found");
			throw new UserNotFoundException("user with mobile# " + mobile + " not registered");
		}
		return userOptional.get();
	}
}
