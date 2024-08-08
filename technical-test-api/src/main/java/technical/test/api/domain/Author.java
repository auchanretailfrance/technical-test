package technical.test.api.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Year;

import static java.lang.String.format;
import static technical.test.api.domain.util.ConstraintValidator.validate;

public record Author(@NotBlank String firstname,
                     @NotBlank String lastname,
                     @NotNull Year birthdate,
                     @Nullable String id) {
    public static Author of(String firstname, String lastname, Integer birthdate) {
        var instance = new Author(firstname, lastname, Year.of(birthdate), format("%s_%s", firstname, lastname).toLowerCase());
        validate(instance);
        return instance;
    }
}
