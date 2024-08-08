package technical.test.api.adapter.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import technical.test.api.adapter.LibraryMapper;
import technical.test.api.adapter.library.BookRepresentation;
import technical.test.api.domain.Book;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = LibraryMapper.class)
public interface BookMapper {

    @Mapping(target = "author", source = "authorId")
    Book map(BookRepresentation bookRepresentation);

    @Mapping(target = "authorId", source = "author")
    BookRepresentation map(Book book);

}
