package com.auchan.technicaltest.facade.impl;

import com.auchan.technicaltest.dto.AuthorDTO;
import com.auchan.technicaltest.facade.AuthorFacade;
import com.auchan.technicaltest.mapper.AuthorMapper;
import com.auchan.technicaltest.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AuthorFacadeImpl implements AuthorFacade {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorFacadeImpl(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @Override
    public Mono<AuthorDTO> createAuthor(AuthorDTO authorDTO) {
        return authorService.createAuthor(authorMapper.toEntity(authorDTO))
                .map(authorMapper::toDTO);
    }

    @Override
    public Flux<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors()
                .map(authorMapper::toDTO);
    }

    @Override
    public Mono<AuthorDTO> getAuthorById(String id) {
        return authorService.getAuthorById(id)
                .map(authorMapper::toDTO);
    }
}
