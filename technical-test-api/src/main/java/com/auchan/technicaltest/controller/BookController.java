package com.auchan.technicaltest.controller;

import com.auchan.technicaltest.dto.BookCreationDTO;
import com.auchan.technicaltest.dto.BookDTO;
import com.auchan.technicaltest.facade.BookFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookFacade bookFacade;

    @Autowired
    public BookController(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDTO> createBook(@RequestBody BookCreationDTO bookCreationDTO) {
        return bookFacade.createBook(bookCreationDTO);
    }

    @GetMapping
    public Flux<BookDTO> getBooks(
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) Integer publicationYear) {
        return bookFacade.getBooksByCriteria(authorName, publicationYear);
    }
}
