package technical.test.api.domain.util;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class ConstraintValidator {
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T object) throws ConstraintViolationException {
        var constraintViolations = VALIDATOR.validate(object);

        if (!constraintViolations.isEmpty()) {
            log.error(constraintViolations.toString());
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
