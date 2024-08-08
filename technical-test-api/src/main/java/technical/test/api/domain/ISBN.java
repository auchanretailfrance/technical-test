package technical.test.api.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import static lombok.AccessLevel.PRIVATE;
import static technical.test.api.domain.util.ConstraintValidator.validate;

@Value
@Accessors(fluent = true)
@RequiredArgsConstructor(access = PRIVATE)
public class ISBN {
    public static final String ISBN_REGEX = "^[0-9-]{10,13}$";

    @NotBlank
    @Pattern(regexp = ISBN_REGEX)
    String id;

    public static ISBN of(String isbn) {
        var instance = new ISBN(isbn);
        validate(instance);
        return instance;
    }

}
