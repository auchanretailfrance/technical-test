package technical.test.api.usecases.author;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import technical.test.api.DTO.Author;
import technical.test.api.mappers.IAuthorMapper;
import technical.test.api.repositories.author.IAuthorRepositoryImpl;

@Service
@AllArgsConstructor
public class RegisterAuthor {

  private final IAuthorRepositoryImpl authorRepository;

  private final IAuthorMapper authorMapper;

  public Mono<Author> register(String firstName, String lastName, int birthDate) {
    return Mono.just(authorMapper.toAuthor(new Author(firstName, lastName, birthDate)))
        .flatMap(authorRepository::save)
        .map(authorMapper::toAuthor);
  }
}
