package com.example.BookLibraryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = {"com.example.BookLibraryService", "com.example.BookLibraryService.config", "com.example.BookLibraryService.component"})
@EnableScheduling
public class BookLibraryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookLibraryServiceApplication.class, args);
	}
}
