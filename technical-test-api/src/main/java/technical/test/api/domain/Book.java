package technical.test.api.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Year;

import static technical.test.api.domain.util.ConstraintValidator.validate;

public record Book(
        @NotNull ISBN isbn,
        @NotBlank String title,
        @NotNull Year releaseDate,
        @NotBlank String author) {

    public static Book of(String isbn, String title, Integer releaseDate, String author) {
        var instance = new Book(ISBN.of(isbn), title, Year.of(releaseDate), author);
        validate(instance);
        return instance;
    }
}
