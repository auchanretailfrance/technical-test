package com.auchan.technicaltest.controller;

import com.auchan.technicaltest.dto.AuthorDTO;
import com.auchan.technicaltest.facade.AuthorFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorFacade authorFacade;

    @Autowired
    public AuthorController(AuthorFacade authorFacade) {
        this.authorFacade = authorFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorFacade.createAuthor(authorDTO);
    }

    @GetMapping
    public Flux<AuthorDTO> getAllAuthors() {
        return authorFacade.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Mono<AuthorDTO> getAuthorById(@PathVariable String id) {
        return authorFacade.getAuthorById(id);
    }
}
