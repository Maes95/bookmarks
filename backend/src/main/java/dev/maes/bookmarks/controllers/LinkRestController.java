package dev.maes.bookmarks.controllers;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/links")
public class LinkRestController {

	@Autowired
	LinkService linkService;


	@GetMapping("/{id}")
	public ResponseEntity<Link> findById(@PathVariable("id") int linkId) throws ErrorProcessingException, EntityNotFoundException {
		Link link = linkService.findById(linkId);
		return ResponseEntity.ok(link);
	}

	@GetMapping("/")
	public ResponseEntity<Page<Link>> findAll(Pageable pageable) throws ErrorProcessingException {
		Page<Link> links = linkService.findAll(pageable);
		return ResponseEntity.ok(links);
	}

	@PostMapping("/")
	public ResponseEntity<Link> save(@Valid @RequestBody Link link) throws UnsavedEntityException {
		Link createdLink = linkService.save(link);
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(createdLink.getId()).toUri();
		return ResponseEntity.created(location).body(createdLink);
	}

	@PutMapping("/")
	public ResponseEntity<Link> update(@RequestBody Link link) throws UnsavedEntityException {
		Link updatedLink = linkService.update(link);
		return ResponseEntity.ok(updatedLink);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Link> delete(@PathVariable("id") int linkId) throws ErrorProcessingException, EntityNotFoundException {
		linkService.delete(linkId);
		return ResponseEntity.noContent().build();
	}
}