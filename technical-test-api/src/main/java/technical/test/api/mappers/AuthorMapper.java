package technical.test.api.mappers;

import org.mapstruct.Mapper;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.storage.models.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorRepresentation authorToAuthorRepresentation(Author author);

    Author authorRepresentationToAuthor(AuthorRepresentation authorRepresentation);
}
