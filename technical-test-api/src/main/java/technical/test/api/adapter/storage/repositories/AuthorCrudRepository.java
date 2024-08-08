package technical.test.api.adapter.storage.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import technical.test.api.adapter.storage.models.AuthorDocument;

interface AuthorCrudRepository extends ReactiveCrudRepository<AuthorDocument, String> {
}
