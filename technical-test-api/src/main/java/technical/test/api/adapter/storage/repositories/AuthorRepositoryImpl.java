package technical.test.api.adapter.storage.repositories;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import technical.test.api.adapter.storage.mapper.AuthorDocumentMapper;
import technical.test.api.domain.Author;
import technical.test.api.domain.AuthorRepository;

@Repository("AuthorRepository")
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository  {

    private final AuthorDocumentMapper mapper;
    @Delegate
    private final AuthorCrudRepository repository;

    @Override
    public Mono<Author> registerAuthor(@NonNull Author author) {
        return repository.save(mapper.map(author)).map(mapper::map);
    }
}
