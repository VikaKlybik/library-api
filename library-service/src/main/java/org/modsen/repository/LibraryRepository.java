package org.modsen.repository;


import org.modsen.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    List<Library> findByDateBorrowedIsNull();
}