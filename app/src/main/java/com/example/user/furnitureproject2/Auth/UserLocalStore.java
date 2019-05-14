package com.example.user.furnitureproject2.Auth;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.user.furnitureproject2.Model.User;

public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);

    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        if(user.getName()!= null){
            spEditor.putString("name", user.getName());
        }
        if(user.getEmail()!= null){
            spEditor.putString("email", user.getEmail());
        }
        if(user.getPassword()!= null){
            spEditor.putString("password", user.getPassword());
        }
//        if(user.getId()<0){
            spEditor.putInt("userId", user.getId());
//        }
        spEditor.commit();
    }

    public User getLoggedInUser(){
        String name = userLocalDatabase.getString("name","");
        String email = userLocalDatabase.getString("email","");
        String password = userLocalDatabase.getString("password","");
        int userId = userLocalDatabase.getInt("userId",0);

        User storedUser = new User(userId,name,email,password);
        return storedUser;

    }

    public boolean getUserLoggedIn(){
        //user logged in
        if(userLocalDatabase.getBoolean("loggedIn",false) == true){
            return true;
        }else{//user logged out
            return false;
        }
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();

    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }


}
