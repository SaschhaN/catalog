package org.agile.catalog.integration;

import org.agile.catalog.data.Book;
import org.agile.catalog.data.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookRestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        repository.save(new Book("9780132350884", "Clean Code", "A Handbook of Agile Software Craftsmanship", "Robert C. Martin"));
        repository.save(new Book("9780134685991", "Effective Java", "Best practices", "Joshua Bloch"));
    }

    @Test
    void shouldReturnBooksFromSearchEndpoint() {
        String url = "http://localhost:" + port + "/api/books/search?keywords=java";

        ResponseEntity<Book[]> response = restTemplate.getForEntity(url, Book[].class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getTitle()).isEqualTo("Effective Java");
    }
}
