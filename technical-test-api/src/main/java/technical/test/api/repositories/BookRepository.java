package technical.test.api.repositories;

import com.mongodb.lang.Nullable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import technical.test.api.entities.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {


//    @Query("{ " +
//            " '$or' : [ { '$where': '?0 == null' } , { 'author' : ?0' } ] , " +
//            " '$or' : [ { '$where': '?1 == null' } , { 'releaseDate' : { '$gt': ?1 } } ] , " +
//            " '$or' : [ { '$where': '?2 == null' } , { 'releaseDate' : { '$lt': ?2 } } ] " +
//            "}")
    Flux<Book> findBookByAuthorAndReleaseDateBetween(String author, Integer from, Integer to);
    Flux<Book> findBookByReleaseDateBetween(Integer from, Integer to);
}
