/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */

package com.mycompany.logandregchat;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Test class for Registration
 * @author RC
 */
public class RegistrationNGTest {

    Registration registration = new Registration();

    public RegistrationNGTest() {}

    /**
     * Test of checkUserName method, of class Registration.
     */
    @Test
    public void testCheckUserName() {
        System.out.println("Testing checkUserName()");

        // ✅ Valid username
        assertTrue(registration.checkUserName("ky_1"),
                "Valid username should return true");

        // 🚫 Missing underscore
        assertFalse(registration.checkUserName("kyle1"),
                "Username without underscore should return false");

        // 🚫 Too long (> 5 chars)
        assertFalse(registration.checkUserName("kyle_123"),
                "Username longer than 5 characters should return false");

        // 🚫 Null check
        assertFalse(registration.checkUserName(null),
                "Null username should return false");
    }

    /**
     * Test of checkPasswordComplexity method, of class Registration.
     */
    @Test
    public void testCheckPasswordComplexity() {
        System.out.println("Testing checkPasswordComplexity()");

        // ✅ Valid password
        assertTrue(registration.checkPasswordComplexity("Pass@123"),
                "Valid password should contain capital, number, special char, length >= 8");

        // 🚫 Missing capital letter
        assertFalse(registration.checkPasswordComplexity("password@1"),
                "Password without capital letter should return false");

        // 🚫 Missing number
        assertFalse(registration.checkPasswordComplexity("Password@"),
                "Password without a number should return false");

        // 🚫 Missing special character
        assertFalse(registration.checkPasswordComplexity("Password1"),
                "Password without special character should return false");

        // 🚫 Too short
        assertFalse(registration.checkPasswordComplexity("Pa@1"),
                "Password shorter than 8 characters should return false");

        // 🚫 Null password
        assertFalse(registration.checkPasswordComplexity(null),
                "Null password should return false");
    }

    /**
     * Test of checkCellPhoneNumber method, of class Registration.
     */
    @Test
    public void testCheckCellPhoneNumber() {
        System.out.println("Testing checkCellPhoneNumber()");

        // ✅ Valid phone number
        assertTrue(registration.checkCellPhoneNumber("+27831234567"),
                "Valid phone number starting with + should return true");

        // 🚫 Missing +
        assertFalse(registration.checkCellPhoneNumber("27831234567"),
                "Phone number without + should return false");

        // 🚫 Too long
        assertFalse(registration.checkCellPhoneNumber("+2783123456789"),
                "Phone number longer than 12 characters should return false");

        // 🚫 Null input
        assertFalse(registration.checkCellPhoneNumber(null),
                "Null phone number should return false");

        // ✅ Phone number with spaces (allowed)
        assertTrue(registration.checkCellPhoneNumber("+2783 123 4567"),
                "Phone number with spaces should be accepted");
    }
}

