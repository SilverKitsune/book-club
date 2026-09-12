package com.bookclub.service;

import com.bookclub.entity.Book;
import com.bookclub.entity.BookStatusEnum;
import com.bookclub.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга с id " + id + " не найдена"));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> findByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre);
    }

    public List<Book> findByStatus(BookStatusEnum status) {
        return bookRepository.findByStatus(status);
    }
}