/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package com.mycompany.logandregchat;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Test class for Login
 * @author RC_Student_Lab
 */
public class LoginNGTest {

    // Create an instance of the Login class
    Login login = new Login();

    public LoginNGTest() {}

    /**
     * Test of loginUser method, of class Login.
     */
    @Test
    public void testLoginUser() {
        System.out.println("Testing loginUser method");

        String storedUser = "ky_1";
        String storedPass = "Pass@123";

        // Valid login
        assertTrue(login.loginUser("ky_1", "Pass@123", storedUser, storedPass),
                "Correct username and password should return true");

        // Wrong username
        assertFalse(login.loginUser("wrong", "Pass@123", storedUser, storedPass),
                "Wrong username should return false");

        // Wrong password
        assertFalse(login.loginUser("ky_1", "WrongPass", storedUser, storedPass),
                "Wrong password should return false");
    }

    /**
     * Test of returnLoginStatus method, of class Login.
     */
    @Test
    public void testReturnLoginStatus() {
        System.out.println("Testing returnLoginStatus method");

        String firstName = "Kyle";
        String lastName = "Smith";

        // Successful login message
        String successMsg = login.returnLoginStatus(true, firstName, lastName);
        assertEquals(successMsg, "Welcome Kyle Smith, it is great to see you again.",
                "Success message should match expected output");

        // Failed login message
        String failMsg = login.returnLoginStatus(false, firstName, lastName);
        assertEquals(failMsg, "Username or password incorrect, please try again.",
                "Failure message should match expected output");
    }
}
