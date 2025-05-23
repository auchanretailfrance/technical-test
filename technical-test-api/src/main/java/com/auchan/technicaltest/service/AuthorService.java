package com.auchan.technicaltest.service;

import com.auchan.technicaltest.model.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<Author> createAuthor(Author author);
    Flux<Author> getAllAuthors();
    Mono<Author> getAuthorById(String id);
}
