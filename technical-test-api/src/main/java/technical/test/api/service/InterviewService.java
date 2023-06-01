package technical.test.api.service;

import org.springframework.stereotype.Service;
import technical.test.api.Interview;
import technical.test.api.repository.InterviewRepository;

import java.util.List;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;

    public InterviewService(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    public List<Interview> getInterviews() {
        return interviewRepository.findAll();
    }

    public Interview createInterview(Interview interview) {
        return interviewRepository.save(interview);
    }
}
