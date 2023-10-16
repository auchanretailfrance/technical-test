package technical.test.api.mappers;

import org.mapstruct.Mapper;
import technical.test.api.DTO.Author;
import technical.test.api.entities.AuthorEntity;

@Mapper(componentModel = "spring")
public interface IAuthorMapper {

  Author toAuthor(AuthorEntity authorEntity);

  AuthorEntity toAuthor(Author author);
}