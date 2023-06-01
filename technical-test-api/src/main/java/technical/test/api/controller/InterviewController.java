package technical.test.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technical.test.api.Interview;
import technical.test.api.service.InterviewService;

import java.util.List;

@RestController
@RequestMapping("interview")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @GetMapping
    public ResponseEntity<List<Interview>> getInterviews() {
        List<Interview> interviews = interviewService.getInterviews();
        return ResponseEntity.ok(interviews);
    }

    @PostMapping
    public ResponseEntity<Interview> createInterview(@RequestBody Interview interview) {
        Interview createdInterview = interviewService.createInterview(interview);
        return ResponseEntity.ok(createdInterview);
    }
}
