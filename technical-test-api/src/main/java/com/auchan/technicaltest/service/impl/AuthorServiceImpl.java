package com.auchan.technicaltest.service.impl;

import com.auchan.technicaltest.model.Author;
import com.auchan.technicaltest.repository.AuthorRepository;
import com.auchan.technicaltest.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Mono<Author> createAuthor(Author author) {
        // Find by name, if not present, save the new author
        return authorRepository.findByName(author.getName())
                .switchIfEmpty(authorRepository.save(author));
    }

    @Override
    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }
}
