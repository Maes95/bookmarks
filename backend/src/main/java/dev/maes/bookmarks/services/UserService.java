package dev.maes.bookmarks.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.maes.bookmarks.configurations.exceptions.EntityNotFoundException;
import dev.maes.bookmarks.configurations.exceptions.ErrorProcessingException;
import dev.maes.bookmarks.configurations.exceptions.UnsavedEntityException;
import dev.maes.bookmarks.entities.User;
import dev.maes.bookmarks.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService{

  @Autowired
  private UserRepository userRepository;
  

  public User findById(final int userId) throws ErrorProcessingException, EntityNotFoundException {
    try {
      // TODO: Add message EntityNotFoundException
      return this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
    } catch (final EntityNotFoundException e) {
      throw e;
    } catch (final Exception e) {
      log.error("User findById(\"{}\"): {}", userId, e.getMessage());
      throw new ErrorProcessingException(e.getMessage());
    }
  }

  public List<User> findAll() throws ErrorProcessingException {
    try {
      return this.userRepository.findAll();
    } catch (final Exception e) {
      log.error("User findAll(): {}", e.getMessage());
      throw new ErrorProcessingException(e.getMessage());
    }
  }

  public User save(final User user) throws UnsavedEntityException {
    try {
      return this.userRepository.save(user);
    } catch (final Exception e) {
      log.error("User save(\"{}\"): {}", user.toString(), e.getMessage());
      throw new UnsavedEntityException(e.getMessage());
    }
  }

  public User update(final User user) throws UnsavedEntityException {
    try {
      return this.userRepository.save(user);
    } catch (final Exception e) {
      log.error("User update(\"{}\"): {}", user.toString(), e.getMessage());
      throw new UnsavedEntityException(e.getMessage());
    }
  }
}
  
