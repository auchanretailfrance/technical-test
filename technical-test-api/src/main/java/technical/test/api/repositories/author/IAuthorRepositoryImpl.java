package technical.test.api.repositories.author;

import reactor.core.publisher.Mono;
import technical.test.api.entities.AuthorEntity;

public interface IAuthorRepositoryImpl {

  Mono<AuthorEntity> findById(String id);

  Mono<AuthorEntity> save(AuthorEntity authorEntity);
}
