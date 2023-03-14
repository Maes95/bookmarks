package dev.maes.bookmarks.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.maes.bookmarks.configurations.exceptions.EntityNotFoundException;
import dev.maes.bookmarks.configurations.exceptions.ErrorProcessingException;
import dev.maes.bookmarks.configurations.exceptions.UnsavedEntityException;
import dev.maes.bookmarks.entities.User;
import dev.maes.bookmarks.security.session.LoggedUser;
import dev.maes.bookmarks.security.session.UserSession;
import dev.maes.bookmarks.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	UserService userService;

	@Autowired
	UserSession userSession;


	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") int userId) throws ErrorProcessingException, EntityNotFoundException {
		User user = userService.findById(userId);
		return ResponseEntity.ok(user);
	}

	@LoggedUser
	@GetMapping("/me")
	public ResponseEntity<User> me() throws ErrorProcessingException, EntityNotFoundException {
		User user = userSession.getLoggedUser();
		return ResponseEntity.ok(user);
	}

	@GetMapping("/")
	public ResponseEntity<List<User>> findAll() throws ErrorProcessingException {
		List<User> users = userService.findAll();
		return ResponseEntity.ok(users);
	}

	@PutMapping
	public ResponseEntity<User> update(@RequestBody User user) throws UnsavedEntityException {
		User userUpdated = userService.update(user);
		return ResponseEntity.ok(userUpdated);
	}
}