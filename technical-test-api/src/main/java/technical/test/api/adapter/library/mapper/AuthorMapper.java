package technical.test.api.adapter.library.mapper;

import org.mapstruct.Mapper;
import technical.test.api.adapter.LibraryMapper;
import technical.test.api.adapter.library.AuthorRepresentation;
import technical.test.api.domain.Author;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = LibraryMapper.class)
public interface AuthorMapper {

    default Author map(AuthorRepresentation author) {
        return Author.of(author.firstname(), author.lastname(), author.birthdate());
    }

    AuthorRepresentation map(Author author);

}
