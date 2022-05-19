package dev.maes.bookmarks.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dev.maes.bookmarks.entities.Link;

@Repository
public interface LinkRepository extends PagingAndSortingRepository<Link, Integer> {

}
