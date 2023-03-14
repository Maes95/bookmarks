package dev.maes.bookmarks.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.maes.bookmarks.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
  
}
