package com.auchan.technicaltest.service.impl;

import com.auchan.technicaltest.model.Author;
import com.auchan.technicaltest.model.Book;
import com.auchan.technicaltest.repository.AuthorRepository;
import com.auchan.technicaltest.repository.BookRepository;
import com.auchan.technicaltest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Mono<Book> createBook(Book book) {
        // Assumes book.getAuthor() is already a managed entity (either new or existing).
        // The author should be handled (retrieved or created) by the caller (e.g., Facade)
        // before this method is called.
        // This service method's primary responsibility is to save the book.
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            // This indicates an issue: the author should have been persisted and have an ID,
            // or it's a new Author object that AuthorService should have handled.
            // For robustness, one might re-trigger author save here, but ideally, it's pre-handled.
            return Mono.error(new IllegalArgumentException("Author must be persisted and have an ID before saving a book, or be a new author handled by AuthorService."));
        }
        return bookRepository.save(book);
    }

    @Override
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<Book> getBooksByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    @Override
    public Flux<Book> getBooksByPublicationYear(int publicationYear) {
        return bookRepository.findByPublicationYear(publicationYear);
    }

    @Override
    public Flux<Book> getBooksByAuthorNameAndPublicationYear(String authorName, int publicationYear) {
        return bookRepository.findByAuthorNameAndPublicationYear(authorName, publicationYear);
    }
}
