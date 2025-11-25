package org.agile.catalog.controller;

import org.agile.catalog.data.Book;
import org.agile.catalog.data.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookRestController {

    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // GET /api/books/search?keyword=java
    @GetMapping("/api/books/search")
    public List<Book> searchBooks(@RequestParam List<String> keywords) {
        return bookRepository.searchByKeywords(keywords);
    }

    // (Optional) endpoint to get all books
    @GetMapping("/api/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
