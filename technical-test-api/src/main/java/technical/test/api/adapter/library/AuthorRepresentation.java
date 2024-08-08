package technical.test.api.adapter.library;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthorRepresentation(@NotBlank String firstname,
                                   @NotBlank String lastname,
                                   @NotNull Integer birthdate,
                                   @Nullable String id) {
}
