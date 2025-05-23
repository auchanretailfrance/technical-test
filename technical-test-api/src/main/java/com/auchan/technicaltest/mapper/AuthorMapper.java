package com.auchan.technicaltest.mapper;

import com.auchan.technicaltest.dto.AuthorDTO;
import com.auchan.technicaltest.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDTO(Author author);
    Author toEntity(AuthorDTO authorDTO);
}
