package com.auchan.technicaltest.repository;

import com.auchan.technicaltest.model.Author;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
    Mono<Author> findByName(String name);
}
