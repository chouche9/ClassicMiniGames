package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.Map;

/**
 * A UserManager for this application.
 */
public class UserManager {
    /**
     * SharedPreferences that stores all users.
     */
    private SharedPreferences sharedPreferences;

    /**
     * Editor that edits the sharedPreferences.
     */
    private SharedPreferences.Editor editor;

    /**
     * The single static instance of UserManager.
     */
    private static UserManager userManager = null;

    /**
     * Construct this UserManager singleton instance.
     *
     * @param activity the activity that called this UserManager.
     */
    private UserManager(Activity activity) {
        this.sharedPreferences = activity.getApplicationContext().
                getSharedPreferences("users", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    /**
     * Method that creates the only instance of UserManager.
     *
     * @param activity the activity that called this UserManager.
     * @return UserManager the only instance of UserManager.
     */
    public static UserManager getInstance(Activity activity)
    {
        if (userManager == null)
            userManager = new UserManager(activity);

        return userManager;
    }

    /**
     * Saves the User instance into sharedPreferences.
     *
     * @param user the user that is to be saved.
     */
    public void saveUsers(User user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(user.getName(), json);
        editor.apply();
    }

    /**
     * Return the User instance with username userName.
     *
     * @param userName the username of a user.
     * @return User the user instance with the username userName.
     */
    public User getUser(String userName){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(userName, null);
        User user = gson.fromJson(json, User.class);
        return user;
    }

    /**
     * Return whether if userName and password are valid.
     *
     * @param userName the username.
     * @param password the password.
     * @return true if userName and password are valid.
     */
    public boolean authenticate(String userName, String password){
        User user = getUser(userName);
        if (user == null){
            return false;
        }
        return (user.getPassword().equals(password));
    }

    /**
     * Return whether if a user with a username userName has an account already.
     *
     * @param userName the username.
     * @return true if a user with userName doesn't exist.
     */
    public boolean createAuthenticate(String userName){
        User user = userManager.getUser(userName);
        return user == null;
    }

    /**
     * Return a string representation of all users in this application.
     *
     * @return a string representation of all users in this application.
     */
    @NonNull
    @Override
    public String toString() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        String result = "";
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            result += userManager.getUser(entry.getKey()).toString();
        }
        return result;
    }
}