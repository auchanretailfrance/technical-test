package technical.test.api.adapter.storage.mapper;

import org.mapstruct.Mapper;
import technical.test.api.adapter.LibraryMapper;
import technical.test.api.adapter.storage.models.AuthorDocument;
import technical.test.api.domain.Author;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = LibraryMapper.class)
public interface AuthorDocumentMapper {

    Author map(AuthorDocument author);

    AuthorDocument map(Author author);

}
