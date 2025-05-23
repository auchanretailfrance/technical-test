package com.auchan.technicaltest.mapper;

import com.auchan.technicaltest.dto.BookCreationDTO;
import com.auchan.technicaltest.dto.BookDTO;
import com.auchan.technicaltest.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface BookMapper {

    @Mapping(source = "author.name", target = "authorName")
    BookDTO toDTO(Book book);

    @Mapping(target = "author", ignore = true)
    Book toEntity(BookCreationDTO bookCreationDTO);
}
