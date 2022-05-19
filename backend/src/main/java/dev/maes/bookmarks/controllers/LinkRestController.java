package dev.maes.bookmarks.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.maes.bookmarks.configurations.exceptions.EntityNotFoundException;
import dev.maes.bookmarks.configurations.exceptions.ErrorProcessingException;
import dev.maes.bookmarks.configurations.exceptions.UnsavedEntityException;
import dev.maes.bookmarks.entities.Link;
import dev.maes.bookmarks.services.LinkService;

@RestController
@RequestMapping("/api/links")
public class LinkRestController {

	@Autowired
	LinkService linkService;


	@GetMapping("/{id}")
	public ResponseEntity<Link> findById(@PathVariable("id") int linkId) throws ErrorProcessingException, EntityNotFoundException {
		Link salida = linkService.findById(linkId);
		return new ResponseEntity<Link>(salida, HttpStatus.OK);
	}

	@GetMapping("/")
		public ResponseEntity<Page<Link>> findAll(Pageable pageable) throws ErrorProcessingException {
			Page<Link> salida = linkService.findAll(pageable);
			return new ResponseEntity<Page<Link>>(salida, HttpStatus.OK);
		}

	@PostMapping
		public ResponseEntity<Link> save(@Valid @RequestBody Link link) throws UnsavedEntityException {
			Link salida = linkService.save(link);
			return new ResponseEntity<Link>(salida, HttpStatus.CREATED);
		}

	@PutMapping
		public ResponseEntity<Link> update(@RequestBody Link link) throws UnsavedEntityException {
			Link salida = linkService.update(link);
			return new ResponseEntity<Link>(salida, HttpStatus.OK);
		}

	@DeleteMapping("/{id}")
		public ResponseEntity<Link> delete(@PathVariable("id") int linkId) throws ErrorProcessingException, EntityNotFoundException {
			linkService.delete(linkId);
			return new ResponseEntity<Link>(HttpStatus.NO_CONTENT);
		}
}