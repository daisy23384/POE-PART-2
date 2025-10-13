/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.logandregchat;

public class Login {

    public boolean loginUser(String inputUsername, String inputPassword, String storedUsername, String storedPassword) {
        return inputUsername.equals(storedUsername) && inputPassword.equals(storedPassword);
    }

    public String returnLoginStatus(boolean success, String firstName, String lastName) {
        if (success) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
