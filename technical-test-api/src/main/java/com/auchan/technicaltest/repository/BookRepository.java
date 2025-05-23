package com.auchan.technicaltest.repository;

import com.auchan.technicaltest.model.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByAuthorName(String authorName);
    Flux<Book> findByPublicationYear(int publicationYear);
    Flux<Book> findByAuthorNameAndPublicationYear(String authorName, int publicationYear);
}
