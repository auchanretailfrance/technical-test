package com.auchan.technicaltest.facade;

import com.auchan.technicaltest.dto.AuthorDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorFacade {
    Mono<AuthorDTO> createAuthor(AuthorDTO authorDTO);
    Flux<AuthorDTO> getAllAuthors();
    Mono<AuthorDTO> getAuthorById(String id);
}
