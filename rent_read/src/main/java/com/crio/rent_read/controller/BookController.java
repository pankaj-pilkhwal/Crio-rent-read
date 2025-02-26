package com.crio.rent_read.controller;

import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.Rental;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.service.BookService;
import com.crio.rent_read.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllAvailableBooks() {
        return ResponseEntity.ok(bookService.getAllAvailableBooks());
    }

    @PostMapping("/{bookId}/rent")
    public ResponseEntity<Rental> rentBook(@PathVariable Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.getUserByEmail(currentPrincipalName);
        return ResponseEntity.ok(bookService.rentBook(bookId, user));
    }

    @PostMapping("/{bookId}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.getUserByEmail(currentPrincipalName);
        bookService.returnBook(bookId, user);
        return ResponseEntity.ok().build();
    }
}