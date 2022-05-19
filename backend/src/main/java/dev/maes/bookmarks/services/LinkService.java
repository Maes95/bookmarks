package dev.maes.bookmarks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.maes.bookmarks.configurations.exceptions.EntityNotFoundException;
import dev.maes.bookmarks.configurations.exceptions.ErrorProcessingException;
import dev.maes.bookmarks.configurations.exceptions.UnsavedEntityException;
import dev.maes.bookmarks.entities.Link;
import dev.maes.bookmarks.repositories.LinkRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LinkService{

  @Autowired
  private LinkRepository linkRepository;
  

  public Link findById(final int linkId) throws ErrorProcessingException, EntityNotFoundException {
    try {
      // TODO: Add message EntityNotFoundException
      return this.linkRepository.findById(linkId).orElseThrow(() -> new EntityNotFoundException());
    } catch (final EntityNotFoundException e) {
      throw e;
    } catch (final Exception e) {
      log.error("Link findById(\"{}\"): {}", linkId, e.getMessage());
      throw new ErrorProcessingException(e.getMessage());
    }
  }

  public Page<Link> findAll(Pageable pageable) throws ErrorProcessingException {
    try {
      return this.linkRepository.findAll(pageable);
    } catch (final Exception e) {
      log.error("Link findAll(): {}", e.getMessage());
      throw new ErrorProcessingException(e.getMessage());
    }
  }

  public Link save(final Link link) throws UnsavedEntityException {
    try {
      return this.linkRepository.save(link);
    } catch (final Exception e) {
      log.error("Link save(\"{}\"): {}", link.toString(), e.getMessage());
      throw new UnsavedEntityException(e.getMessage());
    }
  }

  public Link update(final Link link) throws UnsavedEntityException {
    try {
      return this.linkRepository.save(link);
    } catch (final Exception e) {
      log.error("Link update(\"{}\"): {}", link.toString(), e.getMessage());
      throw new UnsavedEntityException(e.getMessage());
    }
  }

  public void delete(final int linkId) throws ErrorProcessingException, EntityNotFoundException {
    try {
      this.linkRepository.deleteById(linkId);
    } catch (final Exception e) {
      log.error("Link update(\"{}\"): {}", linkId, e.getMessage());
      throw new UnsavedEntityException(e.getMessage());
    }
  }

}
 
