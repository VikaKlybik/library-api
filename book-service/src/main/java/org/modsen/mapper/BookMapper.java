package org.modsen.mapper;


import org.mapstruct.Mapper;
import org.modsen.dto.BookDTO;
import org.modsen.entity.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toBookDTO(Book book);

    Book toBookModel(BookDTO bookDTO);
}