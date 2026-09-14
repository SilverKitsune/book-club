package com.bookclub.controller;

import com.bookclub.entity.Book;
import com.bookclub.entity.BookStatusEnum;
import com.bookclub.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO Проверить, что там с запросами

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/search")
    public List<Book> search(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) BookStatusEnum status
    ) {
        if (author != null) return bookService.findByAuthor(author);
        if (genre != null) return bookService.findByGenre(genre);
        if (status != null) return bookService.findByStatus(status);
        return bookService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        Book existing = bookService.findById(id);
        existing.setAuthor(book.getAuthor());
        existing.setName(book.getName());
        existing.setAnnotation(book.getAnnotation());
        existing.setGenre(book.getGenre());
        existing.setIsAudio(book.getIsAudio());
        existing.setStatus(book.getStatus());
        existing.setUser(book.getUser());
        existing.setMeeting(book.getMeeting());
        return bookService.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
