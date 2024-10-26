package com.deep.library.repositories;

import com.deep.library.domains.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, String> {
    boolean existsByTitle(String title);
}
