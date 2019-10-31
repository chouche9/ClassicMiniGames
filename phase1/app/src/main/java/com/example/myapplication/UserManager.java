package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static UserManager userManager = null;
    private UserManager(Activity activity) {
        this.sharedPreferences = activity.getApplicationContext().getSharedPreferences("users", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    // static variable single_instance of type Singleton

    // variable of type String
    public String s;

    // private constructor restricted to this class itself
    // static method to create instance of Singleton class
    public static UserManager getInstance(Activity activity)
    {
        if (userManager == null)
            userManager = new UserManager(activity);

        return userManager;
    }

    public void saveUsers(User user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(user.getName(), json);
        editor.apply();
    }
    public User getUser(String user_name){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(user_name, null);
        User user = gson.fromJson(json, User.class);
        return user;
    }
    public boolean authenticate(String user_name, String password){
        User user = getUser(user_name);
        if (user == null){
            return false;
        }
        return (user.getPassword().equals(password));
    }
    public boolean createAuthenticate(String userName){
        User user = userManager.getUser(userName);
        return user == null;
    }

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

