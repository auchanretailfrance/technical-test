package technical.test.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import technical.test.api.Interview;

@Repository
public interface InterviewRepository extends MongoRepository<Interview, String> {
}
