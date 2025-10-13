
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.logandregchat;

public class User {
    private String username;
    private String password;
    private String cellPhone;
    private String firstName;
    private String lastName;

    public User(String username, String password, String cellPhone, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellPhone = cellPhone;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getCellPhone() { return cellPhone; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}
