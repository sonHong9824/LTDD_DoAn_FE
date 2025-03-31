package com.example.template_project.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUser {
    Context context;
    public PrefUser(Context context) {
        this.context = context;
    }

    public void saveloginDetails(String email, String password){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", email);
        editor.putString("password", password);
        editor.commit();
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isUsernameEmpty = sharedPreferences.getString("username", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("password", "").isEmpty();
        return isUsernameEmpty || isPasswordEmpty;
    }

}
