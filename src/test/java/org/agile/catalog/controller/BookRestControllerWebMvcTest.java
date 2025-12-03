package org.agile.catalog.controller;

import org.agile.catalog.data.Book;
import org.agile.catalog.data.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
class BookRestControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookRepository bookRepository;

    @Test
    void searchBooks_shouldReturnListOfBooks() throws Exception {
        // 1. Arrange
        Book mockBook = new Book("999-99", "Mocked Title", "Desc", "Mock Author");

        // Uses Mockito to define behavior: whatever list comes in, return the mockBook
        given(bookRepository.searchByKeywords(anyList())).willReturn(List.of(mockBook));

        // 2. Act & Assert
        // This part simulates: GET /api/books/search?keywords=java,spring
        mockMvc.perform(get("/api/books/search")
                        .param("keywords", "java", "spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Mocked Title")))
                .andExpect(jsonPath("$[0].isbn", is("999-99")));
    }
}