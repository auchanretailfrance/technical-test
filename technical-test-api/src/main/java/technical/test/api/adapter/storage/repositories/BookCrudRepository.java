package technical.test.api.adapter.storage.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import technical.test.api.adapter.storage.models.BookDocument;

interface BookCrudRepository extends ReactiveCrudRepository<BookDocument, String> {
}