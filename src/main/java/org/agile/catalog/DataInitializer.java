package org.agile.catalog;

import org.agile.catalog.data.Book;
import org.agile.catalog.data.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final BookRepository repository;

    public DataInitializer(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        saveIfMissing("9780132350884", "Clean Code", "A Handbook of Agile Software Craftsmanship", "Robert C. Martin");
        saveIfMissing("9780134685991", "Effective Java", "Best practices for the Java platform", "Joshua Bloch");
        saveIfMissing("9780596007126", "Head First Design Patterns", "A Brain-Friendly Guide", "Eric Freeman");
    }

    private void saveIfMissing(String isbn, String title, String description, String author) {
        if (!repository.existsByIsbn(isbn)) {
            repository.save(new Book(isbn, title, description, author));
            log.info("Buch importiert: {} (ISBN: {})", title, isbn);
        } else {
            log.debug("Buch existiert bereits: {} - Überspringe Import.", title);
        }
    }
}