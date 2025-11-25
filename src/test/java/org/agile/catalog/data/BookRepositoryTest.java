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
        bookRepository.deleteAll();
        bookRepository.save(new Book("9780132350884", "Clean Code", "A Handbook of Agile Software Craftsmanship", "Robert C. Martin"));
        bookRepository.save(new Book("9780134685991", "Effective Java", "Best practices for the Java platform", "Joshua Bloch"));
        bookRepository.save(new Book("9780596007126", "Head First Design Patterns", "A brain-friendly guide", "Eric Freeman"));
    }

    @Test
    void searchByKeywords_allKeywordsMustMatch_someMatch() {
        // "java" appears in title of Effective Java, "platform" appears in description -> should match (AND)
        List<Book> results = bookRepository.searchByKeywords(List.of("java", "platform"));
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("Effective Java");
    }

    @Test
    void searchByKeywords_allKeywordsMustMatch_noneMatch() {
        // "java" matches Effective Java, but "easy" is not in any field -> no results expected
        List<Book> results = bookRepository.searchByKeywords(List.of("java", "easy"));
        assertThat(results).isEmpty();
    }
}