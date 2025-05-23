package com.auchan.technicaltest.service;

import com.auchan.technicaltest.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<Book> createBook(Book book);
    Flux<Book> getAllBooks();
    Flux<Book> getBooksByAuthorName(String authorName);
    Flux<Book> getBooksByPublicationYear(int publicationYear);
    Flux<Book> getBooksByAuthorNameAndPublicationYear(String authorName, int publicationYear);
}
