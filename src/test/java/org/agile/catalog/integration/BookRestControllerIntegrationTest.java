package org.agile.catalog.integration;

import org.agile.catalog.data.Book;
import org.agile.catalog.data.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setupData() {
        // Pre-populate DB
        bookRepository.save(new Book("111", "Spring Boot in Action", "Great book", "Walls"));
        bookRepository.save(new Book("222", "Hibernate", "ORM magic", "Bauer"));
    }

    @AfterEach
    void cleanData() {
        bookRepository.deleteAll();
    }

    @Test
    void testSearchEndpoint_EndToEnd() {
        String url = "http://localhost:" + port + "/api/books/search?keywords=Action";

        ResponseEntity<List<Book>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getTitle()).isEqualTo("Spring Boot in Action");
    }
}