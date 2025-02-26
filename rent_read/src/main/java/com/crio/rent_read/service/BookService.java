package com.crio.rent_read.service;

import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.Rental;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final RentalRepository rentalRepository;

    public Book createBook(Book book) {
        book.setAvailabilityStatus(true);
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        book.setId(id);
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getAllAvailableBooks() {
        return bookRepository.findByAvailabilityStatusTrue();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    public Rental rentBook(Long bookId, User user) {
        Book book = getBookById(bookId);
        if (!book.isAvailabilityStatus()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book is not available");
        }

        List<Rental> activeRentals = rentalRepository.findByUserAndReturnDateIsNull(user);
        if (activeRentals.size() >= 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has reached maximum rental limit");
        }

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentalDate(LocalDateTime.now());
        book.setAvailabilityStatus(false);
        bookRepository.save(book);
        return rentalRepository.save(rental);
    }

    public void returnBook(Long bookId, User user) {
        Book book = getBookById(bookId);
        Rental rental = rentalRepository.findByUserAndBookAndReturnDateIsNull(user, book);
        if (rental == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found");
        }
        rental.setReturnDate(LocalDateTime.now());
        book.setAvailabilityStatus(true);
        bookRepository.save(book);
        rentalRepository.save(rental);
    }
}
