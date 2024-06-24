package org.modsen.controller;


import lombok.RequiredArgsConstructor;
import org.modsen.dto.BookDTO;
import org.modsen.dto.BookListDTO;
import org.modsen.exception.BookNotFoundException;
import org.modsen.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<BookListDTO> getBooks() {
        return ResponseEntity.ok().body(bookService.getBooks());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addBook(@RequestBody BookDTO bookDTO) {
        bookService.addBook(bookDTO);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        return ResponseEntity.ok().body(bookService.getBookByIsbn(isbn));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getAllBookByIsbn(@PathVariable Long id) throws BookNotFoundException {
        return ResponseEntity.ok().body(bookService.getBookById(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) throws BookNotFoundException {
        bookService.deleteBookById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable Long id, @RequestBody BookDTO bookDTO) throws BookNotFoundException {
        return ResponseEntity.ok().body(bookService.updateBook(id, bookDTO));
    }
}