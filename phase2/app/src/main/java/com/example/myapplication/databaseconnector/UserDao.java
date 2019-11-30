package com.example.myapplication.databaseconnector;

import com.example.myapplication.domain.User;

/** The DAO for handling users. */
interface UserDao {

  /**
   * Save the user into the database.
   *
   * @param user the user to be saved into the database.
   */
  void saveUser(User user);

  /**
   * Get the user object with the specified username from the database.
   *
   * @param username the username of the user.
   * @return the user object with the specified username.
   */
  User getUser(String username);
}
