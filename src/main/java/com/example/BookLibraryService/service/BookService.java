package com.example.BookLibraryService.service;

import com.example.BookLibraryService.entity.Book;
import com.example.BookLibraryService.exception.DuplicateRegistrationException;
import com.example.BookLibraryService.model.request.SearchBookRequest;
import com.example.BookLibraryService.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findByAuthor(String author) {
        if (!StringUtils.hasText(author)) {
            throw new IllegalArgumentException("Author name must not be empty");
        }
        return new ArrayList<>(bookRepository.findByAuthor(author));
    }

    public Book saveBook(SearchBookRequest request) {
        if (!StringUtils.hasText(request.getTitle()) || !StringUtils.hasText(request.getAuthor())) {
            throw new IllegalArgumentException("Title และ Author ห้ามว่าง");
        }

        int buddhistYear = request.getPublishedDate().getYear();
        int currentBuddhistYear = LocalDate.now().getYear() + 543;

        if (buddhistYear <= 1000 || buddhistYear > currentBuddhistYear) {
            throw new IllegalArgumentException("ปีต้องอยู่ในโซน มากกว่า 1000 และน้อยกว่าหรือเท่ากับ " + currentBuddhistYear);
        }

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublishedDate(request.getPublishedDate().minusYears(543));

        return bookRepository.save(book);
    }
}