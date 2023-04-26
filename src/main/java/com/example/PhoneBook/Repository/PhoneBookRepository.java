package com.example.PhoneBook.Repository;

import com.example.PhoneBook.Model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PhoneBookRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM Contact c WHERE lower(c.firstName) LIKE %:searchTerm% OR lower(c.lastName) LIKE %:searchTerm% OR c.phone LIKE %:searchTerm%")
    List<Contact> searchUsers(@Param("searchTerm") String searchTerm);
}