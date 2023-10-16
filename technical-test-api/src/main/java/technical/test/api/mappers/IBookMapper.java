package technical.test.api.mappers;

import org.mapstruct.Mapper;
import technical.test.api.DTO.Book;
import technical.test.api.entities.BookEntity;

@Mapper(componentModel = "spring")
public interface IBookMapper {
  Book toBook(BookEntity bookEntity);
}