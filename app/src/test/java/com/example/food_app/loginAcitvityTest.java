package com.example.food_app;

import com.example.food_app.auth.EmailValidator;
import com.example.food_app.auth.PasswordValidator;
import com.example.food_app.auth.login;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class loginAcitvityTest {

    private login Login;

    private boolean result;


    @Test
    public void isLogin(){
        result = true;
        Login = new login();

        assertEquals(true, login.isLogin(result));
    }

    @Test
    public void passwordValidaor_Return_true() {
        // if it meets the length requirments of the password .
        assertTrue(PasswordValidator.isValidPassword("wrongpassword"));
    }

    @Test
    public void passwordValidaor_Return_false() {
        assertFalse(PasswordValidator.isValidPassword("wron"));
    }


//    @Test
//    public  void isUserLogedin(){
//
//    }




}
