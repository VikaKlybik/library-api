package org.modsen.mapper;

import org.mapstruct.Mapper;
import org.modsen.dto.LibraryDTO;
import org.modsen.entity.Library;

@Mapper(componentModel = "spring")
public interface LibraryMapper {
    LibraryDTO toLibraryDTO(Library library);
    Library toLibraryModel(LibraryDTO libraryDTO);
}