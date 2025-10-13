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

    // Create an instance of the Login class (capital L)
    Login login = new Login();

    public LoginNGTest() {
    }

    /**
     * Test of loginUser method, of class Login.
     */
    @Test
    public void testLoginUser() {
        System.out.println("Testing loginUser method");

        String storedUser = "ky_1";
        String storedPass = "Pass@123";

        // ✅ Test Case 1: Correct username and password
        assertTrue(login.loginUser("ky_1", "Pass@123", storedUser, storedPass),
                "Correct username and password should return true");

        // ✅ Test Case 2: Wrong username
        assertFalse(login.loginUser("wrong", "Pass@123", storedUser, storedPass),
                "Wrong username should return false");

        // ✅ Test Case 3: Wrong password
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

        // ✅ Test Case 1: Successful login
        String successMsg = login.returnLoginStatus(true, firstName, lastName);
        assertEquals(successMsg, "Welcome Kyle Smith, it is great to see you again.",
                "Success message should match expected output");

        // ✅ Test Case 2: Failed login
        String failMsg = login.returnLoginStatus(false, firstName, lastName);
        assertEquals(failMsg, "Username or password incorrect, please try again.",
                "Failure message should match expected output");
    }
}
