package technical.test.api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import technical.test.api.entity.Customer;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
