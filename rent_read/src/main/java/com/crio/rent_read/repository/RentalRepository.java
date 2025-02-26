package com.crio.rent_read.repository;

import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.Rental;
import com.crio.rent_read.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserAndReturnDateIsNull(User user);
    Rental findByUserAndBookAndReturnDateIsNull(User user, Book book);
}
