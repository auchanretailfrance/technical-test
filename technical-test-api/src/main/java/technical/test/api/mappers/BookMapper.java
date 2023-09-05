package technical.test.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import technical.test.api.representations.BookRepresentation;
import technical.test.api.storage.models.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

  BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

  BookRepresentation toBookRepresentation(Book book);
}
