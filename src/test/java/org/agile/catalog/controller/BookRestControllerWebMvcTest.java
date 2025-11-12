package org.agile.catalog.controller;

import org.agile.catalog.data.Book;
import org.agile.catalog.data.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@WebMvcTest(BookRestController.class)
class BookRestControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void shouldReturnBooksFromSearchEndpoint() throws Exception {
        List<Book> books = List.of(
                new Book("9780134685991", "Effective Java", "Best practices", "Joshua Bloch")
        );

        // Mock repository behavior
        Mockito.when(bookRepository.searchBooks("java")).thenReturn(books);

        mockMvc.perform(get("/api/books/search")
                        .param("keyword", "java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Effective Java")))
                .andExpect(jsonPath("$[0].author", is("Joshua Bloch")));
    }
}
