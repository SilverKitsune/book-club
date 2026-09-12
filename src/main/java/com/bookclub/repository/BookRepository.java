package com.bookclub.repository;

import com.bookclub.entity.Book;
import com.bookclub.entity.BookStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByGenreIgnoreCase(String genre);

    List<Book> findByStatus(BookStatusEnum status);
}
