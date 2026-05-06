/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repositories;

import com.dht.pojo.User;


public interface UserRepository {
    User getUserById(int id);
    User getUserByUsername(String username);
    User addUser(User u);
    boolean authenticate(String username, String password);
    boolean userExists(String username, String email);
}
