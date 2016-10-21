package com.zzxzzg.rxpreference;

import android.content.SharedPreferences;

import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

/**
 * Created by yxwang on 16/10/21.
 */

class GsonPreferenceAdapter<T> implements Preference.Adapter<T> {
    final Gson gson = new Gson();
    private Class<T> clazz;

    // Constructor and exception handling omitted for brevity.
    public GsonPreferenceAdapter(Class<T> cla){
        clazz=cla;
    }

    @Override
    public T get(String key, SharedPreferences preferences) {
        return gson.fromJson(preferences.getString(key,""), clazz);
    }

    @Override
    public void set(String key, T value, SharedPreferences.Editor editor) {
        editor.putString(key, gson.toJson(value));
    }
}