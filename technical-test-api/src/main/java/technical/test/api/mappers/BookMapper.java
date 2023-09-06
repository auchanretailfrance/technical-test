package technical.test.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import technical.test.api.DTO.AuthorDTO;
import technical.test.api.DTO.BookDTO;
import technical.test.api.DTO.BookViewDTO;
import technical.test.api.entities.Author;
import technical.test.api.entities.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book bookDtoToBook(BookDTO bookDTODTO);

    BookDTO bookToBookDTO(Book book);

    @Mapping(target = "title", source = "book.title")
    @Mapping(target = "releaseDate", source = "book.releaseDate")
    @Mapping(target = "author", source = "author")
    BookViewDTO bookToBookViewDTO(Book book, String author);
}
