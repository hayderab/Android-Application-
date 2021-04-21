package com.example.food_app;

import com.example.food_app.auth.EmailValidator;

import org.junit.Test;

import static org.junit.Assert.*;

public class loginTest {


     //--------------------------------------------------
    // reference
    // Class lab week 8 App Testing...........
    //--------------------------------------------------


    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.com"));
    }
    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.co.uk"));
    }
    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email"));
    }
    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse()
    {
        assertFalse(EmailValidator.isValidEmail("name@email..com"));
    }
    @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse()
    {
        assertFalse(EmailValidator.isValidEmail("@email.com"));
    }
    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(""));
    }
    @Test
    public void emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(null));
    }

//    @Test
//    public void passwordValidaor_Return_true() {
//        assertTrue(PasswordValidator.isValidPassword("wrongpassword"));
//    }
//
//    @Test
//    public void passwordValidaor_Return_false() {
//        assertFalse(PasswordValidator.isValidPassword("wron"));
//    }

}


