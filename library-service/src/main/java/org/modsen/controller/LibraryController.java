package org.modsen.controller;

import lombok.RequiredArgsConstructor;
import org.modsen.dto.LibraryDTO;
import org.modsen.dto.LibraryListDTO;
import org.modsen.exception.LibraryNotFoundException;
import org.modsen.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/free")
    public ResponseEntity<LibraryListDTO> getFreeBooks() {
        return ResponseEntity.ok().body(libraryService.getFreeBooks());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody LibraryDTO libraryDTO) throws LibraryNotFoundException {
        libraryService.updateBook(id, libraryDTO);
    }
}