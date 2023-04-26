package com.example.PhoneBook.Service;

import com.example.PhoneBook.Exception.InvalidPhoneNumber;
import com.example.PhoneBook.Exception.NoContactFound;
import com.example.PhoneBook.Model.Contact;
import com.example.PhoneBook.Repository.PhoneBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import java.util.List;

@Service
public class PhoneBookService {
    private final PhoneBookRepository phoneBookRepository;

    @Autowired
    public PhoneBookService(PhoneBookRepository phoneBookRepository) {
        this.phoneBookRepository = phoneBookRepository;
    }

    public List<Contact> search(String searchString) {
        return phoneBookRepository.searchUsers(searchString);
    }

    public void deleteContact(Long id) {
        phoneBookRepository.deleteById(id);
    }

    public Contact updateContact(Long id, Contact updatedContact) throws InvalidPhoneNumber {
        Contact existingContact = phoneBookRepository.getById(id);
        if (existingContact == null) {
            throw new NoContactFound("2", "No contact with id " + id);
        }

        if(isPhoneNumberValid(existingContact.getPhone())) {
            existingContact.setFirstName(updatedContact.getFirstName());
            existingContact.setLastName(updatedContact.getLastName());
            existingContact.setPhone(updatedContact.getPhone());
            return phoneBookRepository.save(existingContact);
        }
        throw new InvalidPhoneNumber("1", "Invalid phone number");
    }

    public Contact saveContact(Contact contact) throws InvalidPhoneNumber {
        if(isPhoneNumberValid(contact.getPhone())) {
            return phoneBookRepository.save(contact);
        }
        throw new InvalidPhoneNumber("1", "Invalid phone number");
    }

    public static boolean isPhoneNumberValid(String phone)
    {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        PhoneNumber phoneNumber = null;

        try {
            phoneNumber = phoneUtil.parse(phone, "IN");
            System.out.println("\nType: " + phoneUtil.getNumberType(phoneNumber));
        }
        catch (NumberParseException e) {
            System.out.println("Unable to parse the given phone number: " + phone);
            e.printStackTrace();
        }
        return phoneUtil.isValidNumber(phoneNumber);
    }

    public Contact getById(Long Id) {
        return phoneBookRepository.getReferenceById(Id);
    }
}
