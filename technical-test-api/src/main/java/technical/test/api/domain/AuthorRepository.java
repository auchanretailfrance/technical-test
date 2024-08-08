package technical.test.api.domain;

import lombok.NonNull;
import reactor.core.publisher.Mono;

public interface AuthorRepository {
    Mono<Author> registerAuthor(@NonNull Author author);
}
