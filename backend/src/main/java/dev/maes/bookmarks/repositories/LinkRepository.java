package dev.maes.bookmarks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.maes.bookmarks.entities.Link;

public interface LinkRepository extends JpaRepository<Link, Integer> {

}
