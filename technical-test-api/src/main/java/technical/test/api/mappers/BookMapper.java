package technical.test.api.mappers;

import org.mapstruct.Mapper;
import technical.test.api.representations.BookRepresentation;
import technical.test.api.storage.models.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookRepresentation bookToBookRepresentation(Book book);

    Book bookRepresentationToBook(BookRepresentation bookRepresentation);
}
