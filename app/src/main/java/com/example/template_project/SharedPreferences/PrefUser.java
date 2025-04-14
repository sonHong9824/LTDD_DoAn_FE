package com.example.template_project.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUser {

    private static final String PREF_NAME = "LoginDetails";
    private static final String KEY_EMAIL = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";
    private static final String KEY_ID = "id";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public PrefUser(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }
    public void saveLoginDetails(String email, String password, String name, String id) {
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_ID, id);
        editor.apply();
    }

    public boolean isUserLoggedOut() {
        return getEmail().isEmpty() || getPassword().isEmpty();
    }
    public String getId() {
        return sharedPreferences.getString(KEY_ID, "");
    }
    public String getName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }
    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
