package com.example.PhoneBook.Controller;

import com.example.PhoneBook.Exception.InvalidPhoneNumber;
import com.example.PhoneBook.Model.Contact;
import com.example.PhoneBook.Service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class PhoneBookController {
    @Autowired
    PhoneBookService phoneBookService;

    @GetMapping("/search")
    public List<Contact> search(@RequestParam("find") String searchString) {
        return phoneBookService.search(searchString);
    }

    @PostMapping("/")
    public ResponseEntity<?> createContact(@RequestBody Contact contact) {
        try {
            Contact savedContact =  phoneBookService.saveContact(contact);
            return new ResponseEntity<Contact> (savedContact, HttpStatus.CREATED);
        }catch (InvalidPhoneNumber e) {
            return new ResponseEntity<InvalidPhoneNumber> (e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @RequestBody Contact updatedContact) throws InvalidPhoneNumber {

        // Save the updated user object to the database
        Contact savedContact = phoneBookService.updateContact(id, updatedContact);

        // Return the updated user object in the response
        return ResponseEntity.ok(savedContact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        // Delete Book entity object by ID
        phoneBookService.deleteContact(id);
        // Return 204 No Content in the response
        return ResponseEntity.noContent().build();
    }
}

