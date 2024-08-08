package technical.test.api.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    public Mono<Author> registerAuthor(@NonNull Author author) {
        return repository.registerAuthor(author);
    }
}
