package com.example.myapplication.databaseconnector;

import com.example.myapplication.User;

interface UserDao {

  void saveUser(User user);

  User getUser(String username);
}
