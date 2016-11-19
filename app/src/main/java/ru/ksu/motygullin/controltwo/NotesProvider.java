package ru.ksu.motygullin.controltwo;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by UseR7 on 19.11.2016.
 */

public class NotesProvider {
    private static final String PREFERENCES = "note";
    private static final String NAMES = "note";
    private String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l" , "m", "n", "o", "p" ,"q", "r", "s", "t", "u", "v","w","x" ,"y", "z"};

    private Context context;


    private  static  NotesProvider sInstance;

    public static NotesProvider getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NotesProvider(context.getApplicationContext());
        }
        return sInstance;
    }

    private NotesProvider(Context applicationContext) {
        this.context = applicationContext;
    }

    public void saveContacts(List<Note> contactList){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Note>>(){}.getType();
        String jsonText = gson.toJson(contactList, listType);
        editor.putString(NAMES, jsonText);
        editor.commit();
    }


    private List<Note> fillNotes() {
        Random random =new Random();
        List<Note> users = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            Note user = new Note("Note " + i, chars[random.nextInt(26)]);
            users.add(user);
        }
        return users;
    }

    public List<Note> getNotesList(){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        if(preferences.contains(NAMES)) {
            Gson gson = new Gson();
            String jsonText = preferences.getString(NAMES, "");
            Type listType = new TypeToken<List<Note>>(){}.getType();
            List<Note> contacts = gson.fromJson(jsonText, listType);
            return contacts;
        } else {
            List<Note> contacts;
            contacts = fillNotes();
            saveContacts(contacts);
            return contacts;
        }
    }


}

