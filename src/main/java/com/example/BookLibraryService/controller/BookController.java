package com.example.BookLibraryService.controller;

import com.example.BookLibraryService.entity.Book;
import com.example.BookLibraryService.model.request.SearchBookRequest;
import com.example.BookLibraryService.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class BookController {
    private final BookService bookService;

    public BookController(BookService BookService) {
        this.bookService = BookService;
    }

    @GetMapping("/book")
    public List<Book> searchByAuthor(@RequestParam(name = "author") String author) {
        return bookService.findByAuthor(author);
    }

    @PostMapping("/book")
    public Book saveBook(@RequestBody SearchBookRequest request) {
        return bookService.saveBook(request);
    }
}
