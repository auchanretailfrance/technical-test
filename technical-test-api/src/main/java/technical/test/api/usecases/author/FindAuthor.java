package technical.test.api.usecases.author;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import technical.test.api.DTO.Author;
import technical.test.api.mappers.IAuthorMapper;
import technical.test.api.repositories.author.IAuthorRepositoryImpl;

@Service
@AllArgsConstructor
public class FindAuthor {

  private final IAuthorRepositoryImpl authorRepository;

  private IAuthorMapper authorMapper;

  public Mono<Optional<Author>> findById(String id) {
    return authorRepository.findById(id)
        .map(authorMapper::toAuthor)
        .map(Optional::ofNullable)
        .switchIfEmpty(Mono.just(Optional.empty()));
  }
}
