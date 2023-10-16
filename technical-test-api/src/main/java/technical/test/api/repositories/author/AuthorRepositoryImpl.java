package technical.test.api.repositories.author;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import technical.test.api.entities.AuthorEntity;

@Repository
@AllArgsConstructor
public class AuthorRepositoryImpl implements IAuthorRepositoryImpl {

  private final IAuthorRepository authorRepository;

  @Override
  public Mono<AuthorEntity> findById(String id) {
    return this.authorRepository.findById(id);
  }

  @Override
  public Mono<AuthorEntity> save(AuthorEntity authorEntity) {
    return this.authorRepository.save(authorEntity);
  }
}
