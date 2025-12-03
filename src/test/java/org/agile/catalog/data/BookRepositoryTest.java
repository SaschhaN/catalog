package org.agile.catalog.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void searchByKeywords_shouldFindBook_whenAllKeywordsMatch_CaseInsensitive() {
        //Creates specific scenarios in the DB

        // Match: Contains 'Java' (title) and 'Beginner' (description)
        Book match = new Book("978-1", "Learn Java", "Best for beginners", "Alice");
        entityManager.persist(match);

        // No Match: Contains 'Java' but NOT 'Beginner'
        Book partialMatch = new Book("978-2", "Java Advanced", "For pros", "Bob");
        entityManager.persist(partialMatch);

        // No Match: Contains neither
        Book noMatch = new Book("978-3", "Python Basics", "Snake coding", "Charlie");
        entityManager.persist(noMatch);

        entityManager.flush();

        List<Book> results = bookRepository.searchByKeywords(List.of("JAVA", "beginner"));

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("Learn Java");
    }

    @Test
    void searchByKeywords_shouldReturnEmpty_whenInputIsNull() {
        List<Book> results = bookRepository.searchByKeywords(null);
        assertThat(results).isEmpty();
    }

    @Test
    void searchByKeywords_shouldSearchInAuthorField() {
        Book authorMatch = new Book("978-4", "Coding 101", "General stuff", "Martin Fowler");
        entityManager.persist(authorMatch);

        List<Book> results = bookRepository.searchByKeywords(List.of("Fowler"));

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getAuthor()).isEqualTo("Martin Fowler");
    }
}