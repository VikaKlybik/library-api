package org.modsen.service;

import lombok.RequiredArgsConstructor;
import org.modsen.dto.LibraryDTO;
import org.modsen.dto.LibraryListDTO;
import org.modsen.entity.Library;
import org.modsen.exception.LibraryNotFoundException;
import org.modsen.mapper.LibraryMapper;
import org.modsen.repository.LibraryRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static org.modsen.util.Constant.LIBRARY_NOT_FOUND_BY_ID;

@RequiredArgsConstructor
@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;

    public LibraryListDTO getFreeBooks() {
        return new LibraryListDTO(libraryRepository.findByDateBorrowedIsNull().
                stream().map((book) -> libraryMapper.toLibraryDTO(book)).
                collect(Collectors.toList()));
    }

    public LibraryDTO updateBook(Long id, LibraryDTO libraryDTO) throws LibraryNotFoundException {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new LibraryNotFoundException(String.format(LIBRARY_NOT_FOUND_BY_ID, id)));
        library.setBookId(libraryDTO.getBookId());
        library.setDateBorrowed(libraryDTO.getDateBorrowed());
        library.setDateToReturn(libraryDTO.getDateBorrowed());
        libraryRepository.save(library);
        return libraryMapper.toLibraryDTO(library);
    }
}