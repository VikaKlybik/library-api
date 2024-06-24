package org.modsen.service;

import lombok.RequiredArgsConstructor;
import org.modsen.dto.BookDTO;
import org.modsen.dto.BookListDTO;
import org.modsen.entity.Book;
import org.modsen.exception.BookNotFoundException;
import org.modsen.mapper.BookMapper;
import org.modsen.repository.BookRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static org.modsen.util.Constants.BOOK_NOT_FOUND_BY_ID;
import static org.modsen.util.Constants.BOOK_NOT_FOUND_BY_ISBN;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookListDTO getBooks() {
        return new BookListDTO(bookRepository.findAll().stream()
                .map(bookMapper::toBookDTO)
                .collect(Collectors.toList()));
    }

    public BookDTO addBook(BookDTO bookDTO) {
        if(bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new DuplicateKeyException(String.format(BOOK_NOT_FOUND_BY_ISBN, bookDTO.getIsbn()));
        }
        Book book = bookMapper.toBookModel(bookDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toBookDTO(savedBook);
    }

    public BookDTO getBookByIsbn(String isbn) throws BookNotFoundException {
        Book opt_book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ISBN, isbn)));
        return bookMapper.toBookDTO(opt_book);
    }

    public BookDTO getBookById(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ID, id)));
        return bookMapper.toBookDTO(book);
    }

    public void deleteBookById(Long id) throws BookNotFoundException {
        if(!bookRepository.existsById(id)) {
            throw new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ID, id));
        }
        bookRepository.deleteById(id);
    }

    public BookDTO updateBook(Long id, BookDTO book) throws BookNotFoundException {
        Book opt_book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ID, id)));
        opt_book.setAuthor(book.getAuthor());
        opt_book.setTitle(book.getTitle());
        opt_book.setGenre(book.getGenre());opt_book.setIsbn(book.getIsbn());
        opt_book.setDescription(book.getDescription());
        bookRepository.save(opt_book);
        return bookMapper.toBookDTO(opt_book);
    }
}