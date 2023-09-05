package technical.test.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.storage.models.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

  AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

  AuthorRepresentation toAuthorRepresentation(Author book);
}
