package technical.test.api.repositories.book;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.entities.BookEntity;

@Repository
@AllArgsConstructor
public class BookRepositoryImpl implements IBookRepositoryImpl {

  private final IBookRepository bookRepository;

  private final ReactiveMongoTemplate mongoTemplate;

  public Flux<BookEntity> findByAuthorIdAndReleaseDateYearIsBetween(
      String authorId, Integer yearFrom, Integer yearTo) {

    Query query = new Query();
    if (authorId != null) {
      query.addCriteria(Criteria.where("authorId").is(authorId));
    }

    if (yearFrom != null && yearTo != null) {
      query.addCriteria(Criteria.where("releaseDateYear").gte(yearFrom).lte(yearTo));
    } else if (yearFrom != null) {
      query.addCriteria(Criteria.where("releaseDateYear").gte(yearFrom));
    } else if (yearTo != null) {
      query.addCriteria(Criteria.where("releaseDateYear").lte(yearTo));
    }

    return mongoTemplate.find(query, BookEntity.class);
  }

  @Override
  public Mono<BookEntity> insert(BookEntity bookEntity) {
    return this.bookRepository.insert(bookEntity);
  }
}
