package com.auchan.technicaltest.facade;

import com.auchan.technicaltest.dto.BookCreationDTO;
import com.auchan.technicaltest.dto.BookDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookFacade {
    Mono<BookDTO> createBook(BookCreationDTO bookCreationDTO);
    Flux<BookDTO> getAllBooks();
    Flux<BookDTO> getBooksByCriteria(String authorName, Integer publicationYear);
}
