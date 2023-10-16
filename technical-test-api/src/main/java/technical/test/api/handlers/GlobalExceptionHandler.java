package technical.test.api.handlers;

import jakarta.validation.ConstraintViolationException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Mono<ErrorResponse> handleBadValidationRequestException(ConstraintViolationException ex) {
    return Mono.just(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
        Arrays.stream(ex.getMessage().split(",")).map(String::trim).toList()));
  }
}
