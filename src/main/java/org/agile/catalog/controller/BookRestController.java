package org.agile.catalog.controller;

import org.agile.catalog.data.Book;
import org.agile.catalog.data.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/books/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // (Optional) endpoint to get all books
    @GetMapping("/api/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
