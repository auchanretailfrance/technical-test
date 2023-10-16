package technical.test.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import technical.test.api.DTO.Author;
import technical.test.api.usecases.author.RegisterAuthor;

@RestController
@RequestMapping(value = "/library/authors")
@AllArgsConstructor
@Validated
public class AuthorController {

  private final RegisterAuthor registerAuthor;

  @PostMapping
  public Mono<Author> registerAuthor(
      @RequestParam(value = "firstname", required = false)
      @NotNull(message = "firstName can't be null")
      @NotBlank(message = "firstName can't be empty")
      String firstName,
      @RequestParam(value = "lastname", required = false)
      @NotNull(message = "lastName can't be null")
      @NotBlank(message = "lastName can't be empty")
      String lastName,
      @RequestParam(value = "birthdate", required = false)
      @Positive(message = "birthDate must be positive")
      @NotNull(message = "birthDate can't be null")
      @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "birthDate must be an integer")
      Integer birthDate) {
    return registerAuthor.register(firstName, lastName, birthDate);
  }
}
