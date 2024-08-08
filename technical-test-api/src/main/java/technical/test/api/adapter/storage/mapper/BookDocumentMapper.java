package technical.test.api.adapter.storage.mapper;

import org.mapstruct.Mapper;
import technical.test.api.adapter.LibraryMapper;
import technical.test.api.adapter.storage.models.BookDocument;
import technical.test.api.domain.Book;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = LibraryMapper.class)
public interface BookDocumentMapper {

    Book map(BookDocument book);

    BookDocument map(Book book);

}
