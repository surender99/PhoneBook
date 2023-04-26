package com.example.PhoneBook.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvalidPhoneNumber extends RuntimeException {
    String errorCode;
    String errorMessage;
}
