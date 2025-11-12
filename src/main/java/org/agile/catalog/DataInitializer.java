package org.agile.catalog;

import org.agile.catalog.data.Book;
import org.agile.catalog.data.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository repository;

    public DataInitializer(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        repository.save(new Book("9780132350884", "Clean Code", "A Handbook of Agile Software Craftsmanship", "Robert C. Martin"));
        repository.save(new Book("9780134685991", "Effective Java", "Best practices for the Java platform", "Joshua Bloch"));
        repository.save(new Book("9780596007126", "Head First Design Patterns", "A Brain-Friendly Guide", "Eric Freeman"));
    }
}
