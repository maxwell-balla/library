package com.deep.library.repositories;

import com.deep.library.domains.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    boolean existsByTitle(String title);
    Optional<BookEntity> findByTitle(String title);
}
