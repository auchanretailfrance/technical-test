package technical.test.api.adapter.storage.repositories;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.adapter.storage.mapper.BookDocumentMapper;
import technical.test.api.adapter.storage.models.BookDocument;
import technical.test.api.domain.Book;
import technical.test.api.domain.BookRepository;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static technical.test.api.adapter.storage.models.BookDocument.Fields.AUTHOR;
import static technical.test.api.adapter.storage.models.BookDocument.Fields.RELEASE_DATE;

@Repository("BookRepository")
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final BookDocumentMapper mapper;
    @Delegate
    private final BookCrudRepository repository;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Book> registerAuthor(@NonNull Book book) {
        return repository.save(mapper.map(book))
                .map(mapper::map);
    }

    @Override
    public Flux<Book> retrieveBooksByCriteria(String author, Integer releaseDateGt, Integer releaseDateLt) {
        var query = new Query();

        ofNullable(author).map(id -> where(AUTHOR).is(id)).ifPresent(query::addCriteria);

        if (releaseDateGt != null && releaseDateLt != null) {
            // Due to limitations of the com.mongodb.BasicDocument, you can't add a second 'releaseDate' criteria
            query.addCriteria(where(RELEASE_DATE).gte(releaseDateGt).lte(releaseDateLt));
        } else {
            ofNullable(releaseDateGt).map(date -> where(RELEASE_DATE).gte(date)).ifPresent(query::addCriteria);
            ofNullable(releaseDateLt).map(date -> where(RELEASE_DATE).lte(date)).ifPresent(query::addCriteria);
        }

        return mongoTemplate.find(query, BookDocument.class)
                .map(mapper::map);
    }

    @Override
    public Flux<Book> retrieveAllBooks() {
        return repository.findAll()
                .map(mapper::map);
    }
}
