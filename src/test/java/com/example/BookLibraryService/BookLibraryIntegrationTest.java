package com.example.BookLibraryService;

import com.example.BookLibraryService.model.request.SearchBookRequest;
import com.example.BookLibraryService.repository.BookRepository;
import com.example.BookLibraryService.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BookLibraryIntegrationTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("Case ชื่อห้ามว่าง")
    void save_AuthorEmpty_ShouldThrowException() {
        SearchBookRequest request = new SearchBookRequest();
        request.setTitle("Java 101");
        request.setAuthor(""); // ส่งค่าว่าง
        request.setPublishedDate(LocalDate.from(LocalDateTime.of(2569, 1, 1, 0, 0)));

        assertThrows(IllegalArgumentException.class, () -> bookService.saveBook(request));
    }

    @Test
    @DisplayName("Case: ปีเกิน 1000")
    void save_YearTooLow_ShouldThrowException() {
        SearchBookRequest request = new SearchBookRequest();
        request.setTitle("History");
        request.setAuthor("Old Man");
        request.setPublishedDate(LocalDate.from(LocalDateTime.of(999, 1, 1, 0, 0)));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> bookService.saveBook(request));

        assertTrue(ex.getMessage().contains("มากกว่า 1000"));
    }

    @Test
    @DisplayName("Case: ปีห้ามมากกว่า หรือ หนังสือใส่ข้อมูลปีอนาคต")
    void save_YearInFuture_ShouldThrowException() {
        int nextYear = LocalDate.now().getYear() + 543 + 1;
        SearchBookRequest request = new SearchBookRequest();
        request.setTitle("Future");
        request.setAuthor("Time Traveler");
        request.setPublishedDate(LocalDate.from(LocalDateTime.of(nextYear, 1, 1, 0, 0)));

        assertThrows(IllegalArgumentException.class, () -> bookService.saveBook(request));
    }

    @Test
    @DisplayName("Case: ค้นหาด้วยคำที่ไม่ควรจะหา")
    void search_EmptyAuthor_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> bookService.findByAuthor(" "));
    }
}
