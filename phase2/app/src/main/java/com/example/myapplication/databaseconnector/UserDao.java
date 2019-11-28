package com.example.myapplication.databaseconnector;

import com.example.myapplication.Domain.User;

interface UserDao {

  void saveUser(User user);

  User getUser(String username);
}
