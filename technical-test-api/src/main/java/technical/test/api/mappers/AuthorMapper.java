package technical.test.api.mappers;

import org.mapstruct.Mapper;
import technical.test.api.DTO.AuthorDTO;
import technical.test.api.entities.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author authorDtoToAuthor (AuthorDTO authorDTO);
    AuthorDTO authorToAuthorDTO (Author author);

}
