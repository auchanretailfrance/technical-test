package com.auchan.technicaltest.facade.impl;

import com.auchan.technicaltest.dto.BookCreationDTO;
import com.auchan.technicaltest.dto.BookDTO;
import com.auchan.technicaltest.facade.BookFacade;
import com.auchan.technicaltest.mapper.BookMapper;
import com.auchan.technicaltest.model.Author;
import com.auchan.technicaltest.model.Book;
import com.auchan.technicaltest.service.AuthorService;
import com.auchan.technicaltest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class BookFacadeImpl implements BookFacade {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookMapper bookMapper;

    @Autowired
    public BookFacadeImpl(BookService bookService, AuthorService authorService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookMapper = bookMapper;
    }

    @Override
    public Mono<BookDTO> createBook(BookCreationDTO bookCreationDTO) {
        Book book = bookMapper.toEntity(bookCreationDTO);
        // Author handling: Find by name or create a new one.
        // This logic aligns with the simplified approach in BookServiceImpl but is now more explicit in the facade.
        return authorService.createAuthor(new Author(bookCreationDTO.authorName())) // Leverages AuthorService's create logic (which should handle existing authors)
                .flatMap(author -> {
                    book.setAuthor(author);
                    return bookService.createBook(book);
                })
                .map(bookMapper::toDTO);
    }

    @Override
    public Flux<BookDTO> getAllBooks() {
        return bookService.getAllBooks()
                .map(bookMapper::toDTO);
    }

    @Override
    public Flux<BookDTO> getBooksByCriteria(String authorName, Integer publicationYear) {
        if (StringUtils.hasText(authorName) && publicationYear != null) {
            return bookService.getBooksByAuthorNameAndPublicationYear(authorName, publicationYear)
                    .map(bookMapper::toDTO);
        } else if (StringUtils.hasText(authorName)) {
            return bookService.getBooksByAuthorName(authorName)
                    .map(bookMapper::toDTO);
        } else if (publicationYear != null) {
            return bookService.getBooksByPublicationYear(publicationYear)
                    .map(bookMapper::toDTO);
        } else {
            return getAllBooks();
        }
    }
}
