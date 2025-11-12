package org.agile.catalog.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.save(new Book("9780132350884", "Clean Code", "A Handbook of Agile Software Craftsmanship", "Robert C. Martin"));
        bookRepository.save(new Book("9780134685991", "Effective Java", "Best practices for the Java platform", "Joshua Bloch"));
    }

    @Test
    void shouldFindBooksByKeywordInTitleOrDescriptionOrAuthor() {
        List<Book> result = bookRepository.searchBooks("java");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Effective Java");
    }

    @Test
    void shouldReturnEmptyListWhenNoMatchFound() {
        List<Book> result = bookRepository.searchBooks("python");
        assertThat(result).isEmpty();
    }
}