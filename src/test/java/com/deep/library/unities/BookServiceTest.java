package com.deep.library.unities;

import com.deep.library.Constants;
import com.deep.library.domains.dto.BookResponse;
import com.deep.library.domains.entities.BookEntity;
import com.deep.library.domains.mappers.BookMapper;
import com.deep.library.exceptions.TitleConflictException;
import com.deep.library.repositories.BookRepository;
import com.deep.library.services.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.deep.library.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void shouldCreateBook_shouldReturnBookResponse_whenTitleIsUnique() {
        // Arrange
        when(bookRepository.existsByTitle(UNIQUE_TITLE)).thenReturn(false);
        when(bookRepository.save(any(BookEntity.class))).thenReturn(BOOK_ENTITY);
        when(bookMapper.entityToResponse(any(BookEntity.class))).thenReturn(BOOK_RESPONSE);

        // Act
        BookResponse response = bookService.createBook(BOOK_REQUEST);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.title()).isEqualTo(Constants.UNIQUE_TITLE);
        assertThat(response.description()).isEqualTo(Constants.DESCRIPTION);
    }

    @Test
    void createBook_shouldThrowTitleConflictException_whenTitleIsNotUnique() {
        // Arrange
        when(bookRepository.existsByTitle(UNIQUE_TITLE)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> bookService.createBook(BOOK_REQUEST))
                .isInstanceOf(TitleConflictException.class)
                .hasMessageContaining("The title <<" + UNIQUE_TITLE + ">> already exists");
    }
}